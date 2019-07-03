package cirque.apptraduction.dto;

public class PersonDto {

	private ConversationDto conversation;
	private LanguageDto language;
	private String department;
	private int id;
	
	public PersonDto() {
	}
	
	public PersonDto(String department, ConversationDto conversation, LanguageDto language, int id) {
		this.conversation = conversation;
		this.language = language;
		this.department = department;
		this.id = id;
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
	
	public int getId() {
		return this.id;
	}
}
