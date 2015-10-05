package com.indra.api.favorite;

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
public class FavoriteRepository {
	
	@Autowired
	 protected SessionFactory sessionFactory;
	 
	 public void createFavorite(Favorite favorite) {
	 sessionFactory.getCurrentSession().save(favorite);
	 }
	 
	 public void updateFavorite(Favorite favorite) {
		 sessionFactory.getCurrentSession().saveOrUpdate(favorite);
	 }
	 
	 public void deleteFavorite(Favorite favorite) {
		 sessionFactory.getCurrentSession().delete(favorite);
	 }
	 
	 //QUERY LIST FUNCTIONS
	 
	 @SuppressWarnings("unchecked")
	 public List<Favorite> getFavorites() {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Favorite f")
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Favorite> getFavoritesByUser(int iduser) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Favorite f WHERE iduser=:ID")
	  .setInteger("ID", iduser)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Favorite> getFavoritesByUserById(int iduser, int idfavorite) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Favorite f WHERE iduser=:ID AND idcontact=:IDC")
	  .setInteger("ID", iduser)
	  .setInteger("IDC", idfavorite)
	  .list();
	 }
}
