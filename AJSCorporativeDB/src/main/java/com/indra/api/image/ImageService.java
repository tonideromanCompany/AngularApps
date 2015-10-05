package com.indra.api.image;

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
public class ImageService {
	
	 @Autowired
	 protected ImageRepository repository;
	 
	 public void createImage(Image image) {
	 repository.createImage(image);
	 }
	 
	 public void updateImage(Image image) {
		 repository.updateImage(image);
	 }
	 
	 public void deleteImage(Image image) {
		 repository.deleteImage(image);
	 }
	 
	 public List<Image> getImages() {
	 return repository.getImages();
	 }
	 
	 public List<Image> getImagesByUser(int iduser) {
		 return repository.getImagesByUser(iduser);
		 }
	 
	 public List<Image> getImagesByUserById(int iduser, int idimage) {
		 return repository.getImagesByUserById(iduser, idimage);
		 }

}
