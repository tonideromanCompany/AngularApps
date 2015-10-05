package com.indra.api.event;

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
public class EventService {
	
	 @Autowired
	 protected EventRepository repository;
	 
	 public void createEvent(Event event) {
	 repository.createEvent(event);
	 }
	 
	 public void updateEvent(Event event) {
		 repository.updateEvent(event);
	 }
	 
	 public void deleteEvent(Event event) {
		 repository.deleteEvent(event);
	 }
	 
	 public List<Event> getEvents() {
	 return repository.getEvents();
	 }
	 
	 public List<Event> getEventsByUser(int iduser) {
		 return repository.getEventsByUser(iduser);
		 }
	 
	 public List<Event> getEventsById(int id) {
		 return repository.getEventsById(id);
		 }
}
