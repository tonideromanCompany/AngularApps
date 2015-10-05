(function () {
    'use strict';

    angular
        .module('app.profile')
        .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['securityservice', 'logger', 'dataservice', '$q', '$http', '$scope','$rootScope'];
    /* @ngInject */
    function ProfileController(securityservice, logger, dataservice, $q, $http, $scope, $rootScope) {
        var vm = this;
        vm.title = 'Profile';
        vm.profile = {};
        vm.profiles = [];
        vm.currentuser = {};
        vm.contacts = [];
        vm.favorites = [];
        vm.images = [];
        vm.index = 0;
        vm.postImage = "";
        vm.showBioContainer = true;
        vm.showContactsContainer = false;
        vm.showConfigContainer = false;
        vm.showGalleryContainer = false;
        auth();
        vm.settings = 1;
        
        vm.createImage = function() {
        	$http.post("/api/profile/user/"+vm.currentuser.id+"/profileimage").
        	then(function(response){
        		console.log("UPDATE do correctly");
        	}, function(response){
        		console.log("Something wrong happens, check you're console status");
        });
        }
        
        vm.showInfo = function() {
        	vm.showBioContainer = true;
            vm.showContactsContainer = false;
            vm.showConfigContainer = false;
            vm.showGalleryContainer = false;
            $("#bioNav").addClass("active");
            $("#contactsNav").removeClass("active");
            $("#configNav").removeClass("active");
            $("#galleryNav").removeClass("active");
        }
        
        vm.showContacts = function() {
        	vm.showContactsContainer = true;
        	vm.showBioContainer = false;
            vm.showConfigContainer = false;
            vm.showGalleryContainer = false;
            $("#contactsNav").addClass("active");
            $("#configNav").removeClass("active");
            $("#bioNav").removeClass("active");
            $("#galleryNav").removeClass("active");
        }
        
        vm.showGallery = function() {
        	vm.showGalleryContainer = true;
        	vm.showConfigContainer = false;
        	vm.showContactsContainer = false;
        	vm.showBioContainer = false;
        	$("#galleryNav").addClass("active");
        	$("#configNav").removeClass("active");
            $("#contactsNav").removeClass("active");
            $("#bioNav").removeClass("active");
        }
        
        vm.showConfig = function() {
        	vm.showConfigContainer = true;
        	vm.showContactsContainer = false;
        	vm.showBioContainer = false;
        	vm.showGalleryContainer = false;
        	$("#configNav").addClass("active");
            $("#contactsNav").removeClass("active");
            $("#bioNav").removeClass("active");
            $("#galleryNav").removeClass("active");
        }
        
        vm.showFileupload = function(){
        	$("#file-manager").modal("show");
        }
        
        vm.showImage = function(i){
        	vm.index = vm.images.indexOf(i);
        	console.log(vm.index);
        	$("#image-show").modal("show");
        	vm.imagemodal = vm.images[vm.index].url;
        	vm.showImagePrev = function(){
        		vm.index--;
        		if(vm.index >= 0)
            	vm.imagemodal = vm.images[vm.index].url;
        		else {
        			vm.index++;
        			logger.warning("This is the first photo");
        		}
            }
        	vm.showImageNext = function(){
        		vm.index++;
        		if(vm.index < vm.images.length)
        			vm.imagemodal = vm.images[vm.index].url;
        		else {
        			logger.warning("This is the last photo");
        			vm.index--;
        		}
            }
        	
        	 vm.deleteImage = function() {
 	        	var id = vm.images[vm.index].id;
 	        	$http['delete']('/api/profile/user/'+vm.currentuser.id+'/image/'+id).
 	        	then(function(response){
 	        		vm.images.splice(vm.index,1);
 	        		vm.showImagePrev();
 	        		logger.success("Image deleted satisfactory");
 	        	}, function(response){
 	        		logger.error("Can't delete the image, something wrong happens");
 	        	});
 	        }
        
        }
        
        vm.profileimage = function() {
        	   $http.post('/api/profile/user/'+vm.currentuser.id+'/profileimage/'+vm.images[vm.index].id, "Sending image").
               then(function(response){
             		console.log("UPDATE do correctly");
             		$rootScope.Profile="profile/user/"+vm.currentuser.id+"/image/"+vm.images[vm.index].id;
             	}, function(response){
             		console.log("Something wrong happens, check you're console status");
             });
        }
        
        vm.addFavorite = function(c) {
        	var index = vm.contacts.indexOf(c);
        	$http.post('/api/user/'+vm.currentuser.id+'/favorite', vm.contacts[index].id).
        	then(function(response) {
        		console.log("Favorite created");
        		vm.contacts[index].fav = "gold";
        	}, function(response) {
        		logger.error("Something wrong was happen deleting favorite");
        	});
        }
        
        vm.setopt = function(opt) {
        	vm.settings = opt;
        }
        
        $(".modal-image").click(function() {
        	$(".options").toggle();
        });
        
        function auth() {
        	vm.currentuser = securityservice.getUser();
			if(jQuery.isEmptyObject(vm.currentuser)) {
        	}
        	else {
        		activate();
        	}
        }
        
        function activate() {
        	var promises = [getProfile(), getFavorites(), getContacts(),getImages(), getProfiles()];
        	return $q.all(promises).then(function() {
        		MyImage();
        		putImages();
        		checkFav();
        		logger.info('Activated Profile View');
        	 });
        }
        
        function getProfile() {
        	var id = vm.currentuser.id;
            return dataservice.getProfile(id).then(function (data) {
                vm.profile = data;
                return vm.profile;
            });
        }
        
        function getImages() {
        	var id = vm.currentuser.id;
            return dataservice.getImages(id).then(function (data) {
                vm.images = data;
                return vm.images;
            });
        }
        
        function getContacts() {
        	var id = vm.currentuser.id;
            return dataservice.getContacts(id).then(function (data) {
                vm.contacts = data;
                return vm.contacts;
            });
        }
        
        function getFavorites() {
        	var id = vm.currentuser.id;
            return dataservice.getFavorites(id).then(function (data) {
                vm.favorites = data;
                return vm.favorites;
            });
        }
        
        function getProfiles() {
            return dataservice.getProfiles(
            		).then(function (data) {
                vm.profiles = data;
                return vm.profiles;
            });
        }
        
        function putImages(){
        	for(var i=0;i<vm.contacts.length;i++){
        		for(var j=0;j<vm.profiles.length;j++){
        		if(vm.contacts[i].id==vm.profiles[j].iduser)
        			vm.contacts[i].image=vm.profiles[j].image;
        		}
        	}
        }
        
        function MyImage() {
        	if(vm.profile.image!=0)
        		$rootScope.Profile="profile/user/"+vm.currentuser.id+"/image/"+vm.profile.image;
        	else
        		$rootScope.Profile="images/default.png";
        }
        
        function checkFav() {
        	for(var i=0;i<vm.favorites.length;i++){
        		for(var j=0;j<vm.contacts.length;j++){
        			if(vm.contacts[i].id==vm.contacts[j].id)
        				vm.contacts[j].fav = "gold";
        		}
        	}
        }
    }
})();
