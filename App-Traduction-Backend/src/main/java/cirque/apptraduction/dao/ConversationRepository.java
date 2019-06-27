package cirque.apptraduction.dao;

import org.springframework.data.repository.CrudRepository;

import cirque.apptraduction.Model.*;

public interface ConversationRepository extends CrudRepository <Conversation, Integer>{

	Conversation findConversationById(int id);
	boolean existsById(int id);
	
}
