package cirque.apptraduction.dao;

import org.springframework.data.repository.CrudRepository;

import cirque.apptraduction.Model.*;

public interface AudioRepository extends CrudRepository <Audio, Integer>{
	
	Audio findAudioById(int id);
	boolean existsById(int id);
	
}
