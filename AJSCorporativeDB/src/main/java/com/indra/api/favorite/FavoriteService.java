package com.indra.api.favorite;

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
public class FavoriteService {
	
	@Autowired
	 protected FavoriteRepository repository;
	 
	 public void createFavorite(Favorite favorite) {
	 repository.createFavorite(favorite);
	 }
	 
	 public void updateFavorite(Favorite favorite) {
		 repository.updateFavorite(favorite);
	 }
	 
	 public void deleteFavorite(Favorite favorite) {
		 repository.deleteFavorite(favorite);
	 }
	 
	 public List<Favorite> getFavorites() {
		 return repository.getFavorites();
	 }
	 
	 public List<Favorite> getFavoritesbyUser(int iduser) {
	 return repository.getFavoritesByUser(iduser);
	 }
	 
	 public List<Favorite> getFavoritesByUserById(int iduser, int idfavorite) {
		 return repository.getFavoritesByUserById(iduser, idfavorite);
		 }
}
