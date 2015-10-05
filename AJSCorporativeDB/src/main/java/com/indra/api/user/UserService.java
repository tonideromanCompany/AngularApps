package com.indra.api.user;

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
public class UserService {
	 
	 @Autowired
	 protected UserRepository repository;
	 
	 public void createUser(User user) {
	 repository.createUser(user);
	 }
	 
	 public void updateUser(User user) {
		 repository.updateUser(user);
	 }
	 
	 public void deleteUser(User user) {
		 repository.deleteUser(user);
	 }
	 
	 public List<User> getUsers() {
	 return repository.getUsers();
	 }
	 
	 public List<User> getUsersSearch(String term) {
		 return repository.getUsersSearch(term);
		 }
	 
	 public List<User> getUsersById(int iduser) {
		 return repository.getUsersById(iduser);
		 }
	 
	 public List<User> getLogin(String email, String password) {
		 return repository.getLogin(email, password);
	 }
	 
	 public List<User> getUsersByEmail(String email) {
		 return repository.getUsersByEmail(email);
		 }
}
