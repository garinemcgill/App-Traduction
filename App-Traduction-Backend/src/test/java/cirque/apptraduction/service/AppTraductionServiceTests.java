package cirque.apptraduction.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

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
		
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.JUNE, 13, 9, 00, 0);
		Date date = new Date(c.getTimeInMillis());
		Time time = new Time(c.getTimeInMillis());
		Boolean withGoogle = true;
		
		try {
			service.createConversation(date, time, withGoogle, manager);
		} catch (IllegalArgumentException e) {
			fail();
		}
		
		List<Conversation> allConversations = service.getAllConversations();
		assertEquals(1, allConversations.size());
		assertEquals(withGoogle, allConversations.get(0).getWithGoogle());
	}
	
	
	@Test
	public void testCreateConversationNullDate() {
		assertEquals(0, service.getAllConversations().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.JUNE, 13, 9, 00, 0);
		Time time = new Time(c.getTimeInMillis());
		Boolean withGoogle = true;
		Date date = null;
		
		String error = null;
		try {
			service.createConversation(date, time, withGoogle, manager);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "The date and time cannot be null");
		List<Conversation> allConversations = service.getAllConversations();
		assertEquals(0, allConversations.size());
	}
	
	
	@Test
	public void testCreateConversationNullTime() {
		assertEquals(0, service.getAllConversations().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.JUNE, 13, 9, 00, 0);
		Date date = new Date(c.getTimeInMillis());
		Boolean withGoogle = true;
		Time time = null;
		
		String error = null;
		try {
			service.createConversation(date, time, withGoogle, manager);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		assertEquals(error, "The date and time cannot be null");
		List<Conversation> allConversations = service.getAllConversations();
		assertEquals(0, allConversations.size());
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
		
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.JUNE, 13, 9, 00, 0);
		Date date = new Date(c.getTimeInMillis());
		Time time = new Time(c.getTimeInMillis());
		Boolean withGoogle = true;
		Conversation conversation = service.createConversation(date, time, withGoogle, manager);
		
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
	}
	
	
	@Test
	public void testCreatePersonNullDepartment() {
		assertEquals(0, service.getAllPersons().size());
		
		TraductionAppManager manager = service.createTraductionAppManager();
		
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.JUNE, 13, 9, 00, 0);
		Date date = new Date(c.getTimeInMillis());
		Time time = new Time(c.getTimeInMillis());
		Boolean withGoogle = true;
		Conversation conversation = service.createConversation(date, time, withGoogle, manager);
		
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
		
		Calendar c = Calendar.getInstance();
		c.set(2019, Calendar.JUNE, 13, 9, 00, 0);
		Date date = new Date(c.getTimeInMillis());
		Time time = new Time(c.getTimeInMillis());
		Boolean withGoogle = true;
		Conversation conversation = service.createConversation(date, time, withGoogle, manager);
		
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
}
