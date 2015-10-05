(function () {
    'use strict';

    angular
        .module('app.core')
        .factory('socketservice', socketservice);

    socketservice.$inject = ['$window','$http', '$q', 'logger','$rootScope'];
    /* @ngInject */
    function socketservice($window, $http, $q, logger, $rootScope) {
        var service = {
            setSocket: setSocket
        };
        
        var vm = this;
        
        function setSocket() {
        	$rootScope.socket = new SockJS("ws");
        	$rootScope.stompClient = Stomp.over($rootScope.socket);
        	$rootScope.stompClient.connect({}, function(frame) {
                console.log('Connected: ' + frame);
            });
        	return "connected";
        }
    return service;
    }
})();