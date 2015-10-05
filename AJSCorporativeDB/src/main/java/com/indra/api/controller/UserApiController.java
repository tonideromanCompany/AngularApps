package com.indra.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.indra.api.event.Event;
import com.indra.api.event.EventService;
import com.indra.api.favorite.Favorite;
import com.indra.api.favorite.FavoriteService;
import com.indra.api.security.Auth;
import com.indra.api.security.AuthService;
import com.indra.api.user.User;
import com.indra.api.user.UserService;

/**
 * 
 * @author arommartinez
 *
 */

@Controller
public class UserApiController {
	
	@Autowired
	protected UserService userservice;
	
	@Autowired
	protected EventService eventservice;
	
	
	@Autowired
	protected FavoriteService favoriteservice;
	
	@Autowired
	AuthService authservice;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsers(){
		List<User> userlist = userservice.getUsers();
		return userlist;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setUser(@RequestBody User user) {
		List<User> userlist = userservice.getUsersByEmail(user.getEmail());
		if(userlist.size()==0) {
		userservice.createUser(user);
		List<User> us = userservice.getUsersByEmail(user.getEmail());
		return new ResponseEntity<Integer>(us.get(0).getId(), HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@Auth
	@RequestMapping(value = "/users/search", method = RequestMethod.GET)
	public @ResponseBody List<User> getUsersSearch(@RequestParam(value="term") String term) {
		List<User> userlist = userservice.getUsersSearch(term);
		return userlist;
	}
	
	@Auth
	@RequestMapping(value = "users/{id}/events", method = RequestMethod.GET)
	public @ResponseBody List<Event> getUserEvents(@PathVariable(value="id") int id){
		List<Event> eventlist = eventservice.getEventsByUser(id);
		return eventlist;
	}
	
	@Auth
	@RequestMapping(value = "/user/{id}/favorite", method = RequestMethod.GET)
	public @ResponseBody List<User> getFavorites(@PathVariable(value = "id") int id) {
		List<Favorite> favlist = favoriteservice.getFavoritesbyUser(id);
		List<User> userlist = new ArrayList<User>();
		for(int i=0; i<favlist.size();i++){
			List<User> tmplist = userservice.getUsersById(favlist.get(i).getIdcontact());
			userlist.add(tmplist.get(0));
		}
		return userlist;
	}
	
	@Auth
	@RequestMapping(value = "/user/{id}/favorite", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> setFavorite(@PathVariable(value = "id") int id, @RequestBody int idcontact) {
		Favorite fav = new Favorite();
		fav.setIduser(id);
		fav.setIdcontact(idcontact);
		favoriteservice.createFavorite(fav);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
	}
	
	@Auth
	@RequestMapping(value = "/user/{id}/favorite/{idfav}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteFavorite(@PathVariable(value = "id") int id, @PathVariable(value = "idfav") int idfav) {
		Favorite fav = favoriteservice.getFavoritesByUserById(id, idfav).get(0);
		favoriteservice.deleteFavorite(fav);
	}
}
