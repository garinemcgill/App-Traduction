package cirque.apptraduction.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cirque.apptraduction.Model.Person;
import cirque.apptraduction.Model.Audio;
import cirque.apptraduction.Model.Conversation;
import cirque.apptraduction.Model.Language;
import cirque.apptraduction.Model.Survey;
import cirque.apptraduction.Model.Text;
import cirque.apptraduction.Model.TraductionAppManager;


@Repository
public class AppTraductionRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Person createPerson(String department) {
		Person p = new Person();
		p.setDepartment(department);
		entityManager.persist(p);
		return p;
	}

	@Transactional
	public Person getPerson(String name) {
		Person p = entityManager.find(Person.class, name);
		return p;
	}



}
