package com.indra.api.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indra.api.chat.Chat.State;

/**
 * 
 * @author arommartinez
 *
 */

@Transactional
@Service
public class ChatService {
	
	@Autowired
	 protected ChatRepository repository;
	 
	 public void createChat(Chat chat) {
	 repository.createChat(chat);
	 }
	 
	 public void updateChat(Chat chat) {
		 repository.updateChat(chat);
	 }
	 
	 public void deleteChat(Chat chat) {
		 repository.deleteChat(chat);
	 }
	 
	 public List<Chat> getChats() {
		 return repository.getChats();
	 }
	 
	 public List<Chat> getChatsbyUser(int iduser) {
		 return repository.getChatsByUser(iduser);
	 }
	 
	 
	 public List<Chat> getChatsbyUserbyState(int iduser, State state) {
		 return repository.getChatsByUserByState(iduser, state);
		 }
}
