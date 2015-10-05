(function () {
    'use strict';

    angular
        .module('app.home')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['logger', '$window', '$rootScope', 'securityservice', '$http'];
    /* @ngInject */
    function HomeController(logger, $window, $rootScope, securityservice, $http) {
        var vm = this;
        vm.currentuser = {};
        vm.filename = "";
        vm.usermail = "";
        vm.fail="";
        activate();
        
        vm.generateUsers = function(type, opt) {
        	$("#AllUsers").modal('show');
        	$("#modalcontent").show();
        	$("#modalcontent2").hide();
        	$("#modalcontent3").hide();
        	switch(type){
        	case 1: vm.type = {title: "PDF", header:"#d9534f", classtype: "btn-danger", button: "fa fa-file-pdf-o fa-lg",}; break;
        	case 2: vm.type = {title: "Excel", header:"#5cb85c", classtype: "btn-success", button: "fa fa-file-excel-o fa-lg"}; break;
        	case 3: vm.type = {title: "Word", header:"#428bca", classtype: "btn-primary", button: "fa fa-file-word-o fa-lg"}; break;
        	}
        	switch(opt){
        		case 1: $("#input2").hide(); break;
        		case 2: $("#input2").show(); break;
        	}
        	vm.close = function() {
        		$("#AllUsers").modal('hide');
        	}
        	vm.report = function() {
        		if(type==1 && opt==1) {
        			//PDF LIST!
        			if(vm.filename=="") {
        				vm.fail="border: 1px solid red !important;";
        				$("#FileName").attr("placeholder", "This field can't be null");
        			}
        			else {
	        			$http.post("/api/report/users/pdf", vm.filename).
	        			then(function(response) {
	        				clean();
		        			vm.finnish="background-color:#d9534f;padding:20px";
		        			$("#modalcontent2").show();
		        			$("#modalcontent3").hide();
		        			$("#modalcontent").hide();
	        			}, function(reponse) {
	        				clean();
		        			vm.finnish="background-color:#d9534f;padding:20px";
		        			$("#modalcontent3").show();
		        			$("#modalcontent2").hide();
		        			$("#modalcontent").hide();
	        			});
	        			
        			}
        		}
        		else if(type==1 && opt==2) {
        			//PDF USER
        			if(vm.filename=="") {
        				vm.fail="border: 1px solid red !important;";
        				$("#FileName").attr("placeholder", "This field can't be null");
        			}
        			if(vm.usermail=="") {
        				vm.fail="border: 1px solid red !important;";
        				$("#UserMail").attr("placeholder", "This field can't be null");
        			}
        			if(vm.filename!="" && vm.usermail!="") {
        				$http.post("/api/report/user/"+vm.usermail+"/pdf", vm.filename).
	        			then(function(response) {
	        				clean();
		        			vm.finnish="background-color:#d9534f;padding:20px";
		        			$("#modalcontent2").show();
		        			$("#modalcontent").hide();
	        			}, function(reponse) {
	        				clean();
		        			vm.finnish="background-color:#d9534f;padding:20px";
		        			$("#modalcontent3").show();
		        			$("#modalcontent").hide();
	        			});
        			}
        		}
        	}
        	function clean() {
        		vm.filename = "";
                vm.usermail = "";
                $("#FileName").attr("placeholder", "");
                $("#UserMail").attr("placeholder", "");
                vm.fail = "";
        	}
        }
        
        function activate() {
        	vm.currentuser = securityservice.getUser();
        	logger.success("Admin mode view");
        }
    }
})();
