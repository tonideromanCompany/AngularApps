package com.indra.api.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author arommartinez
 *
 */

@Transactional
@Service
public class MessageService {
	
	@Autowired
	 protected MessageRepository repository;
	 
	 public void createMessage(Message message) {
	 repository.createMessage(message);
	 }
	 
	 public void updateMessage(Message message) {
		 repository.updateMessage(message);
	 }
	 
	 public void deleteMessage(Message message) {
		 repository.deleteContact(message);
	 }
	 
	 public List<Message> getMessages() {
		 return repository.getMessages();
	 }
	 
	 public List<Message> getMessagesbyUser(int iduserto) {
	 return repository.getMessagesByUser(iduserto);
	 }

}
