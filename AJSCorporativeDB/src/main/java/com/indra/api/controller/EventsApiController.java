package com.indra.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indra.api.event.Event;
import com.indra.api.event.EventService;
import com.indra.api.security.Auth;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
public class EventsApiController {
	
	@Autowired
	protected EventService eventservice;

	@Auth
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	public @ResponseBody List<Event> getEvents(){
		List<Event> eventlist = eventservice.getEvents();
		return eventlist;
	}
	
	@Auth
	@RequestMapping(value = "/events", method = RequestMethod.POST)
	public @ResponseBody void setEvent(@RequestBody Event event) {
		eventservice.createEvent(event);
	}
	
	@Auth
	@RequestMapping(value = "/event", method = RequestMethod.POST)
	public @ResponseBody void updateEvent(@RequestBody Event event) {
		eventservice.updateEvent(event);
	}
	
	@Auth
	@RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteEvent(@PathVariable("id") int id) {
		List<Event> eventlist = eventservice.getEventsById(id);
		eventservice.deleteEvent(eventlist.get(0));
	}
}
