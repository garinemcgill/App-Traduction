package cirque.apptraduction.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cirque.apptraduction.dao.*;
import cirque.apptraduction.Model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTraductionServiceTests {

	@Autowired
	private AppTraductionService service;
	
	@Autowired
	private AudioRepository audioRepository;
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private SurveyRepository surveyRepository;
	@Autowired
	private TextRepository textRepository;
	@Autowired
	private TraductionAppManagerRepository managerRepository;
	
	
	
	/*Clear all databases before and after all tests*/
	@After
	@Before
	public void clearDatabase() {
		managerRepository.deleteAll();
		languageRepository.deleteAll();
		conversationRepository.deleteAll();
		personRepository.deleteAll();
		surveyRepository.deleteAll();
		audioRepository.deleteAll();
		textRepository.deleteAll();
	}
	
	
	
	//*************************************************************************************************************//
	//*********************************************CONVERSATION TESTS**********************************************//
	//*************************************************************************************************************//
	
	@Test
	public void testCreateConversation() {
		assertEquals(0, service.getAllConversations().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		try {
			service.createConversation(manager);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Conversation> allConversations = service.getAllConversations();
		assertEquals(1, allConversations.size());
		assertEquals(true, allConversations.get(0).getWithGoogle());
		
		Set<Conversation> managerConversations = manager.getConversation();
		assertEquals(1, managerConversations.size());
		
		if (allConversations.get(0).getDate() == null) {
			fail();
		}
		if (allConversations.get(0).getTime() == null) {
			fail();
		}
	}
		
	
	//*************************************************************************************************************//
	//***********************************************LANGUAGE TESTS************************************************//
	//*************************************************************************************************************//
	
	@Test
	public void testCreateLanguage() {
		assertEquals(0, service.getAllLanguages().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		String name = "English";
		
		try {
			service.createLanguage(name, manager);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Language> allLanguages = service.getAllLanguages();
		assertEquals(1, allLanguages.size());
		assertEquals(name, allLanguages.get(0).getName());
		
		Set<Language> managerLanguages = manager.getLanguage();
		assertEquals(1, managerLanguages.size());
		assertEquals(name, service.toList(managerLanguages).get(0).getName());
		
	}
	
	
	@Test
	public void testCreateLanguageNameDouble() {
		assertEquals(0, service.getAllLanguages().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		String name = "English";
		String error = null;
		
		service.createLanguage(name, manager);
		try {
			service.createLanguage(name, manager);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		List<Language> allLanguages = service.getAllLanguages();
		assertEquals(1, allLanguages.size());
		assertEquals(name, allLanguages.get(0).getName());
		assertEquals(error, "This language already exists.");
	}
	
	
	@Test
	public void testCreateLanguageNullName() {
		assertEquals(0, service.getAllLanguages().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		String name = null;
		String error = null;
		
		try {
			service.createLanguage(name, manager);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		List<Language> allLanguages = service.getAllLanguages();
		assertEquals(0, allLanguages.size());
		assertEquals(error, "The name of a language cannot be null or empty");
	}
	
	
	@Test
	public void testCreateLanguageEmptyName() {
		assertEquals(0, service.getAllLanguages().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		String name = "";
		String error = null;
		
		try {
			service.createLanguage(name, manager);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		List<Language> allLanguages = service.getAllLanguages();
		assertEquals(0, allLanguages.size());
		assertEquals(error, "The name of a language cannot be null or empty");
	}
	
	
	
	
	//*************************************************************************************************************//
	//************************************************PERSON TESTS*************************************************//
	//*************************************************************************************************************//
	
	@Test
	public void testCreatePerson() {
		assertEquals(0, service.getAllPersons().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "TI";
		
		try {
			service.createPerson(department, conversation, language);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Person> allPersons = service.getAllPersons();
		assertEquals(1, allPersons.size());
		assertEquals(department, allPersons.get(0).getDepartment());
		
		Set<Person> conversationPersons = conversation.getPerson();
		assertEquals(1, conversationPersons.size());
		Set<Person> languagePersons = language.getPerson();
		assertEquals(1, languagePersons.size());
	}
	
	
	@Test
	public void testCreatePersonNullDepartment() {
		assertEquals(0, service.getAllPersons().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = null;
		String error = null;
		
		try {
			service.createPerson(department, conversation, language);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		List<Person> allPersons = service.getAllPersons();
		assertEquals(0, allPersons.size());
		assertEquals(error, "The department cannot be null or empty");
	}
	
	
	@Test
	public void testCreatePersonEmptyDepartment() {
		assertEquals(0, service.getAllPersons().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "";
		String error = null;
		
		try {
			service.createPerson(department, conversation, language);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		List<Person> allPersons = service.getAllPersons();
		assertEquals(0, allPersons.size());
		assertEquals(error, "The department cannot be null or empty");
	}
	
	
	
	
	//*************************************************************************************************************//
	//************************************************SURVEY TESTS*************************************************//
	//*************************************************************************************************************//
	
	@Test
	public void testCreateSurvey() {
		assertEquals(0, service.getAllSurveys().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "TI";
		Person person = service.createPerson(department, conversation, language);
		
		Integer helpsWork = 2, replacesService = 6, rating = 5;
		
		try {
			service.createSurvey(helpsWork, replacesService, rating, person);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Survey> allSurveys = service.getAllSurveys();
		assertEquals(1, allSurveys.size());
		assertEquals(rating, allSurveys.get(0).getRating());
		
		Survey personSurvey = person.getSurvey();
		assertEquals(rating, personSurvey.getRating());
	}
	
	
	@Test
	public void testCreateSurveyNegativeRating() {
		assertEquals(0, service.getAllSurveys().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "TI";
		Person person = service.createPerson(department, conversation, language);
		
		Integer helpsWork = 2, replacesService = 6, rating = -5;
		String error = null;
		
		try {
			service.createSurvey(helpsWork, replacesService, rating, person);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		List<Survey> allSurveys = service.getAllSurveys();
		assertEquals(0, allSurveys.size());
		assertEquals(error, "Ratings cannot be negative");
	}
	
	
	
	
	//*************************************************************************************************************//
	//********************************************AUDIO AND TEXT TESTS*********************************************//
	//*************************************************************************************************************//
	
	@Test
	public void testCreateOriginalAudio() {
		assertEquals(0, service.getAllAudios().size());
		assertEquals(0, service.getAllOriginalAudios().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "TI";
		Person person = service.createPerson(department, conversation, language);
		
		String message = "blablabla";
		
		try {
			service.createOriginalAudio(message, person);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Audio> allAudios = service.getAllAudios();
		assertEquals(1, allAudios.size());
		List<Audio> allOriginalAudios = service.getAllOriginalAudios();
		assertEquals(1, allOriginalAudios.size());
		List<Audio> allTranslatedAudios = service.getAllTranslatedAudios();
		assertEquals(0, allTranslatedAudios.size());
		assertEquals(message, allOriginalAudios.get(0).getMessage());
		
		Set<Audio> personAudioSet = person.getOriginalAudio();
		List<Audio> personAudio = service.toList(personAudioSet);
		assertEquals(1, personAudio.size());
		assertEquals(message, personAudio.get(0).getMessage());
	}
	
	
	@Test
	public void testCreateOriginalText() {
		assertEquals(0, service.getAllTexts().size());
		assertEquals(0, service.getAllOriginalTexts().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "TI";
		Person person = service.createPerson(department, conversation, language);
		
		String audioMessage = "blablabla";
		Audio audio = service.createOriginalAudio(audioMessage, person);
		
		String textMessage = "BlaBlaBla";
		
		try {
			service.createOriginalText(textMessage, person, audio);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Text> allTexts = service.getAllTexts();
		assertEquals(1, allTexts.size());
		List<Text> allOriginalTexts = service.getAllOriginalTexts();
		assertEquals(1, allOriginalTexts.size());
		List<Text> allTranslatedTexts = service.getAllTranslatedTexts();
		assertEquals(0, allTranslatedTexts.size());
		assertEquals(textMessage, allOriginalTexts.get(0).getMessage());
		
		Set<Text> personTextSet = person.getOriginalText();
		List<Text> personText = service.toList(personTextSet);
		assertEquals(1, personText.size());
		assertEquals(textMessage, personText.get(0).getMessage());
		assertEquals(audio.getMessage(), personText.get(0).getMatchingAudio().getMessage());
		assertEquals(audio.getMatchingText().getMessage(), personText.get(0).getMessage());
	}
	
	
	@Test
	public void testCreateTranslatedText() {
		assertEquals(0, service.getAllTexts().size());
		assertEquals(0, service.getAllOriginalTexts().size());
		assertEquals(0, service.getAllTranslatedTexts().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "TI";
		Person person = service.createPerson(department, conversation, language);
		
		String audioMessage = "blablabla";
		Audio audio = service.createOriginalAudio(audioMessage, person);
		
		String textMessage = "BlaBlaBla";
		Text originalText = service.createOriginalText(textMessage, person, audio);
		
		String translatedMessage = "BluBluBlu";
		
		try {
			service.createTranslatedText(translatedMessage, person, originalText);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Text> allTexts = service.getAllTexts();
		assertEquals(2, allTexts.size());
		List<Text> allOriginalTexts = service.getAllOriginalTexts();
		assertEquals(1, allOriginalTexts.size());
		List<Text> allTranslatedTexts = service.getAllTranslatedTexts();
		assertEquals(1, allTranslatedTexts.size());
		assertEquals(translatedMessage, allTranslatedTexts.get(0).getMessage());
		
		Set<Text> personTextSet = person.getTranslatedText();
		List<Text> personText = service.toList(personTextSet);
		assertEquals(1, personText.size());
		assertEquals(translatedMessage, personText.get(0).getMessage());
		assertEquals(originalText.getMessage(), personText.get(0).getTranslatedText().getMessage());
		assertEquals(originalText.getTranslatedText().getMessage(), personText.get(0).getMessage());
	}
	
	
	@Test
	public void testCreateTranslatedAudio() {
		assertEquals(0, service.getAllAudios().size());
		assertEquals(0, service.getAllOriginalAudios().size());
		assertEquals(0, service.getAllTranslatedAudios().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Conversation conversation = service.createConversation(manager);
		
		String name = "English";
		Language language = service.createLanguage(name, manager);

		String department = "TI";
		Person person = service.createPerson(department, conversation, language);
		
		String audioMessage = "blablabla";
		Audio originalAudio = service.createOriginalAudio(audioMessage, person);
		
		String textMessage = "BlaBlaBla";
		Text originalText = service.createOriginalText(textMessage, person, originalAudio);
		
		String translatedMessage = "BluBluBlu";
		Text translatedText = service.createTranslatedText(translatedMessage, person, originalText);
		
		String translatedAudio = "blublublu";
		
		try {
			service.createTranslatedAudio(translatedAudio, person, translatedText);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Audio> allAudios = service.getAllAudios();
		assertEquals(2, allAudios.size());
		List<Audio> allOriginalAudios = service.getAllOriginalAudios();
		assertEquals(1, allOriginalAudios.size());
		List<Audio> allTranslatedAudios = service.getAllTranslatedAudios();
		assertEquals(1, allTranslatedAudios.size());
		assertEquals(translatedAudio, allTranslatedAudios.get(0).getMessage());
		
		Set<Audio> personAudioSet = person.getTranslatedAudio();
		List<Audio> personAudio = service.toList(personAudioSet);
		assertEquals(1, personAudio.size());
		assertEquals(translatedAudio, personAudio.get(0).getMessage());
		assertEquals(translatedText.getMessage(), personAudio.get(0).getMatchingText().getMessage());
		assertEquals(translatedText.getMatchingAudio().getMessage(), personAudio.get(0).getMessage());
	}
}
