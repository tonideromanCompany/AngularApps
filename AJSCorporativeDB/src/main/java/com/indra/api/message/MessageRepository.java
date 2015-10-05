package com.indra.api.message;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author arommartinez
 *
 */
@Repository
public class MessageRepository {
	
	 @Autowired
	 protected SessionFactory sessionFactory;
	 
	 public void createMessage(Message message) {
	 sessionFactory.getCurrentSession().save(message);
	 }
	 
	 public void updateMessage(Message message) {
		 sessionFactory.getCurrentSession().saveOrUpdate(message);
	 }
	 
	 public void deleteContact(Message message) {
		 sessionFactory.getCurrentSession().delete(message);
	 }
	 
	 //QUERY LIST FUNCTIONS
	 
	 @SuppressWarnings("unchecked")
	 public List<Message> getMessages() {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Message m")
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Message> getMessagesByUser(int iduserto) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Message m WHERE iduserto=:ID")
	  .setInteger("ID", iduserto)
	  .list();
	 }

}
