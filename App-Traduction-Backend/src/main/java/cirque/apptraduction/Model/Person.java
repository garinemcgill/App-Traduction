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
	
	
	
	private Set<Audio> originalAudio;

	@OneToMany(mappedBy="person" , cascade={CascadeType.ALL})
	public Set<Audio> getOriginalAudio() {
		return this.originalAudio;
	}

	public void setOriginalAudio(Set<Audio> originalAudios) {
		this.originalAudio = originalAudios;
	}

	
	
	private Set<Text> originalText;

	@OneToMany(mappedBy="person" , cascade={CascadeType.ALL})
	public Set<Text> getOriginalText() {
		return this.originalText;
	}

	public void setOriginalText(Set<Text> originalTexts) {
		this.originalText = originalTexts;
	}
	
	
	
	private Set<Text> translatedText;

	@OneToMany(mappedBy="person" , cascade={CascadeType.ALL})
	public Set<Text> getTranslatedText() {
		return this.translatedText;
	}

	public void setTranslatedText(Set<Text> translatedTexts) {
		this.translatedText = translatedTexts;
	}
	
	
	
	private Set<Audio> translatedAudio;

	@OneToMany(mappedBy="person" , cascade={CascadeType.ALL})
	public Set<Audio> getTranslatedAudio() {
		return this.translatedAudio;
	}

	public void setTranslatedAudio(Set<Audio> translatedAudios) {
		this.translatedAudio = translatedAudios;
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
