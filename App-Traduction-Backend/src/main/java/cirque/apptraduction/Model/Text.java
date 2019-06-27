package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public class Text{
   private Audio correspondingAudio;
   
   @OneToOne
   public Audio getCorrespondingAudio() {
      return this.correspondingAudio;
   }
   
   public void setCorrespondingAudio(Audio correspondingAudio) {
      this.correspondingAudio = correspondingAudio;
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
private Boolean isSentByPerson;

public void setIsSentByPerson(Boolean value) {
    this.isSentByPerson = value;
}
public Boolean getIsSentByPerson() {
    return this.isSentByPerson;
}
}
