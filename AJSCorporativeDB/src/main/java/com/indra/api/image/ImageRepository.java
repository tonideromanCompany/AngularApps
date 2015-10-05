package com.indra.api.image;

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
public class ImageRepository {
	
	@Autowired
	 protected SessionFactory sessionFactory;
	 
	 public void createImage(Image image) {
	 sessionFactory.getCurrentSession().save(image);
	 }
	 
	 public void updateImage(Image image) {
		 sessionFactory.getCurrentSession().saveOrUpdate(image);
	 }
	 
	 public void deleteImage(Image image) {
		 sessionFactory.getCurrentSession().delete(image);
	 }
	 
	 //QUERY LIST FUNCTIONS
	 
	 @SuppressWarnings("unchecked")
	 public List<Image> getImages() {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Image i")
	  .list();
	 }

	 @SuppressWarnings("unchecked")
	 public List<Image> getImagesByUser(int iduser) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Image i WHERE iduser=:ID")
	  .setInteger("ID", iduser)
	  .list();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public List<Image> getImagesByUserById(int iduser, int idimage) {
	 return sessionFactory.getCurrentSession()
	  .createQuery("FROM Image i WHERE iduser=:ID AND id=:IDIMG")
	  .setInteger("ID", iduser)
	  .setInteger("IDIMG", idimage)
	  .list();
	 }

}
