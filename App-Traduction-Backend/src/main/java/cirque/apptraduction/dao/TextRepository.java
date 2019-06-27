package cirque.apptraduction.dao;

import org.springframework.data.repository.CrudRepository;

import cirque.apptraduction.Model.*;

public interface TextRepository extends CrudRepository <Text, Integer>{

	Text findTextById(int id);
	boolean existsById(int id);
	
}
