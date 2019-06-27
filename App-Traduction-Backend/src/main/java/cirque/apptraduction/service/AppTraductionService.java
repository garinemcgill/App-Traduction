package cirque.apptraduction.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	 * 		adds conversation to list in manager */
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
	
	@Transactional
	public Audio createAudio(String message, Boolean isOriginal, Person person) {
		Audio audio = new Audio();
		audio.setMessage(message);
		audio.setIsOriginal(isOriginal);
		audio.setPerson(person);
		audioRepository.save(audio);
		return audio;
	}
	
	@Transactional
	public Text createText(String message, Boolean isOriginal, Person person) {
		Text text = new Text();
		text.setMessage(message);
		text.setIsOriginal(isOriginal);
		text.setPerson(person);
		textRepository.save(text);
		return text;
	}
	
	
	
	
	
	
	
	
}
