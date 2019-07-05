package cirque.apptraduction.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;

import com.google.api.gax.rpc.ApiStreamObserver;
import com.google.api.gax.rpc.BidiStreamingCallable;
import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.*;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.common.util.concurrent.SettableFuture;
import com.google.protobuf.ByteString;
import com.google.cloud.translate.*;
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cirque.apptraduction.Model.*;
import cirque.apptraduction.Model.Language;
import cirque.apptraduction.dao.*;

@Service
public class AppTraductionService {

	@Autowired
	AudioRepository audioRepository;
	@Autowired
	ConversationRepository conversationRepository;
	@Autowired
	LanguageRepository languageRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	SurveyRepository surveyRepository;
	@Autowired
	TextRepository textRepository;
	@Autowired
	TraductionAppManagerRepository managerRepository;


	//*************************************************************************************************************//
	//**********************************************CREATE METHODS*************************************************//
	//*************************************************************************************************************//


	/*Creates manager*/
	@Transactional
	public TraductionAppManager createTraductionAppManager() {
		TraductionAppManager manager = new TraductionAppManager();
		managerRepository.save(manager);
		return manager;
	}


	/*Creates language
	 * 		adds language to list in manager */
	@Transactional
	public Language createLanguage(String name, TraductionAppManager manager) {
		if(name == null || name == "") {
			throw new IllegalArgumentException("The name of a language cannot be null or empty");
		}

		Set<Language> newLanguages = manager.getLanguage();
		if(newLanguages == null) {
			newLanguages = new HashSet<Language>();
		}
		for (Language iLanguage : newLanguages) {
			if (iLanguage.getName().equals(name))
				throw new IllegalArgumentException("This language already exists.");
		}

		Language language = new Language();
		language.setName(name);
		language.setTraductionAppManager(manager);
		languageRepository.save(language);

		newLanguages.add(language);
		manager.setLanguage(newLanguages);
		managerRepository.save(manager);

		return language;
	}


	/*Creates conversation
	 * 		uses current date and time and chooses between Amazon and Google engines
	 * 		adds conversation to list in manager */
	@Transactional
	public Conversation createConversation(TraductionAppManager manager) {
		Conversation conversation = new Conversation();

		Date date = new Date(System.currentTimeMillis());
		conversation.setDate(date);
		Time time = new Time(System.currentTimeMillis());
		conversation.setTime(time);

		conversation.setWithGoogle(chooseWithGoogle());

		conversation.setTraductionAppManager(manager);
		conversationRepository.save(conversation);

		Set<Conversation> newConversations = manager.getConversation();
		if(newConversations == null) {
			newConversations = new HashSet<Conversation>();
		}
		newConversations.add(conversation);
		manager.setConversation(newConversations);
		managerRepository.save(manager);

		return conversation;
	}


	/*Creates Person 
	 * 		adds person to list in languages and list in conversation */
	@Transactional
	public Person createPerson(String department, Conversation conversation, Language language) {
		if(department == null || department == "") {
			throw new IllegalArgumentException("The department cannot be null or empty");
		}
		if(language == null) {
			throw new IllegalArgumentException("The language cannot be null");
		}
		Person person = new Person();
		person.setDepartment(department);
		person.setConversation(conversation);
		person.setLanguage(language);
		personRepository.save(person);

		Set<Person> newPersonsConversation = conversation.getPerson();
		if(newPersonsConversation == null) {
			newPersonsConversation = new HashSet<Person>();
		}
		newPersonsConversation.add(person);
		conversation.setPerson(newPersonsConversation);
		conversationRepository.save(conversation);

		Set<Person> newPersonsLanguage = language.getPerson();
		if(newPersonsLanguage == null) {
			newPersonsLanguage = new HashSet<Person>();
		}
		newPersonsLanguage.add(person);
		language.setPerson(newPersonsLanguage);
		languageRepository.save(language);

		return person;
	}


	/* Creates survey
	 * 		adds survey to person */
	@Transactional
	public Survey createSurvey(int helpsWork, int replacesService, int rating, Person person) {
		if(helpsWork < 0 || replacesService < 0 || rating < 0) {
			throw new IllegalArgumentException("Ratings cannot be negative");
		}
		Survey survey = new Survey();
		survey.setHelpsWork(helpsWork);
		survey.setReplacesService(replacesService);
		survey.setRating(rating);
		survey.setPerson(person);
		surveyRepository.save(survey);

		person.setSurvey(survey);
		personRepository.save(person);

		return survey;
	}


	/* Creates original audio (audio that was spoken by a real person)
	 * 		added to original audio list in person */
	@Transactional
	public Audio createOriginalAudio(String message, Person person) {
		Audio audio = new Audio();
		audio.setMessage(message);
		audio.setPerson(person);
		audio.setIsOriginal(true);
		audioRepository.save(audio);

		Set<Audio> newAudios = person.getOriginalAudio();
		if(newAudios == null) {
			newAudios = new HashSet<Audio>();
		}
		newAudios.add(audio);
		person.setOriginalAudio(newAudios);
		personRepository.save(person);

		return audio;
	}


	/* Creates translated audio (audio that was synthesized by AI engine)
	 * 		added to translated audio list in person
	 * 		matched with corresponding translated text */
	@Transactional
	public Audio createTranslatedAudio(String message, Person person, Text matchingText) {
		Audio audio = new Audio();
		audio.setMessage(message);
		audio.setPerson(person);
		audio.setIsOriginal(false);
		audio.setMatchingText(matchingText);
		audioRepository.save(audio);

		matchingText.setMatchingAudio(audio);
		textRepository.save(matchingText);

		Set<Audio> newAudios = person.getTranslatedAudio();
		if(newAudios == null) {
			newAudios = new HashSet<Audio>();
		}
		newAudios.add(audio);
		person.setTranslatedAudio(newAudios);
		personRepository.save(person);

		return audio;
	}


	/* Creates original text (text that was transcribed from original audio)
	 * 		added to original text list in person
	 * 		matched with corresponding original audio */
	@Transactional
	public Text createOriginalText(String message, Person person, Audio matchingAudio) {
		Text text = new Text();
		text.setMessage(message);
		text.setPerson(person);
		text.setIsOriginal(true);
		text.setMatchingAudio(matchingAudio);
		textRepository.save(text);

		matchingAudio.setMatchingText(text);
		audioRepository.save(matchingAudio);

		Set<Text> newTexts = person.getOriginalText();
		if(newTexts == null) {
			newTexts = new HashSet<Text>();
		}
		newTexts.add(text);
		person.setOriginalText(newTexts);
		personRepository.save(person);

		return text;
	}


	/* Creates translated text (from original text)
	 * 		added to translated text list in person
	 * 		matched with corresponding original text */
	@Transactional
	public Text createTranslatedText(String message, Person person, Text originalText) {
		Text text = new Text();
		text.setMessage(message);
		text.setPerson(person);
		text.setIsOriginal(false);
		text.setTranslatedText(originalText);
		textRepository.save(text);

		originalText.setTranslatedText(text);
		textRepository.save(originalText);

		Set<Text> newTexts = person.getTranslatedText();
		if(newTexts == null) {
			newTexts = new HashSet<Text>();
		}
		newTexts.add(text);
		person.setTranslatedText(newTexts);
		personRepository.save(person);

		return text;
	}




	//*************************************************************************************************************//
	//************************************************GET METHODS**************************************************//
	//*************************************************************************************************************//


	/* Gets the TraductionAppManager */
	@Transactional
	public TraductionAppManager getManager() {
		if (toList(managerRepository.findAll()).size() == 0) {
			return null;
		}
		return toList(managerRepository.findAll()).get(0);
	}


	/*Gets a language with its name*/
	@Transactional
	public Language getLanguage(String name) {
		Language language = languageRepository.findLanguageByName(name);
		return language;		
	}


	/*Gets a conversation with its id*/
	@Transactional
	public Conversation getConversation(int id) {
		Conversation conversation = conversationRepository.findConversationById(id);
		return conversation;		
	}


	/*Gets a person with its id*/
	@Transactional
	public Person getPerson(int id) {
		Person person = personRepository.findPersonById(id);
		return person;		
	}


	/*Gets an audio with its id*/
	@Transactional
	public Audio getAudio(int id) {
		Audio audio = audioRepository.findAudioById(id);
		return audio;		
	}


	/*Gets a text with its id*/
	@Transactional
	public Text getText(int id) {
		Text text = textRepository.findTextById(id);
		return text;		
	}


	/* Gets all audios */
	@Transactional
	public List<Audio> getAllAudios() {
		return toList(audioRepository.findAll());
	}


	/* Gets all conversations */
	@Transactional
	public List<Conversation> getAllConversations() {
		return toList(conversationRepository.findAll());
	}


	/* Gets all languages */
	@Transactional
	public List<Language> getAllLanguages() {
		return toList(languageRepository.findAll());
	}


	/* Gets all persons */
	@Transactional
	public List<Person> getAllPersons() {
		return toList(personRepository.findAll());
	}


	/* Gets all surveys */
	@Transactional
	public List<Survey> getAllSurveys() {
		return toList(surveyRepository.findAll());
	}


	/* Gets all texts */
	@Transactional
	public List<Text> getAllTexts() {
		return toList(textRepository.findAll());
	}


	/* Gets all original audios */
	@Transactional
	public List<Audio> getAllOriginalAudios() {
		List<Audio> allAudios = toList(audioRepository.findAll());
		List<Audio> allOriginalAudios = new ArrayList<Audio>();
		for (Audio audio : allAudios) {
			if (audio.getIsOriginal() == true)
				allOriginalAudios.add(audio);
		}
		return allOriginalAudios;
	}


	/* Gets all translated audios */
	@Transactional
	public List<Audio> getAllTranslatedAudios() {
		List<Audio> allAudios = toList(audioRepository.findAll());
		List<Audio> allTranslatedAudios = new ArrayList<Audio>();
		for (Audio audio : allAudios) {
			if (audio.getIsOriginal() == false)
				allTranslatedAudios.add(audio);
		}
		return allTranslatedAudios;
	}


	/* Gets all original texts */
	@Transactional
	public List<Text> getAllOriginalTexts() {
		List<Text> allTexts = toList(textRepository.findAll());
		List<Text> allOriginalTexts = new ArrayList<Text>();
		for (Text text : allTexts) {
			if (text.getIsOriginal() == true)
				allOriginalTexts.add(text);
		}
		return allOriginalTexts;
	}


	/* Gets all translated texts */
	@Transactional
	public List<Text> getAllTranslatedTexts() {
		List<Text> allTexts = toList(textRepository.findAll());
		List<Text> allTranslatedTexts = new ArrayList<Text>();
		for (Text text : allTexts) {
			if (text.getIsOriginal() == false)
				allTranslatedTexts.add(text);
		}
		return allTranslatedTexts;
	}





	//*************************************************************************************************************//
	//********************************************INTERMEDIATE METHODS*********************************************//
	//*************************************************************************************************************//


	/* Transforms iterable list into arrayList */
	public <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}


	/* TO BE CHANGED ONCE AMAZON ACCOUNT HAS BEEN CREATED
	 *		Chooses between Amazon and Google*/
	public Boolean chooseWithGoogle() {
		/*Random rand = new Random();
		int r = rand.nextInt(2);
		if (r == 0) return true;
		else return false;*/
		return true;
	}







	//*************************************************************************************************************//
	//****************************************GOOGLE API CALLS - SPEECH TO TEXT************************************//
	//*************************************************************************************************************//


	/**
	 * Performs streaming speech recognition on raw PCM audio data.
	 *
	 * @param fileName the path to a PCM audio file to transcribe.
	 */
	public static void streamingRecognizeFile(String fileName) throws Exception, IOException {
		Path path = Paths.get(fileName);
		byte[] data = Files.readAllBytes(path);

		// Instantiates a client with GOOGLE_APPLICATION_CREDENTIALS
		try (SpeechClient speech = SpeechClient.create()) {

			// Configure request with local raw PCM audio
			RecognitionConfig recConfig =
					RecognitionConfig.newBuilder()
					.setEncoding(com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding.LINEAR16)
					.setLanguageCode("en-US")
					.setSampleRateHertz(16000)
					.setModel("default")
					.build();
			StreamingRecognitionConfig config =
					StreamingRecognitionConfig.newBuilder().setConfig(recConfig).build();

			class ResponseApiStreamingObserver<T> implements ApiStreamObserver<T> {
				private final SettableFuture<List<T>> future = SettableFuture.create();
				private final List<T> messages = new java.util.ArrayList<T>();

				@Override
				public void onNext(T message) {
					messages.add(message);
				}

				@Override
				public void onError(Throwable t) {
					future.setException(t);
				}

				@Override
				public void onCompleted() {
					future.set(messages);
				}

				// Returns the SettableFuture object to get received messages / exceptions.
				public SettableFuture<List<T>> future() {
					return future;
				}
			}

			ResponseApiStreamingObserver<StreamingRecognizeResponse> responseObserver =
					new ResponseApiStreamingObserver<>();

			BidiStreamingCallable<StreamingRecognizeRequest, StreamingRecognizeResponse> callable =
					speech.streamingRecognizeCallable();

			@SuppressWarnings("deprecation")
			ApiStreamObserver<StreamingRecognizeRequest> requestObserver =
			callable.bidiStreamingCall(responseObserver);

			// The first request must **only** contain the audio configuration:
			requestObserver.onNext(
					StreamingRecognizeRequest.newBuilder().setStreamingConfig(config).build());

			// Subsequent requests must **only** contain the audio data.
			requestObserver.onNext(
					StreamingRecognizeRequest.newBuilder()
					.setAudioContent(ByteString.copyFrom(data))
					.build());

			// Mark transmission as completed after sending the data.
			requestObserver.onCompleted();

			List<StreamingRecognizeResponse> responses = responseObserver.future().get();

			for (StreamingRecognizeResponse response : responses) {
				// For streaming recognize, the results list has one is_final result (if available) followed
				// by a number of in-progress results (if iterim_results is true) for subsequent utterances.
				// Just print the first result here.
				StreamingRecognitionResult result = response.getResultsList().get(0);
				// There can be several alternative transcripts for a given chunk of speech. Just use the
				// first (most likely) one here.
				SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
				System.out.printf("Transcript : %s\n", alternative.getTranscript());
			}
		}
	}




	/** Performs microphone streaming speech recognition with a duration of 1 minute. */
	public static void streamingMicRecognize() throws Exception {

		ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
		try (SpeechClient client = SpeechClient.create()) {

			responseObserver =
					new ResponseObserver<StreamingRecognizeResponse>() {
				ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();

				public void onStart(StreamController controller) {}

				public void onResponse(StreamingRecognizeResponse response) {
					responses.add(response);
				}

				public void onComplete() {
					for (StreamingRecognizeResponse response : responses) {
						StreamingRecognitionResult result = response.getResultsList().get(0);
						SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
						System.out.printf("Transcript : %s\n", alternative.getTranscript());
					}
				}

				public void onError(Throwable t) {
					System.out.println(t);
				}
			};

			ClientStream<StreamingRecognizeRequest> clientStream =
					client.streamingRecognizeCallable().splitCall(responseObserver);

			RecognitionConfig recognitionConfig =
					RecognitionConfig.newBuilder()
					.setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
					.setLanguageCode("en-US")
					.setSampleRateHertz(16000)
					.build();
			StreamingRecognitionConfig streamingRecognitionConfig =
					StreamingRecognitionConfig.newBuilder().setConfig(recognitionConfig).build();

			StreamingRecognizeRequest request =
					StreamingRecognizeRequest.newBuilder()
					.setStreamingConfig(streamingRecognitionConfig)
					.build(); // The first request in a streaming call has to be a config

			clientStream.send(request);
			// SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true,
			// bigEndian: false
			AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
			DataLine.Info targetInfo =
					new Info(
							TargetDataLine.class,
							audioFormat); // Set the system information to read from the microphone audio stream

			if (!AudioSystem.isLineSupported(targetInfo)) {
				System.out.println("Microphone not supported");
				System.exit(0);
			}
			// Target data line captures the audio stream the microphone produces.
			TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
			System.out.println("Start speaking");
			long startTime = System.currentTimeMillis();
			// Audio Input Stream
			AudioInputStream audio = new AudioInputStream(targetDataLine);
			while (true) {
				long estimatedTime = System.currentTimeMillis() - startTime;
				byte[] data = new byte[6400];
				audio.read(data);
				if (estimatedTime > 60000) { // 60 seconds
					System.out.println("Stop speaking.");
					targetDataLine.stop();
					targetDataLine.close();
				}
				request =
						StreamingRecognizeRequest.newBuilder()
						.setAudioContent(ByteString.copyFrom(data))
						.build();
				clientStream.send(request);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		responseObserver.onComplete();
	}





	//*************************************************************************************************************//
	//***********************************GOOGLE API CALLS - TEXT TO TEXT TRANSLATION*******************************//
	//*************************************************************************************************************//


	/** Performs microphone streaming speech recognition with a duration of 1 minute. */
	public static void defaultTextTranslation(String text) {
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		Translation translation = translate.translate(text);
		System.out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());
	}

	/** Performs microphone streaming speech recognition with a duration of 1 minute. */
	public static void simpleTextTranslation(String text, String source, String target) {
		Translate translate = TranslateOptions.getDefaultInstance().getService();
		Translation translation =
				translate.translate(
						text,
						Translate.TranslateOption.sourceLanguage(source),	//source = "es"
						Translate.TranslateOption.targetLanguage(target),	//target = "de"
						// Use "base" for standard edition, "nmt" for the premium model.
						Translate.TranslateOption.model("nmt"));

		System.out.printf("TranslatedText:\nText: %s\n", translation.getTranslatedText());
	}






	//*************************************************************************************************************//
	//*****************************************GOOGLE API CALLS - TEXT TO SPEECH***********************************//
	//*************************************************************************************************************//



	public static void textToSpeech() throws Exception {
		// Instantiates a client
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
			// Set the text input to be synthesized
			SynthesisInput input = SynthesisInput.newBuilder()
					.setText("Hello, World!")
					.build();

			// Build the voice request, select the language code ("en-US") and the ssml voice gender
			// ("neutral")
			VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
					.setLanguageCode("en-US")
					.setSsmlGender(SsmlVoiceGender.NEUTRAL)
					.build();

			// Select the type of audio file you want returned

			AudioConfig audioConfig = AudioConfig.newBuilder()
					.setAudioEncoding(com.google.cloud.texttospeech.v1.AudioEncoding.MP3)
					.build();

			// Perform the text-to-speech request on the text input with the selected voice parameters and
			// audio file type
			SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice,
					audioConfig);

			// Get the audio contents from the response
			ByteString audioContents = response.getAudioContent();

			// Write the response to the output file.
			try (OutputStream out = new FileOutputStream("output.mp3")) {
				out.write(audioContents.toByteArray());
				System.out.println("Audio content written to file \"output.mp3\"");
			}
		}
	}

	/**
	 * Demonstrates using the Text to Speech client to synthesize text or ssml.
	 *
	 * @param text the raw text to be synthesized. (e.g., "Hello there!")
	 * @throws Exception on TextToSpeechClient Errors.
	 */
	public static ByteString synthesizeText(String text) throws Exception {
		// Instantiates a client
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
			// Set the text input to be synthesized
			SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

			// Build the voice request
			VoiceSelectionParams voice =
					VoiceSelectionParams.newBuilder()
					.setLanguageCode("en-US") // languageCode = "en_us"
					.setSsmlGender(SsmlVoiceGender.FEMALE) // ssmlVoiceGender = SsmlVoiceGender.FEMALE
					.build();

			// Select the type of audio file you want returned
			AudioConfig audioConfig =
					AudioConfig.newBuilder()
					.setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
					.build();

			// Perform the text-to-speech request
			SynthesizeSpeechResponse response =
					textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

			// Get the audio contents from the response
			ByteString audioContents = response.getAudioContent();

			// Write the response to the output file.
			try (OutputStream out = new FileOutputStream("output.mp3")) {
				out.write(audioContents.toByteArray());
				System.out.println("Audio content written to file \"output.mp3\"");
				return audioContents;
			}
		}
	}
}
