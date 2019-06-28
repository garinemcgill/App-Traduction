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
public class AppTraductionServiceRelationshipTests {

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
	
	
}
