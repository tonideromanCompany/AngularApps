(function () {
    'use strict';

    angular.module('app', [
        'app.core',
        'app.widgets',
        'app.profile',
        'app.admin',
        'app.calendar',
        'app.dashboard',
        'app.layout',
        'demo',
        'app.login',
        'app.index',
        'app.signup',
        'app.home'
    ])
    
    .config(['$httpProvider' , function ($httpProvider) {
    	$httpProvider.interceptors.push(['$q', '$window', '$rootScope', function($q, $window , $rootScope) {
            return {
                    'request': function (config) {
                        config.headers = config.headers || {};
                        if (localStorage.getItem("authToken")===null) {
                        } else {
                        	var token = $window.localStorage['authToken'];
                            config.headers.Authorization = token;
                        }
                        return config;
                    },
                    'responseError': function(response) {
                        if(response.status == 401) {
                        	localStorage.removeItem('currentuser');
                        	localStorage.removeItem('authToken');
                            $rootScope.Shell="app/login/login.html";
                        }
                        return $q.reject(response);
                    }
                };
            }]);
    }]);
    
})();
