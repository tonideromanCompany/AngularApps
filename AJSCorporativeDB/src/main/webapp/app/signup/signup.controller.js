(function () {
    'use strict';

    angular
        .module('app.signup')
        .controller('SignupController', SignupController);

    SignupController.$inject = ['$rootScope','$q', 'securityservice', 'logger', '$http'];
    /* @ngInject */
    function SignupController($rootScope, $q, securityservice, logger, $http) {
        var vm = this;
        vm.language = {
				english: 'EN',
				spanish: 'ES',
				french: 'FR'
		}
        vm.formUser = {};
        vm.formProfile = {};
        
        vm.signin = function() {
        	$rootScope.Shell="app/login/login.html";
        }
        
        vm.signup = function() {
        	vm.formUser = {};
        	vm.formProfile = {};
        	vm.success=true;
        	if(vm.formName==undefined) {
        		$("#Name").attr("placeholder", "Name - Required field");
        		vm.result1="red";
        		vm.success = false;
        	} else vm.result1="green";
        	if(vm.formSurname==undefined) {
        		$("#Surname").attr("placeholder", "Surname - Required field");
        		vm.result2="red";
        		vm.success = false;
        	} else vm.result2="green";
        	if(vm.formEmail==undefined) {
        		$("#Email").attr("placeholder", "Email - Required field");
        		vm.result3="red";
        		vm.success = false;
        	} else vm.result3="green";
        	if(vm.formPass==undefined) {
        		$("#Pass").attr("placeholder", "Password - Required field");
        		vm.result4="red";
        		vm.success = false;
        	} else vm.result4="green";
        	if(vm.formCity==undefined) {
        		$("#City").attr("placeholder", "City - Required field");
        		vm.result6="red";
        		vm.success = false;
        	} else vm.result6="green";
        	if(vm.formCountry==undefined) {
        		$("#Country").attr("placeholder", "Country - Required field");
        		vm.result7="red";
        		vm.success = false;
        	} else vm.result7="green";
        	if(vm.formBirthday==undefined) {
        		$("#Birthday").attr("placeholder", "Birthday - Required field");
        		vm.result8="red";
        		vm.success = false;
        	} else vm.result8="green";
        	if(vm.formPass==vm.formPass2 && vm.formPass!=undefined){
        		vm.result4="green";
        		vm.result5="green";
        	} else {
        		if(vm.formPass!=undefined) {
        		$("#Pass").attr("placeholder", "Password must be equal");
        		$("#Pass2").attr("placeholder", "Password must be equal");
        		$("#Pass").val("");
        		$("#Pass2").val("");
        		}
        		vm.result4="red";
        		vm.result5="red";
        		vm.success = false;
        	}
        	if(vm.success) {
	        	vm.formUser = {
	        			name: vm.formName,
	        			surname: vm.formSurname,
	        			email: vm.formEmail,
	                	password: vm.formPass
	        	};
	        	vm.formProfile = {
	        	city: vm.formCity,
	        	country: vm.formCountry,
	        	birthday: vm.formBirthday.getTime()
	        	};
	        	$http.post("/api/users", vm.formUser).
	        	then(function(response) {
	        		var id = response.data;
	        		vm.formProfile.iduser = id;
	        		$http.post("/api/profile", vm.formProfile).
	        		then(function(response) {
	        			$http.get('/api/log?email='+vm.formUser.email+'&password='+vm.formUser.password).
	    				then(function(response) {
	    					securityservice.setToken(response.headers('Token'));
	    					securityservice.setUser(response.data);
	    					$rootScope.Shell="app/layout/shell.html";
	    				}, function(response) {
	    					logger.error("Something wrong happens with your account, please reload the page and sign in again");
	    				});
	        		}, function(response) {
	        			console.log("ERROR! "+response.status);
	        		});
	        	}, function(response) {
	        		console.log("Server responds with an error code: "+response.status);
	        		if(response.status == 406){
	        			$("#Email").attr("placeholder", "This email it is registered");
	        			$("#Email").val("");
	        			vm.result3="red";
	        		}
	        	});
        	}
        }
    }
})();
