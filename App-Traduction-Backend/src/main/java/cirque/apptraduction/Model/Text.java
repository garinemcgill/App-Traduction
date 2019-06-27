package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Text{
	
	private Audio matchingAudio;

	@OneToOne
	public Audio getMatchingAudio() {
		return this.matchingAudio;
	}

	public void setMatchingAudio(Audio matchingAudio) {
		this.matchingAudio = matchingAudio;
	}
	
	
	
	private Text translatedText;

	@OneToOne(mappedBy="translatedText" )
	public Text getTranslatedText() {
		return this.translatedText;
	}

	public void setTranslatedText(Text translatedText) {
		this.translatedText = translatedText;
	}

	
	
	private Person person;

	@ManyToOne(optional=false)
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	
	
	private String message;

	public void setMessage(String value) {
		this.message = value;
	}
	public String getMessage() {
		return this.message;
	}
	
	
	
	private Integer id;

	public void setId(Integer value) {
		this.id = value;
	}
	@Id
	@GeneratedValue
	public Integer getId() {
		return this.id;
	}
	
	
	
	private Boolean isOriginal;

	public void setIsOriginal(Boolean value) {
		this.isOriginal = value;
	}
	public Boolean getIsOriginal() {
		return this.isOriginal;
	}
	
}
