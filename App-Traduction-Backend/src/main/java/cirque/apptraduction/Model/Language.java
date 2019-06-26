package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Language{
   private TraductionAppManager traductionAppManager;
   
   @ManyToOne(optional=false)
   public TraductionAppManager getTraductionAppManager() {
      return this.traductionAppManager;
   }
   
   public void setTraductionAppManager(TraductionAppManager traductionAppManager) {
      this.traductionAppManager = traductionAppManager;
   }
   
   private Set<Person> person;
   
   @OneToMany(mappedBy="language" )
   public Set<Person> getPerson() {
      return this.person;
   }
   
   public void setPerson(Set<Person> persons) {
      this.person = persons;
   }
   
   private LanguageName name;

public void setName(LanguageName value) {
    this.name = value;
}
@Id
public LanguageName getName() {
    return this.name;
}
}
