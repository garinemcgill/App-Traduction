package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@Entity
public class Person{
	
	private Conversation conversation;

	@ManyToOne(optional=false)
	public Conversation getConversation() {
		return this.conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	
	
	private Language language;

	@ManyToOne
	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	
	
	private Set<Text> text;

	@OneToMany(mappedBy="person" , cascade={CascadeType.ALL})
	public Set<Text> getText() {
		return this.text;
	}

	public void setText(Set<Text> texts) {
		this.text = texts;
	}

	
	
	private Set<Audio> audio;

	@OneToMany(mappedBy="person" , cascade={CascadeType.ALL})
	public Set<Audio> getAudio() {
		return this.audio;
	}

	public void setAudio(Set<Audio> audios) {
		this.audio = audios;
	}

	
	
	private Survey survey;

	@OneToOne(mappedBy="person" , cascade={CascadeType.ALL})
	public Survey getSurvey() {
		return this.survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
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
	
	
	
	private String department;

	public void setDepartment(String value) {
		this.department = value;
	}
	public String getDepartment() {
		return this.department;
	}
	
}
