package cirque.apptraduction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	
	/*
	@PostMapping(value = { "/newConversation", "/newConversation/" })
	public ConversationDto createConversation() throws IllegalArgumentException {
		
		TraductionAppManager manager = service.getManager();
		if (manager == null) {
			manager = service.createTraductionAppManager();
		}
		
		
		
		Conversation conversation = service.createConversation(manager);
		return convertToDto(conversation);
	}*/
	
	
	
	
	
	
	
	
}
