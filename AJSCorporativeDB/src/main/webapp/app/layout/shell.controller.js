(function() {
    'use strict';

    angular
        .module('app.layout')
        .controller('ShellController', ShellController);

    ShellController.$inject = ['securityservice','$rootScope', '$timeout', 'config', 'logger', '$q', 'dataservice'];
    /* @ngInject */
    function ShellController(securityservice, $rootScope, $timeout, config, logger, $q, dataservice) {
        var vm = this;
        vm.busyMessage = 'Please wait ...';
        vm.isBusy = true;
        vm.profile = {};
        vm.currentuser = {};
        $rootScope.showSplash = true;
        vm.navline = {
            title: 'CorporativeDB',
            text: 'ANGULAR application',
            english: 'EN',
    		spanish: 'ES',
    		french: 'FR',
        };
        
        auth();
        
        if(jQuery.isEmptyObject(vm.currentuser)) {
    		$rootScope.showPage=false;
    	}
    	else {
    		$rootScope.showPage=true;
    		logger.success('Welcome back '+vm.currentuser.name+' '+vm.currentuser.surname, null);
        	hideSplash();
    	}
         
        $(document).ready(function () {
    		
    		$("#someinfo").click(function(){
    	        $("#infomodal").modal('show');
    	    });
        });
        
        function auth() {
        	vm.currentuser = securityservice.getUser();
        	activate();
		}
        
        function activate() {
            var promises = [getProfile()];
            return $q.all(promises).then(function() {
            	$rootScope.Profile=vm.profile.image;
            });
        }
        
        function getProfile() {
        	var id = vm.currentuser.id;
            return dataservice.getProfile(id).then(function (data) {
                vm.profile = data;
                return vm.profile;
            });
        }
        
        function hideSplash() {
            //Force a 1 second delay so we can see the splash.
            $timeout(function() {
                $rootScope.showSplash = false;
            }, 1000);
        }
    }
})();
