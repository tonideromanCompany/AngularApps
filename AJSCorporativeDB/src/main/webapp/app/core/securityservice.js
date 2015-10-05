(function () {
    'use strict';

    angular
        .module('app.core')
        .factory('securityservice', securityservice);

    securityservice.$inject = ['$window','$http', '$q', 'logger','$rootScope'];
    /* @ngInject */
    function securityservice($window, $http, $q, logger, $rootScope) {
        var service = {
            getUser: getUser,
            setUser: setUser,
            setToken: setToken,
            getToken: getToken,
            distroy: distroy
        };
        
        var vm = this;
        
        return service;
        
        function setToken(data) {
        	$window.localStorage['authToken'] = JSON.stringify(data);
          }
        
        function getToken() {
        	if(localStorage.getItem("authToken")===null)
        		$rootScope.Shell="login.html";
        	else
          	return JSON.parse($window.localStorage['authToken']);
          }
        
        function setUser(data) {
        	$window.localStorage['currentuser']  = JSON.stringify(data);
          }
        
        function getUser() {
        	if(localStorage.getItem("currentuser")===null)
        		$rootScope.Shell="login.html";
        	else
          	return JSON.parse($window.localStorage['currentuser']);
          }
        
        function distroy() {
        	$window.localStorage.removeItem('currentuser');
        	$window.localStorage.removeItem('authToken');
        }
        
    }
    })();