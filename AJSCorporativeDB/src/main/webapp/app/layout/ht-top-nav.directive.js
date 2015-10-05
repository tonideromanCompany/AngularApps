(function() {
    'use strict';

    angular
        .module('app.layout')
        .directive('htTopNav', htTopNav);

    /* @ngInject */
    function htTopNav () {
        var directive = {
            bindToController: true,
            controller: TopNavController,
            controllerAs: 'vm',
            restrict: 'EA',
            scope: {
                'navline': '='
            },
            templateUrl: 'app/layout/ht-top-nav.html'
        };

        /* @ngInject */
        function TopNavController(securityservice, $rootScope, $http, $q, dataservice, logger, socketservice) {
            var vm = this;
            vm.currentuser = {};
            vm.notifications = [];
            vm.contacts = [];
            vm.stompClient;
            vm.logout = function(){
            	$http.get('/api/logout')
            	.then(function(response) {
            		securityservice.distroy();
            		$rootScope.Shell="app/login/login.html";
            	}), function(response){
            		securityservice.distroy();
            		$rootScope.Shell="app/login/login.html";
            	};
            }
            auth();
            
            
            
            vm.showNotifications = function() {
            	vm.notalert = "white";
            	$("#Notifications").modal('show');
            }
            
            
            function auth() {
            	vm.currentuser = securityservice.getUser();
            	activate();
    		}
            
            function activate() {
                var promises = [getProfile(), getContacts(), setSocket()];
                return $q.all(promises).then(function() {
                	OpenSocket();
                	MyImage();
                });
            }
            
            function getProfile() {
            	var id = vm.currentuser.id;
                return dataservice.getProfile(id).then(function (data) {
                    vm.profile = data;
                    return vm.profile;
                });
            }
            
            function setSocket() {
            	return socketservice.setSocket();
            }
            
            function getContacts() {
            	var id = vm.currentuser.id;
                return dataservice.getContacts(id).then(function (data) {
                    vm.contacts = data;
                    return vm.contacts;
                });
            }
            
            function OpenSocket() {
            	waitForSocketConnection($rootScope.socket, function(){
            	$rootScope.stompClient.subscribe('/topic/'+vm.currentuser.name+'/notifications', function(data){
                        var d = JSON.parse(data.body);
                        vm.notifications.push(d);
                        vm.notalert = "red !important";
                        CheckContacts();
                        logger.info("Hi! Have received a new "+d.type);
                    });
            	});
            }
            
         // Make the function wait until the connection is made...
            function waitForSocketConnection(socket, callback){
                setTimeout(
                    function () {
                        if (socket.readyState === 1) {
                            console.log("Connection is made")
                            if(callback != null){
                                callback();
                            }
                            return;

                        } else {
                            waitForSocketConnection(socket, callback);
                        }

                    }, 5); // wait 5 milisecond for the connection...
            }
            
            function CheckContacts() {
            	console.log(vm.contacts);
            	for(var i=0;i<vm.notifications.length;i++){
            		for(var j=0;j<vm.contacts.length;j++){
            			if(vm.notifications[i].iduserfrom==vm.contacts[j].id){
            				vm.notifications[i].nameFrom=vm.contacts[j].name;
            				vm.notifications[i].surnameFrom=vm.contacts[j].surname;
            			}
            		}
            	}
            }
            
            function MyImage() {
            	if(vm.profile.image!=0)
            		$rootScope.Profile="profile/user/"+vm.currentuser.id+"/image/"+vm.profile.image;
            	else
            		$rootScope.Profile="images/default.png";
            }
            
            $(document).ready(function () {
        		
        		$("#someinfo").click(function(){
        	        $("#infomodal").modal('show');
        	    });
            });
            
            
        }

        return directive;
    }
})();
