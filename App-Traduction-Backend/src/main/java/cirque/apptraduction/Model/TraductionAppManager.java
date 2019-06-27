package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class TraductionAppManager{

	private Set<Conversation> conversation;

	@OneToMany(mappedBy="traductionAppManager" , cascade={CascadeType.ALL})
	public Set<Conversation> getConversation() {
		return this.conversation;
	}

	public void setConversation(Set<Conversation> conversations) {
		this.conversation = conversations;
	}



	private Set<Language> language;

	@OneToMany(mappedBy="traductionAppManager" , cascade={CascadeType.ALL})
	public Set<Language> getLanguage() {
		return this.language;
	}

	public void setLanguage(Set<Language> languages) {
		this.language = languages;
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
	
}
