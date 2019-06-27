package cirque.apptraduction.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cirque.apptraduction.Model.*;
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
	
	
	@Transactional
	public TraductionAppManager createTraductionAppManager() {
		TraductionAppManager manager = new TraductionAppManager();
		managerRepository.save(manager);
		return manager;
	}

	@Transactional
	public Language createLanguage(String name, TraductionAppManager manager) {
		if(name == null || name == "") {
			throw new IllegalArgumentException("The name of a language cannot be null or empty");
		}
		Language language = new Language();
		language.setName(name);
		language.setTraductionAppManager(manager);
		languageRepository.save(language);
		return language;
	}
	
	@Transactional
	public Conversation createConversation(Date date, Time time, Boolean withGoogle, TraductionAppManager manager) {
		if(date == null || time == null) {
			throw new IllegalArgumentException("The date and time cannot be null");
		}
		Conversation conversation = new Conversation();
		conversation.setDate(date);
		conversation.setTime(time);
		conversation.setWithGoogle(withGoogle);
		conversation.setTraductionAppManager(manager);
		conversationRepository.save(conversation);
		return conversation;
	}
	
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
		return person;
	}
	
	@Transactional
	public Survey createSurvey(int helpsWork, int replacesService, int rating, Person person) {
		Survey survey = new Survey();
		survey.setHelpsWork(helpsWork);
		survey.setReplacesService(replacesService);
		survey.setRating(rating);
		survey.setPerson(person);
		surveyRepository.save(survey);
		return survey;
	}
	
	@Transactional
	public Audio createAudio(String message, Boolean isSent, Person person) {
		Audio audio = new Audio();
		audio.setMessage(message);
		audio.setIsSentByPerson(isSent);
		audio.setPerson(person);
		audioRepository.save(audio);
		return audio;
	}
	
	@Transactional
	public Text createText(String message, Boolean isSent, Person person) {
		Text text = new Text();
		text.setMessage(message);
		text.setIsSentByPerson(isSent);
		text.setPerson(person);
		textRepository.save(text);
		return text;
	}
	
}
