package cirque.apptraduction.dao;

import org.springframework.data.repository.CrudRepository;

import cirque.apptraduction.Model.*;

public interface SurveyRepository extends CrudRepository <Survey, Integer>{

	Survey findSurveyById(int id);
	boolean existsById(int id);
	
}
