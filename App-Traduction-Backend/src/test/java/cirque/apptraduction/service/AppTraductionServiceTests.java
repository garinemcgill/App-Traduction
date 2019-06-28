package cirque.apptraduction.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import cirque.apptraduction.service.*;

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
