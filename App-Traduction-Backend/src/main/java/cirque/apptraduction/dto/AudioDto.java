package cirque.apptraduction.dto;

public class AudioDto {
	
	private Boolean isOriginal;
	private String message;
	private PersonDto person;
	private TextDto matchingText;
	private int id;
	
	public AudioDto() {
	}

	public AudioDto(String message, PersonDto person, int id) {
		this.message = message;
		this.person = person;
		this.isOriginal = true;
		this.id = id;
	}
	
	public AudioDto(String message, PersonDto person, TextDto matchingText, int id) {
		this.message = message;
		this.person = person;
		this.matchingText = matchingText;
		this.isOriginal = false;
		this.id = id;
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
	
	public int getId() {
		return this.id;
	}
}
