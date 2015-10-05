(function () {
    'use strict';

    angular
        .module('app.dashboard')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['securityservice', '$rootScope', '$q', 'dataservice', 'logger', '$http'];
    /* @ngInject */
    function DashboardController(securityservice, $rootScope, $q, dataservice, logger, $http) {
        var vm = this;
        
        /**
         * VARIABLES
         */
        vm.currentuser = {};
        vm.favorites = [];
        vm.profiles = [];
        vm.events = [];
        vm.openchats = [];
        vm.messages = [];
        vm.showmessages = [];
        vm.chat={};
        vm.messagechats=[];
        vm.messagechatsshow=[];
        vm.newmessage = {};
        vm.message = {};
        vm.connections = [];
        vm.subscriptions = [];
        vm.mssBoxfirst=0;
        vm.title = 'Dashboard';
        vm.d = new Date();
        vm.currentevents = [];
        vm.currentmonth = new Date().getMonth();
        vm.currentmonth = MyMonth();
        vm.currentday = new Date().getDate();
        vm.currentweekday = new Date().getDay();
        vm.currentweekday = MyWeek();
        vm.currentDate = vm.d.getFullYear()+"-"+(vm.d.getMonth()+1)+"-"+vm.d.getDate();
        auth();
        
        /**
         * FAVORITES OPTIONS
         */
        vm.showFavoritesBox = function() {
			$("#FavoritesBox").toggle('slow');
		}
        
        vm.hover = function(p) {
        	return p.showOptions = ! p.showOptions;
        }
        
        vm.deleteFavorite = function(p){
        	var index = vm.favorites.indexOf(p);
        	if (index > -1){
        		$http['delete']('/api/user/'+vm.currentuser.id+'/favorites/'+vm.favorites[index].id).
        		then(function(response) {
        			vm.favorites.splice(index,1);
        		}, function(response) {
        			logger.error("Something wrong happens deleting selected favorite");
        		});
        	}
        }
       
        /**
         * CHAT SETTINGS & FUNCTIONS
         */
        
        function CheckSubscription(index) {
        	var sub = false;
        	for(var i=0; i<vm.subscriptions.length; i++) {
        		if(index==vm.subscriptions[i])
        			sub = true
        	}
        	if(!sub)
        		Subscribe(index);
        }
        function Subscribe(index) {
            $rootScope.stompClient.subscribe('/topic/'+vm.currentuser.id+'/chats/'+vm.favorites[index].id, function(data){
            	var d = JSON.parse(data.body);
                	vm.mss={};
        			vm.mss.classtype="contact";
        			vm.mss.message=d.body;
        			vm.messagechatsshow.push(vm.mss);
        			vm.messagechats.push(d);
            });
            vm.subscriptions.push(index);
        }    
        
        vm.OpenChatFavorite = function(p){
        	var index = vm.favorites.indexOf(p);
        	vm.messagechatsshow = [];
        	vm.chat.user=vm.favorites[index].name+" "+vm.favorites[index].surname;
        	vm.chat.minimizeduser=vm.favorites[index].name.substring(0,1)+vm.favorites[index].surname.substring(0,1);
        	vm.chatOp = {state: 0, iduserfrom:vm.currentuser.id, iduserto: vm.favorites[index].id};
            CheckSubscription(index);
            CheckStatus(index);
        	vm.openchats.push(vm.chat);
        	for(var i=0; i<vm.messagechats.length;i++){
        		if(vm.messagechats[i].iduserfrom==vm.currentuser.id && vm.messagechats[i].iduserto==vm.favorites[index].id){
        			vm.mss={};
        			vm.mss.classtype="user";
        			vm.mss.message=vm.messagechats[i].body;
        			vm.messagechatsshow.push(vm.mss);
        		}
        		else if(vm.messagechats[i].iduserfrom==vm.favorites[index].id && vm.messagechats[i].iduserto==vm.currentuser.id){
        			vm.mss={};
        			vm.mss.classtype="contact";
        			vm.mss.message=vm.messagechats[i].body;
        			vm.messagechatsshow.push(vm.mss);
        		}
        	}
        	$("#ChatMinimized").hide('fast');
        	$( "#ChatMinimized" ).draggable();
        	$("#OpenChat").show('slow');
        	
            vm.addMessage = function(){
            	vm.m = {};
            	vm.m.body = vm.newmessage.body;
            	vm.m.iduserfrom=vm.currentuser.id;
            	vm.m.iduserto=vm.favorites[index].id;
            	vm.m.idchat=1; //PROBE!
            	vm.messagechats.push(vm.m);
            	$http.post("/api/chat/1/message", vm.m).
            	then(function(data) {}, function(data){
            		logger.error("Your message can't be sended");
            	});
            	vm.mss={};
            	vm.mss.classtype="user";
            	vm.mss.message=vm.m.body;
            	vm.messagechatsshow.push(vm.mss);
        		//var objDiv = document.getElementById("scrollwindow").scrollHeight;
                $("#scrollwindow").scrollTop(9999);
                vm.newmessage.body = "";
            }
        	
        }
        
        function CheckStatus(index) {
        	if(vm.connections.length==0)
        		vm.state = "red";
        	for(var i=0; i<vm.connections.length; i++) {
        			if(vm.favorites[index].id==vm.connections[i].id) {
        				vm.state = "lime";
        			}
        			else {
        				vm.state = "red";
        			}
        	}
        }
        
        vm.close = function() {
   		 $("#OpenChat").hide('slow');
   		$("#ChatMinimized").hide('fast');
   		 vm.openchats = [];
   }
        
        vm.minimize = function(){
        	$("#OpenChat").hide('slow');
        	$('#ChatMinimized').show('slow');
        }
        
      vm.maximize = function(){
    	  $("#ChatMinimized").hide('fast');
      	$('#OpenChat').show('slow');
      }
        
        
        /**
         * WIDGETS SETTINGS & FUNCTIONS
         */
        $(function() {
    		$( "#sortable" ).sortable({
    			revert: true
    		});
    		$( "#draggable" ).draggable({
    			connectToSortable: "#sortable",
    			helper: "clone",
    			revert: "invalid"
    		});
    		$( "ul, li, div" ).disableSelection();
    	});

        $("#add-to-list").click(function() {
        	$("#sortable").append(' <li class="bgray ui-state-default widgetBox">'+
        			'<div class="pull-right"><span id=close style="display:none" class="fa fa-close deleteicon"></span></div>'+
                    '<div class="pull-left"><i class="fa fa-plus"></i></div>'+
                    '<div class="datas-text pull-left">'+
                        '<span class="bold">New Widget</span>'+
                    '</div>'+
                    '<div class="clearfix"></div>'+
                '</li>');
        	});
        vm.txt = 0;
        $(function(){
        	$(' .widgetBox').hover(function(){
        		vm.txt = $(this).find("#close").text();
        		$(this).find("#close").removeClass("notification");
        		$(this).find("#close").addClass("fa fa-close deleteicon");
        		$(this).find("#close").empty();
        	}, function(){
        		$(this).find("#close").removeClass("fa fa-close deleteicon");
        		$(this).find("#close").addClass("notification");
        		$(this).find("#close").text(vm.txt);
        	}
        	);
        })
        
         vm.deleteicon = function(i) {
    	  $("#widget"+i).remove();
      } 
        
        $("#sidebar-toggle").click(function(e) {
            e.preventDefault();
            $("#wrapper").toggleClass("toggled");
        });
        
        /**
         * DATE FUNCTIONS
         */
		function MyWeek(){
			switch(vm.currentweekday){
			case 1: return "Monday"; break; case 2: return "Tuesday"; break; case 3: return "Wednesday"; break;
			case 4: return "Thuesday"; break; case 5: return "Friday"; break; case 6: return "Saturday"; break;
			case 0: return "Sunday"; break; default: return "Error"; break;
			}
		}
		function MyMonth(){
			switch(vm.currentmonth){
			case 0: return "January"; break; case 1: return "February"; break; case 2: return "March"; break;
			case 3: return "April"; break; case 4: return "May"; break; case 5: return "June"; break;
			case 6: return "July"; break; case 7: return "August"; break; case 8: return "September"; break;
			case 9: return "October"; break; case 10: return "November"; break; case 11: return "December"; break;
			default: return "Error"; break;
			}
		}
		
		/**
		 * CURRENT EVENTS
		 */
		
		vm.showEventsBox = function() {
			$("#EventsBox").toggle('slow');
		}
		
		function CurrentEvents(){
			var j = 0;
			var tmpevents = new Array();
			for(var i=0;i<vm.events.length;i++){
					if(vm.events[i].start.substring(0,10)==vm.d.toJSON().slice(0,10)){
						var currentevent = {};
						currentevent.title = vm.events[i].title;
						currentevent.description = vm.events[i].description;
						currentevent.start = vm.events[i].start.substring(11,16);
						currentevent.end = vm.events[i].end.substring(11,16);
						vm.currentevents[j]=currentevent;
						currentevent = {};
						j++;
					}
				}
			vm.dataorder();
			}
		
		function UpToDown(elem1, elem2){
			return elem2-elem1;
		}
		
		function DownToUp(elem1, elem2){
			return elem1-elem2;
		}
		
		vm.dataorder = function(){
			var tmparray = new Array();
			var tmpcurrentevents = new Array();
			for(var i=0;i<vm.currentevents.length;i++){
				tmparray[i] = vm.currentevents[i].start.substring(0,2);
			}
			tmparray.sort(DownToUp);
			for(var i=0;i<tmparray.length;i++){
				for(var j=0;j<vm.currentevents.length;j++){
					if(tmparray[i]==vm.currentevents[j].start.substring(0,2))
						tmpcurrentevents[i]=vm.currentevents[j];
				}
			}
			vm.currentevents=tmpcurrentevents;
		}
		
		/**
		 * MESSAGE FUNCTIONS
		 */
		
		vm.showMessagesBox = function() {
			$("#MessagesBox").toggle('slow');
		}
		
		vm.writeamessage = function(p) {
			console.log("LLegamos!");
			if(!document.getElementById('Show-mss-switch').checked){
			$("#write-message-widget").show("fast");
			}
			var index = vm.favorites.indexOf(p);
			vm.messagetouser=vm.favorites[index].name+" "+vm.favorites[index].surname;
			vm.tmpmss = {
					iduserfrom: vm.currentuser.id,
					iduserto: vm.favorites[index].id
			};
		}
		
		vm.Msgform = function() {
			vm.tmpmss.body = vm.messagetxtarea;
			$http.post('/api/messages', vm.tmpmss).
	        	then(function(response){
	           	}, function(response){
	           });
			vm.messagetxtarea = "";
			}
		
		vm.submitmssform = function(){
		        if(document.getElementById('myonoffswitch').checked) {   
		        } else {  
		        	$("#write-message-widget").hide("slow");
		        }  
		}
		
		function ExtractUserName() {
			 for(var j=0;j<vm.contacts.length;j++){
	                for(var i=0;i<vm.messages.length;i++){
	                	if(vm.messages[i].iduserfrom==vm.contacts[j].id)
	                		vm.messages[i].userfrom=vm.contacts[j].name+" "+vm.contacts[j].surname;
	                }
             }
		}
		function ShowMessages(){
			for(var i=0;i<3;i++){
				if(vm.messages[i]!=undefined)
					vm.showmessages.push(vm.messages[i]);
			}
		}
		
		vm.ShowMessages = function(){
			if(vm.showmessages.length==vm.messages.length){
				logger.warning("There's no more messages");
			}
			if(vm.showmessages==null){
				for(var i=0;i<3;i++){
					vm.showmessages.push(vm.messages[i]);
				}
			}
			else{
				var l = vm.showmessages.length;
				for(var i=0;i<3;i++){
					if(vm.messages[l+i]!=null){
						vm.showmessages.push(vm.messages[l+i]);
					}
				}
			}
		}
		
		/**
		 * REQUESTS FUNCTIONS 
		 */
		vm.showRequestsBox = function() {
			$("#RequestsBox").toggle('slow');
		}
		
		$(".js-example-basic-multiple").select2({ 
			 ajax: {
			    url: "/api/users/search",
			    dataType: 'json',
			    headers: {'Authorization': localStorage.getItem('authToken')},
			    delay: 250,
			    data: function (term) {
			      return term;
			    },
			    processResults: function (data, page) {
			      return {
			        results: data
			      };
			    },
			    cache: true
			  },
			  placeholder: "Search a user",
			  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
			  minimumInputLength: 3,
			  templateResult: contactsFormatResult
			  //templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
			});
		
		function log (evt) {
			  if (!evt) {
			    var args = "{}";
			  } else {
			    var args = JSON.stringify(evt.params, function (key, value) {
			      if (value && value.nodeName) return "[DOM node]";
			      if (value instanceof $.Event) return "[$.Event]";
			    });
			    vm.selectedEvent = args;
			    console.log(args);
			    $("#selected-contact").modal('show');
			  }
		}
		
		function contactsFormatResult(state) {
			var $state = $( "<table class='table table-condensed'><tr>"+
			"<td><img src='images/user2.png' class='image'/><strong> "+state.name+" "+state.surname+"</strong></td>"+
			"</tr></table>");
			return $state;
		}
		
		/**
		 * BBDD ACCES FUNCTIONS
		 */
		
		function auth() {
				vm.currentuser = securityservice.getUser();
					if(jQuery.isEmptyObject(vm.currentuser)) {
	            	}
	            	else {
	            		activate();
	            	}
		}
        function activate() {
            var promises = [getFavorites(),getContacts(),getEvents(),getMessages(),getProfiles()];
            return $q.all(promises).then(function() {
                    putImages();
            		CurrentEvents();
                    ExtractUserName();
                    ShowMessages();
                    Connect();
                    NewConnections();
                    logger.info('Activated Dashboard View');
            	});
        }
        
        function getFavorites() {
        	var id = vm.currentuser.id;
            return dataservice.getFavorites(id).then(function (data) {
                vm.favorites = data;
                return vm.favorites;
            });
        }
        
        function getContacts() {
        	var id = vm.currentuser.id;
            return dataservice.getContacts(id).then(function (data) {
                vm.contacts = data;
                return vm.contacts;
            });
        }
        
        function getProfiles() {
            return dataservice.getProfiles().then(function (data) {
                vm.profiles = data;
                return vm.profiles;
            });
        }
        
        function getMessages() {
        	var id = vm.currentuser.id;
            return dataservice.getMessages(id).then(function (data) {
                vm.messagesBBDD = data;
                vm.messages = data.reverse();
                return vm.messages;
            });
        }
        
        function getEvents() {
        	var id = vm.currentuser.id;
            return dataservice.getEvents(id).then(function (data) {
                vm.events = data;
                return vm.events;
            });
        }
        
        /**
         * ANOTHER FUNCTIONS
         */
        
        function Connect() {
        	$http.post("/api/chat/user/"+vm.currentuser.id+"/connect").
        		then(function(response) {
        			logger.info("You're connected");
        			vm.connections = response.data;
        		}, function(response) {
        			logger.error("ERROR with your connection");
        		});
        }
        
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
        
        function NewConnections() {
        	waitForSocketConnection($rootScope.socket, function() {
            $rootScope.stompClient.subscribe('/topic/'+vm.currentuser.id+'/chats', function(data){
            	var d = JSON.parse(data.body);
                	vm.connections.push(d);
            });
        	});
        } 
        
        function putImages(){
        	for(var i=0;i<vm.favorites.length;i++){
        		for(var j=0;j<vm.profiles.length;j++){
        		if(vm.favorites[i].id==vm.profiles[j].iduser)
        			vm.favorites[i].image=vm.profiles[j].image;
        		}
        	}
        }
        
    }
})();
