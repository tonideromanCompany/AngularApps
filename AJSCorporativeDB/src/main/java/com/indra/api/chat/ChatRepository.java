package com.indra.api.chat;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.indra.api.chat.Chat.State;

/**
 * 
 * @author arommartinez
 *
 */

@Repository
public class ChatRepository {
	
	 @Autowired
	 protected SessionFactory sessionFactory;
	 
	 public void createChat(Chat chat) {
	 sessionFactory.getCurrentSession().save(chat);
	 }
	 
	 public void updateChat(Chat chat) {
		 sessionFactory.getCurrentSession().saveOrUpdate(chat);
	 }
	 
	 public void deleteChat(Chat chat) {
		 sessionFactory.getCurrentSession().delete(chat);
	 }
	 
	 //QUERY LIST FUNCTIONS
	 
	 @SuppressWarnings("unchecked")
	 public List<Chat> getChats() {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Chat c")
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Chat> getChatsByUser(int iduser) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Chat c WHERE iduserfrom=:ID OR iduserto=:ID")
	  .setInteger("ID", iduser)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Chat> getChatsByUserByState(int iduser, State state) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Chat c WHERE iduserfrom=:ID OR iduserto=:ID AND state=:ST")
	  .setInteger("ID", iduser)
	  .setParameter("ST", state)
	  .list();
	 }
}
