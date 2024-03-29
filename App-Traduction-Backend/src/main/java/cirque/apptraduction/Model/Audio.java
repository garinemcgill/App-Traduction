package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Audio{
	
	private Text matchingText;

	@OneToOne(mappedBy="matchingAudio" )
	public Text getMatchingText() {
		return this.matchingText;
	}

	public void setMatchingText(Text matchingText) {
		this.matchingText = matchingText;
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
