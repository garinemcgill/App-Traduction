package cirque.apptraduction.dao;

import org.springframework.data.repository.CrudRepository;

import cirque.apptraduction.Model.*;

public interface TraductionAppManagerRepository extends CrudRepository <TraductionAppManager, Integer>{

	TraductionAppManager findTraductionAppManagerById(int id);
	boolean existsById(int id);
	
}
