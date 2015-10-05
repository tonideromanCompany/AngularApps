(function(){
	var login = angular.module('login', []);
	
	login.controller("LoginController", function(){
		var vm = this;
		
		vm.language = {
				english: 'EN',
				spanish: 'ES',
				french: 'FR'
		}
		
		vm.log = function(){
			window.location.assign("/");
		}
	});
})();