package cirque.apptraduction.dto;

public class AudioDto {
	
	private Boolean isOriginal;
	private String message;
	private PersonDto person;
	private TextDto matchingText;
	
	public AudioDto() {
	}

	public AudioDto(String message, PersonDto person) {
		this.message = message;
		this.person = person;
		this.isOriginal = true;
	}
	
	public AudioDto(String message, PersonDto person, TextDto matchingText) {
		this.message = message;
		this.person = person;
		this.matchingText = matchingText;
		this.isOriginal = false;
	}
	
	public Boolean getIsOriginal() {
		return this.isOriginal;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public PersonDto getPerson() {
		return this.person;
	}
	
	public TextDto getMatchingText() {
		return this.matchingText;
	}
}
