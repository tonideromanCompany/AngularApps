package com.indra.api.event;

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
public class EventRepository {
	
	 @Autowired
	 protected SessionFactory sessionFactory;
	 
	 public void createEvent(Event event) {
	 sessionFactory.getCurrentSession().save(event);
	 }
	 
	 public void updateEvent(Event event) {
		 sessionFactory.getCurrentSession().saveOrUpdate(event);
	 }
	 
	 public void deleteEvent(Event event) {
		 sessionFactory.getCurrentSession().delete(event);
	 }
	 
	 //QUERY LIST FUNCTIONS
	 
	 @SuppressWarnings("unchecked")
	 public List<Event> getEvents() {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Event e")
	  .list();
	 }

	 @SuppressWarnings("unchecked")
	 public List<Event> getEventsByUser(int iduser) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Event e WHERE iduser=:ID")
	  .setInteger("ID", iduser)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Event> getEventsById(int id) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Event e WHERE id=:ID")
	  .setInteger("ID", id)
	  .list();
	 }
}
