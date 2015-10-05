package com.indra.api.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indra.api.message.Message;
import com.indra.api.message.MessageService;
import com.indra.api.notification.Notification;
import com.indra.api.notification.Notification.Type;
import com.indra.api.security.Auth;
import com.indra.api.user.User;
import com.indra.api.user.UserService;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
public class MessagesApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessagesApiController.class);
	
	private Type type;
	
	@Autowired
	protected MessageService messageservice;
	
	@Autowired
	protected UserService userservice;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	 /*@Autowired
	 public MessagesApiController(SimpMessagingTemplate messagingTemplate) {
		 super();
		 this.messagingTemplate=messagingTemplate;
	 }*/
	
	@Auth
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
	public @ResponseBody List<Message> getMessages(@PathVariable(value="id") int id){
		List<Message> messageslist = messageservice.getMessagesbyUser(id);
		return messageslist;
	}

	@Auth
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setMessages(@RequestBody Message message){
			messageservice.createMessage(message);
			User us = userservice.getUsersById(message.getIduserto()).get(0);
			Notification notification = new Notification();
			notification.setIduserto(us.getId()); notification.setIduserfrom(message.getIduserfrom());
			notification.setType(type.MESSAGE); notification.setTime(getCurrentTime());
			LOGGER.info("NOTIFICATION: Type "+notification.getType()+" Time "+notification.getTime()+" To: "+us.getName());
			this.messagingTemplate.convertAndSend("/topic/"+us.getName()+"/notifications", notification);
			return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	public Timestamp getCurrentTime() {
		Date date = new Date();
		return new Timestamp(date.getTime());
	}
}
