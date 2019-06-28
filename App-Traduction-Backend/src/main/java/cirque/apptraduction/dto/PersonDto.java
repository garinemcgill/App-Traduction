package cirque.apptraduction.dto;

public class PersonDto {

	private ConversationDto conversation;
	private LanguageDto language;
	private String department;
	
	public PersonDto() {
	}
	
	public PersonDto(String department, ConversationDto conversation, LanguageDto language) {
		this.conversation = conversation;
		this.language = language;
		this.department = department;
	}
	
	public String getDepartment() {
		return this.department;
	}
	
	public ConversationDto getConversation() {
		return this.conversation;
	}
	
	public LanguageDto getLanguage() {
		return this.language;
	}
}
