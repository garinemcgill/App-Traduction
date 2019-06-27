package cirque.apptraduction.dao;

import org.springframework.data.repository.CrudRepository;

import cirque.apptraduction.Model.*;

public interface LanguageRepository extends CrudRepository <Language, String>{

	Language findLanguageByName(String name);
	boolean existsByName(String name);
	
}
