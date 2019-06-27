package cirque.apptraduction.dao;

import org.springframework.data.repository.CrudRepository;

import cirque.apptraduction.Model.*;

public interface PersonRepository extends CrudRepository <Person, Integer>{

	Person findPersonById(int id);
	boolean existsById(int id);

}
