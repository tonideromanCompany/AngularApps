(function () {
    'use strict';

    angular
        .module('app.core')
        .factory('dataservice', dataservice);

    dataservice.$inject = ['$http', '$q', 'logger'];
    /* @ngInject */
    function dataservice($http, $q, logger) {
        var service = {
            getContacts: getContacts,
            getBudgesE: getBudgesE,
            getEvents: getEvents,
            getMessages: getMessages,
            getLogout: getLogout,
            getProfile: getProfile,
            getImages: getImages,
            getProfileImage: getProfileImage,
            getProfiles: getProfiles,
            getFavorites: getFavorites
        };
        
        var vm = this;
        vm.usertoken = "";
        vm.currentuser = {};
        
        return service;
        
        function getLogout() {
      	  return $http.get('/api/logout')
            .then(success)
            .catch(fail);
      	  
      	  function success(response) {
      		window.location.assign("/api/login.html");
            }

            function fail(error) {
          	  window.location.assign("/api/login.html");
            }
      }
        
        function getContacts(id) {
            return $http.get('/api/contacts/'+id)
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for people failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        function getFavorites(id) {
            return $http.get('/api/user/'+id+'/favorite')
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for favorites failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        function getProfiles() {
            return $http.get('/api/profile')
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for profiles failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        
        function getProfile(id) {
            return $http.get('/api/profile/user/'+id)
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for profile failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        function getProfileImage(id) {
            return $http.get('/api/profile/user/'+id+'/profileimage')
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for profile image failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        function getImages(id) {
            return $http.get('/api/profile/user/'+id+'/image')
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for images failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        function getMessages(id) {
            return $http.get('/api/messages/'+id)
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for messages failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        function getBudgesE() {
            return $http.get('/api/budgesE')
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for budges failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
        function getEvents(id) {
            return $http.get('/api/users/'+id+'/events')
                .then(success)
                .catch(fail);

            function success(response) {
                return response.data;
            }

            function fail(error) {
                var msg = 'query for events failed. ' + error.data.description;
                logger.error(msg);
                return $q.reject(msg);
            }
        }
        
    }
})();
