package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Conversation{

	private TraductionAppManager traductionAppManager;

	@ManyToOne(optional=false)
	public TraductionAppManager getTraductionAppManager() {
		return this.traductionAppManager;
	}

	public void setTraductionAppManager(TraductionAppManager traductionAppManager) {
		this.traductionAppManager = traductionAppManager;
	}



	private Set<Person> person;

	@OneToMany(mappedBy="conversation" , cascade={CascadeType.ALL})
	public Set<Person> getPerson() {
		return this.person;
	}

	public void setPerson(Set<Person> persons) {
		this.person = persons;
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
	
	
	
	private Date date;

	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
	}
	
	
	
	private Time time;

	public void setTime(Time value) {
		this.time = value;
	}
	public Time getTime() {
		return this.time;
	}
	
	
	
	private Boolean withGoogle;

	public void setWithGoogle(Boolean value) {
		this.withGoogle = value;
	}
	public Boolean getWithGoogle() {
		return this.withGoogle;
	}
	
}
