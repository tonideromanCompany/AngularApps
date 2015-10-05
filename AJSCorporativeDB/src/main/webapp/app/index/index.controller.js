(function () {
    'use strict';

    angular
        .module('app.index')
        .controller('IndexController', IndexController);

    IndexController.$inject = ['logger', '$window', '$rootScope'];
    /* @ngInject */
    function IndexController(logger, $window, $rootScope) {
        var vm = this;
        vm.Shell="";
        
        if(localStorage.getItem('authToken')===null){
        	$rootScope.Shell="app/login/login.html";
        }
        else {
        	$rootScope.Shell="app/layout/shell.html";
        }
    }
})();
