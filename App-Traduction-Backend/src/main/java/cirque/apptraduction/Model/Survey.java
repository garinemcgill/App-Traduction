package cirque.apptraduction.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;
import javax.persistence.Id;

@Entity
public class Survey{
   private Person person;
   
   @OneToOne(optional=false)
   public Person getPerson() {
      return this.person;
   }
   
   public void setPerson(Person person) {
      this.person = person;
   }
   
   private Integer helpsWork;

public void setHelpsWork(Integer value) {
    this.helpsWork = value;
}
public Integer getHelpsWork() {
    return this.helpsWork;
}
private Integer replacesService;

public void setReplacesService(Integer value) {
    this.replacesService = value;
}
public Integer getReplacesService() {
    return this.replacesService;
}
private Integer rating;

public void setRating(Integer value) {
    this.rating = value;
}
public Integer getRating() {
    return this.rating;
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
