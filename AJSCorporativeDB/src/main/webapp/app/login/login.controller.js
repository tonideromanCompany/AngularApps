(function () {
    'use strict';

    angular
        .module('app.login')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope','$q', 'securityservice', 'logger', '$http'];
    /* @ngInject */
    function LoginController($rootScope, $q, securityservice, logger, $http) {
        var vm = this;
		vm.user = "";
		vm.password = "";
		vm.language = {
				english: 'EN',
				spanish: 'ES',
				french: 'FR'
		}
		var token = "";
		vm.signup = function() {
			$rootScope.Shell="app/signup/signup.html";
		}
		vm.login = function(){
			$http.get('/api/log?email='+vm.user+'&password='+vm.password).
				then(function(response) {
					console.log("Todo ha ido bien");
					$("#form1").removeClass("error");
					$("#form2").removeClass("error");
					$("#form1").addClass("succes");
					$("#form2").addClass("succes");
					$("#alert").hide("slow");
					securityservice.setToken(response.headers('Token'));
					securityservice.setUser(response.data);
					if(vm.user=="antonio@indra.es")
						$rootScope.Shell="report/layout/home.html";
					else
						$rootScope.Shell="app/layout/shell.html";
				})
				.catch(function(response){
				$("#form1").removeClass("succes");
				$("#form2").removeClass("succes");
				$("#form1").addClass("error");
				$("#form2").addClass("error");
				$("#alert").show('fast');
			});
		}  
    }
})();
