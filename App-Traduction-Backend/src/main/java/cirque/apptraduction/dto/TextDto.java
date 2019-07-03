package cirque.apptraduction.dto;

public class TextDto {
	
	private Boolean isOriginal;
	private String message;
	private PersonDto person;
	private AudioDto matchingAudio;
	private TextDto translatedText;
	private int id;
	
	public TextDto() {
	}

	public TextDto(String message, PersonDto person, TextDto translatedText, int id) {
		this.message = message;
		this.person = person;
		this.translatedText = translatedText;
		this.isOriginal = false;
		this.id = id;
	}
	
	public TextDto(String message, PersonDto person, AudioDto matchingAudio, int id) {
		this.message = message;
		this.person = person;
		this.matchingAudio = matchingAudio;
		this.isOriginal = true;
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
	
	public AudioDto getMatchingAudio() {
		return this.matchingAudio;
	}
	
	public TextDto getTranslatedText() {
		return this.translatedText;
	}
	
	public int getId() {
		return this.id;
	}
}
