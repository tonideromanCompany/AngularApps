package com.indra.api.controller;

import java.util.ArrayList;
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

import com.indra.api.chat.Chat;
import com.indra.api.chat.ChatMessage;
import com.indra.api.chat.ChatService;
import com.indra.api.chat.ConnectionService;
import com.indra.api.contact.Contact;
import com.indra.api.contact.ContactService;
import com.indra.api.user.User;
import com.indra.api.user.UserService;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
public class ChatApiController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatApiController.class);
	
	@Autowired
	ConnectionService connectionservice;
	
	@Autowired
	protected ChatService chatservice;
	
	@Autowired
	protected UserService userservice;
	
	@Autowired
	protected ContactService contactservice;
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getChats() {
		List<Chat> chatlist = chatservice.getChats();
		return new ResponseEntity<List<Chat>>(chatlist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setChat(@RequestBody Chat chat) {
		//chatservice.updateChat(chat);
		LOGGER.info("/topic/"+chat.getIduserfrom()+"/chats/"+chat.getIduserto());
		if(connectionservice.getConnections(chat.getIduserto())!= null){
			this.messagingTemplate.convertAndSend("/topic/"+chat.getIduserfrom()+"/chats/"+chat.getIduserto(), 1);
		} 
		else
			this.messagingTemplate.convertAndSend("/topic/"+chat.getIduserfrom()+"/chats/"+chat.getIduserto(), 0);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/chat/{id}/message", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setMessage(@RequestBody ChatMessage message) {
		if(connectionservice.getConnections(message.getIduserto())!=null){
		this.messagingTemplate.convertAndSend("/topic/"+message.getIduserto()+"/chats/"+message.getIduserfrom(), message);
		//chatservice.createMessage(message)
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
		}
		else {
			LOGGER.error("The user it is not connected. Impossible send the message");
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/chat/user/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getChatsByUser(@PathVariable(value="id") int id) {
		List<Chat> chatlist = chatservice.getChatsbyUser(id);
		return new ResponseEntity<List<Chat>>(chatlist, HttpStatus.OK);
	}
	
	/* Set the user in the connection cache if it isn't it.
	 * List the connected user's contacts and send the list.
	 */
	@RequestMapping(value = "/chat/user/{id}/connect", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setUserConnection(@PathVariable(value="id") int id) {
		User user = userservice.getUsersById(id).get(0);
		List<User> userlist = new ArrayList<User>();
		if(connectionservice.getConnections(id)==null) {
			connectionservice.setConnected(user, id);
			LOGGER.info("New connection in ConnectionCache, identifier:"+user.getId());
			SendNewConnection(id);
		}
		List<Contact> contactlist = contactservice.getContactsbyUser(id);
		for(int i=0; i<contactlist.size(); i++) {
			int idcontact = contactlist.get(i).getIdcontact();
			if(connectionservice.getConnections(idcontact)!=null) {
				userlist.add(connectionservice.getConnections(idcontact));
			}
		}
		return new ResponseEntity<List<User>>(userlist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/chat/user/{id}/disconnect", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setUserDisConnection(@PathVariable(value="id") int id) {
		connectionservice.setDisconnected(id);
		LOGGER.info("Disconnection in ConnectionCache, identifier:"+id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	/**
	 * FUNCTIONS
	 * 
	 */
	
	public void SendNewConnection(int id) {
		List<Contact> contactlist = contactservice.getContactsbyUser(id);
		for(int i=0; i<contactlist.size(); i++) {
			int idcontact = contactlist.get(i).getIdcontact();
			if(connectionservice.getConnections(idcontact)!=null) {
				LOGGER.info("There is a connected contact: "+idcontact);
				this.messagingTemplate.convertAndSend("/topic/"+idcontact+"/chats",connectionservice.getConnections(id));
			}
		}
	}
	
}
