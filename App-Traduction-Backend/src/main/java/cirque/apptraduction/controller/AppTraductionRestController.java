package cirque.apptraduction.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cirque.apptraduction.dto.*;
import cirque.apptraduction.Model.*;
import cirque.apptraduction.service.*;

@CrossOrigin(origins = "*")
@RestController
public class AppTraductionRestController {
	
	@Autowired
	AppTraductionService service;

	
	
	//*************************************************************************************************************//
	//*******************************************POST CREATE METHODS***********************************************//
	//*************************************************************************************************************//
	
	@PostMapping(value = { "/newConversation", "/newConversation/" })
	public ConversationDto createConversation() throws IllegalArgumentException {
		
		TraductionAppManager manager = service.getManager();
		if (manager == null) {
			manager = service.createTraductionAppManager();
		}		
		
		Conversation conversation = service.createConversation(manager);
		return convertToDto(conversation);
	}
	
	
	
	@PostMapping(value = { "/newLanguage/{name}", "/newLanguage/{name}/" })
	public LanguageDto createLanguage(@PathVariable("name") String name
			) throws IllegalArgumentException {
		
		TraductionAppManager manager = service.getManager();
		if (manager == null) {
			manager = service.createTraductionAppManager();
		}		
		
		Language language = service.createLanguage(name, manager);
		return convertToDto(language);
	}
	
	
	
	@PostMapping(value = { "/newPerson/{department}/{conversation}/{language}", 
			"/newPerson/{department}/{conversation}/{language}/"})
	public PersonDto createPerson(@PathVariable("department") String department, 
			@PathVariable("conversation") int conversationId, @PathVariable("language") String languageName
			) throws IllegalArgumentException {	
		
		Conversation conversation = service.getConversation(conversationId);
		Language language = service.getLanguage(languageName);
		
		Person person = service.createPerson(department, conversation, language);
		return convertToDto(person);
	}
	
	
	
	@PostMapping(value = { "/newSurvey/{person}", "/newPerson/{person}/"})
	public SurveyDto createSurvey(@PathVariable("person") int personId, @RequestParam int helpsWork, 
			@RequestParam int replacesService, @RequestParam int rating) throws IllegalArgumentException {	
		
		Person person= service.getPerson(personId);
		
		Survey survey = service.createSurvey(helpsWork, replacesService, rating, person);
		return convertToDto(survey);
	}
	
	
	
	@PostMapping(value = { "/newOriginalAudio/{person}", "/newOriginalAudio/{person}/"})
	public AudioDto createOriginalAudio(@PathVariable("person") int personId, 
			@RequestParam String message) throws IllegalArgumentException {	
		
		Person person= service.getPerson(personId);
		
		Audio audio = service.createOriginalAudio(message, person);
		return convertToDto(audio, true);
	}
	
	
	
	@PostMapping(value = { "/newOriginalText/{person}/{audio}", "/newOriginalText/{person}/{audio}/"})
	public TextDto createOriginalText(@PathVariable("person") int personId, @PathVariable("audio") int audioId,
			@RequestParam String message) throws IllegalArgumentException {	
		
		Person person = service.getPerson(personId);
		Audio audio = service.getAudio(audioId);
		
		Text text = service.createOriginalText(message, person, audio);
		return convertToDto(text, true);
	}
	
	
	
	@PostMapping(value = { "/newTranslatedText/{person}/{text}", "/newTranslatedText/{person}/{text}/"})
	public TextDto createTranslatedText(@PathVariable("person") int personId, @PathVariable("text") int textId,
			@RequestParam String message) throws IllegalArgumentException {	
		
		Person person = service.getPerson(personId);
		Text originalText = service.getText(textId);
		
		Text text = service.createTranslatedText(message, person, originalText);
		return convertToDto(text, false);
	}
	
	
	
	@PostMapping(value = { "/newTranslatedAudio/{person}/{text}", "/newTranslatedAudio/{person}/{text}/"})
	public AudioDto createTranslatedAudio(@PathVariable("person") int personId, @PathVariable("text") int textId,
			@RequestParam String message) throws IllegalArgumentException {	
		
		Person person = service.getPerson(personId);
		Text text = service.getText(textId);
		
		Audio audio = service.createTranslatedAudio(message, person, text);
		return convertToDto(audio, false);
	}
	
	
	
	
	//*************************************************************************************************************//
	//*********************************************GET LIST METHODS************************************************//
	//*************************************************************************************************************//
	
	@GetMapping(value = { "/allConversations", "/allConversations/"})
	public List<ConversationDto> viewAllConversations(){
		List<ConversationDto> allConversations = new ArrayList<ConversationDto>();
		Iterable<Conversation> conversations = service.getAllConversations();
		
		for (Conversation conversation : conversations) {
			allConversations.add(convertToDto(conversation));
		}
		return allConversations;
	}
	
	
	@GetMapping(value = { "/allLanguages", "/allLanguages/"})
	public List<LanguageDto> viewAllLanguages(){
		List<LanguageDto> allLanguages = new ArrayList<LanguageDto>();
		Iterable<Language> languages = service.getAllLanguages();
		
		for (Language language : languages) {
			allLanguages.add(convertToDto(language));
		}
		return allLanguages;
	}
	
	
	@GetMapping(value = { "/allPersons", "/allPersons/"})
	public List<PersonDto> viewAllPersons(){
		List<PersonDto> allPersons = new ArrayList<PersonDto>();
		Iterable<Person> persons = service.getAllPersons();
		
		for (Person person : persons) {
			allPersons.add(convertToDto(person));
		}
		return allPersons;
	}
	
	
	@GetMapping(value = { "/allSurveys", "/allSurveys/"})
	public List<SurveyDto> viewAllSurveys(){
		List<SurveyDto> allSurveys = new ArrayList<SurveyDto>();
		Iterable<Survey> surveys = service.getAllSurveys();
		
		for (Survey survey : surveys) {
			allSurveys.add(convertToDto(survey));
		}
		return allSurveys;
	}
	
	
	@GetMapping(value = { "/allOriginalAudios", "/allOriginalAudios/"})
	public List<AudioDto> viewAllOriginalAudios(){
		List<AudioDto> allAudios = new ArrayList<AudioDto>();
		Iterable<Audio> audios = service.getAllOriginalAudios();
		
		for (Audio audio : audios) {
			allAudios.add(convertToDto(audio, true));
		}
		return allAudios;
	}
	
	
	@GetMapping(value = { "/allTranslatedAudios", "/allTranslatedAudios/"})
	public List<AudioDto> viewAllTranslatedAudios(){
		List<AudioDto> allAudios = new ArrayList<AudioDto>();
		Iterable<Audio> audios = service.getAllTranslatedAudios();
		
		for (Audio audio : audios) {
			allAudios.add(convertToDto(audio, false));
		}
		return allAudios;
	}
	
	
	@GetMapping(value = { "/allAudios", "/allAudios/"})
	public List<AudioDto> viewAllAudios(){
		List<AudioDto> allAudios = new ArrayList<AudioDto>();
		Iterable<Audio> originalAudios = service.getAllOriginalAudios();
		Iterable<Audio> translatedAudios = service.getAllTranslatedAudios();
		
		for (Audio audio : originalAudios) {
			allAudios.add(convertToDto(audio, true));
		}
		for (Audio audio : translatedAudios) {
			allAudios.add(convertToDto(audio, false));
		}
		return allAudios;
	}
	
	
	@GetMapping(value = { "/allOriginalTexts", "/allOriginalTexts/"})
	public List<TextDto> viewAllOriginalTexts(){
		List<TextDto> allTexts = new ArrayList<TextDto>();
		Iterable<Text> texts = service.getAllOriginalTexts();
		
		for (Text text : texts) {
			allTexts.add(convertToDto(text, true));
		}
		return allTexts;
	}
	
	
	@GetMapping(value = { "/allTranslatedTexts", "/allTranslatedTexts/"})
	public List<TextDto> viewAllTranslatedTexts(){
		List<TextDto> allTexts = new ArrayList<TextDto>();
		Iterable<Text> texts = service.getAllTranslatedTexts();
		
		for (Text text : texts) {
			allTexts.add(convertToDto(text, false));
		}
		return allTexts;
	}
	
	
	@GetMapping(value = { "/allTexts", "/allTexts/"})
	public List<TextDto> viewAllTexts(){
		List<TextDto> allTexts = new ArrayList<TextDto>();
		Iterable<Text> originalTexts = service.getAllOriginalTexts();
		Iterable<Text> translatedTexts = service.getAllTranslatedTexts();
		
		for (Text text : originalTexts) {
			allTexts.add(convertToDto(text, true));
		}
		for (Text text : translatedTexts) {
			allTexts.add(convertToDto(text, false));
		}
		return allTexts;
	}
	
	
	
	
	//*************************************************************************************************************//
	//******************************************CONVERT TO DTO METHODS*********************************************//
	//*************************************************************************************************************//
	
	private ConversationDto convertToDto(Conversation c) {
		if (c == null) {
			throw new IllegalArgumentException("There is no such Conversation!");
		}
		return new ConversationDto(c.getDate(), c.getTime(), c.getWithGoogle());
	}
	
	
	private LanguageDto convertToDto(Language l) {
		if (l == null) {
			throw new IllegalArgumentException("There is no such Language!");
		}
		return new LanguageDto(l.getName());
	}
	
	
	private PersonDto convertToDto(Person p) {
		if (p == null) {
			throw new IllegalArgumentException("There is no such Person!");
		}
		return new PersonDto(p.getDepartment(), convertToDto(p.getConversation()), convertToDto(p.getLanguage()));
	}
	
	
	private SurveyDto convertToDto(Survey s) {
		if (s == null) {
			throw new IllegalArgumentException("There is no such Survey!");
		}
		return new SurveyDto(s.getHelpsWork(), s.getReplacesService(), s.getRating(), convertToDto(s.getPerson()));
	}
	
	
	private AudioDto convertToDto(Audio a, boolean isOriginal) {
		if (a == null) {
			throw new IllegalArgumentException("There is no such Audio!");
		}
		if (isOriginal) {
			return new AudioDto(a.getMessage(), convertToDto(a.getPerson()));
		}
		else {
			return new AudioDto(a.getMessage(), convertToDto(a.getPerson()), convertToDto(a.getMatchingText(), false));
		}
	}
	
	
	private TextDto convertToDto(Text t, boolean isOriginal) {
		if (t == null) {
			throw new IllegalArgumentException("There is no such Text!");
		}
		if (isOriginal) {
			return new TextDto(t.getMessage(), convertToDto(t.getPerson()), convertToDto(t.getMatchingAudio(), true));
		}
		else {
			return new TextDto(t.getMessage(), convertToDto(t.getPerson()), convertToDto(t.getTranslatedText(), true));
		}
	}
	
}
