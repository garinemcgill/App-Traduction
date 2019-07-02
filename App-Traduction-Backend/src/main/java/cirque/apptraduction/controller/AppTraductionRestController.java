package cirque.apptraduction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
