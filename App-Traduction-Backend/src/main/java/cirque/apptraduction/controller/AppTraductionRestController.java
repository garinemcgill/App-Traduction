package cirque.apptraduction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	
	
	@PostMapping(value = { "/newPerson/{department}/{conversation}/{language}", "/newPerson/{department}/{conversation}/{language}/"})
	public PersonDto createPerson(@PathVariable("department") String department, @PathVariable("conversation") int conversationId, 
			@PathVariable("language") String languageName) throws IllegalArgumentException {	
		
		Conversation conversation = service.getConversation(conversationId);
		Language language = service.getLanguage(languageName);
		
		Person person = service.createPerson(department, conversation, language);
		return convertToDto(person);
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
	
}
