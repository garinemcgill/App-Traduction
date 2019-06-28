package cirque.apptraduction.dto;

public class TextDto {
	
	private Boolean isOriginal;
	private String message;
	private PersonDto person;
	private AudioDto matchingAudio;
	private TextDto translatedText;
	
	public TextDto() {
	}

	public TextDto(String message, PersonDto person, TextDto translatedText) {
		this.message = message;
		this.person = person;
		this.translatedText = translatedText;
		this.isOriginal = false;
	}
	
	public TextDto(String message, PersonDto person, AudioDto matchingAudio) {
		this.message = message;
		this.person = person;
		this.matchingAudio = matchingAudio;
		this.isOriginal = true;
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
	
	public AudioDto getMatchingAudio() {
		return this.matchingAudio;
	}
	
	public TextDto getTranslatedText() {
		return this.translatedText;
	}
}
