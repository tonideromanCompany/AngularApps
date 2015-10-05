(function () {
    'use strict';

    angular
        .module('app.calendar')
        .controller('CalendarController', CalendarController);

    CalendarController.$inject = ['securityservice', '$http', '$q', 'dataservice', 'logger','$scope'];
    /* @ngInject */
    function CalendarController(securityservice, $http, $q, dataservice, logger, scope) {
        var vm = this;
        vm.currentuser = {};
        vm.title = 'Calendar';
        vm.date = new Date();
        vm.d = vm.date.getDate();
        vm.m = vm.date.getMonth();
        vm.y = vm.date.getFullYear();
        vm.lastView;
        vm.events = [];
        vm.event = {};
        
        auth();
        
        function auth() {
        	vm.currentuser = securityservice.getUser();
			if(jQuery.isEmptyObject(vm.currentuser)) {
        	}
        	else {
        		activate();
        	}
        }
        
        function activate() {
        	var promises = [getEvents()];
            return $q.all(promises).then(function() {
            	logger.info('Activated Calendar View');
            	$('#calendar').fullCalendar( 'addEventSource', vm.events);
              $("select#startselect").val("08:00");
              $("select#endselect").val("09:00");
            });
        }
        
        function getEvents() {
        	var id = vm.currentuser.id;
        	console.log(vm.currentuser);
            return dataservice.getEvents(id).then(function (data) {
                vm.events = data;
                //console.log(vm.events);
                return vm.events;
            });
        }
        vm.submit = function() {
        	$("#commentForm").submit();
        	vm.edit();
        }
        
        vm.edit = function(){
        	$("#textfields").hide('fast');
        	$("#formfields").show('slow');
        }
        vm.open = function(){
        $("#textfields").show('fast');
        $("#formfields").hide('fast');
        if(!$("#wrapper").hasClass("toggled"))
        $("#wrapper").addClass("toggled");
        }
        
        vm.close = function() {
          $("#textfields").show('fast');
          $("#formfields").hide('fast');
          if($("#wrapper").hasClass("toggled"))
              $("#wrapper").removeClass("toggled");
        }
        vm.back = function() {
          $("#textfields").show('slow');
        	$("#formfields").hide('fast');
        }
        
        $(document).ready(function() {
            // page is now ready, initialize the calendar...
        	$("#submitmodifyevent").click(function(){
    	        $("#modifyTitlemodal").modal('hide');
    	    });
          
          $("#submitmodifyevent").click(function(){
              $("#addEventmodal").modal('hide');
          });
            $('#calendar').fullCalendar({
            	eventClick: function(event, jsEvent, view) {
            		vm.event={
            				title: event.title,
            				start: event.start,
            				end: event.end,
            				description: event.description
            		};
                    if(vm.event.allDay) vm.allday="fa fa-check";
                    else vm.allday="fa fa-close";
                    scope.$apply();
                    },
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay'
                },
                columnFormat: {
                    month: 'ddd',
                    week: 'ddd D',
                    day: 'dddd D'
                },  
                defaultView: 'month',
                firstDay: 1,            
                //editable: true,
                selectable: true,
                allDaySlot: false,
                scrollTime: "8:00:00",
                
                events: vm.events,
                eventBackgroundColor: 'rgba(255,99,71,0.7)',
                eventBorderColor: 'Tomato',
                
                eventRender: function(event, element) {
                element.bind('dblclick', function() {
                   	vm.open();
                    vm.del = function(){
                  	  $('#calendar').fullCalendar('removeEvents',event.id);
                  	  $http['delete']("/api/events/"+event.id).
                  	  then(function(response){
                      		console.log("DELETE do correctly");
                      	}, function(response){
                      		console.log("Something wrong happens, check you're console status");
                      });
                  	  vm.close();
                    }
                vm.modify = function(){
                  if(vm.modifyTitle) {
                 	event.title = vm.modifyTitle;
                 	}
                 	if(vm.modifyDescription) {
                 	event.description = vm.modifyDescription;
                 	}
                  if(vm.modifyStart) {
                 	  event.start = vm.modifyStart;
                  }
                  if(vm.modifyEnd) {
                    event.end = vm.modifyend;
                  }
                  event.allDay = vm.modifyAllDay;
                  $('#calendar').fullCalendar('updateEvent', event);
                  vm.tmpevent={
                	  id: event.id,
                	  iduser: event.iduser,
                	  title: event.title,
                	  start: event.start,
                	  end: event.end,
                	  description: event.description,
                	  allday: event.allday,
                	  editable: event.editable,
                	  durationeditable: event.durationeditable
                  };
                  $http.post('/api/event', vm.tmpevent).
                  then(function(response){
                		console.log("UPDATE do correctly");
                	}, function(response){
                		console.log("Something wrong happens, check you're console status");
                });
                  vm.close();
                }
                });
                },
                eventDrop: function(event) {
                	vm.Dragevent={
                      	  id: event.id,
                      	  iduser: event.iduser,
                      	  title: event.title,
                      	  start: event.start,
                      	  end: event.end,
                      	  description: event.description,
                      	  allday: event.allday,
                      	  editable: event.editable,
                      	  durationeditable: event.durationeditable
                        };
                	$http.post('/api/event', vm.Dragevent).
                    then(function(response){
                  		console.log("UPDATE do correctly");
                  	}, function(response){
                  		console.log("Something wrong happens, check you're console status");
                  });
                },
                eventResize: function(event) {
                	vm.Resizeevent={
                      	  id: event.id,
                      	  iduser: event.iduser,
                      	  title: event.title,
                      	  start: event.start,
                      	  end: event.end,
                      	  description: event.description,
                      	  allday: event.allday,
                      	  editable: event.editable,
                      	  durationeditable: event.durationeditable
                        };
                	$http.post('/api/event', vm.Resizeevent).
                    then(function(response){
                  		console.log("UPDATE do correctly");
                  	}, function(response){
                  		console.log("Something wrong happens, check you're console status");
                  });
                },
                select: function(start, end) {
                  //$("#addEventmodal").modal('show');
                  vm.newtitle = "New Event";
                  var eventData;
                  if(vm.newtitle) {
                  eventData = {
                      title: vm.newtitle,
                      iduser: vm.currentuser.id,
                      start: start,
                      end: end,
                      description: "",
                      allday: false,
                      editable: true,
                      durationeditable: true
                };
                  vm.events.push(eventData);
                  $http.post('/api/events',eventData).
              	then(function(response){
              		console.log("POST do correctly");
              	}, function(response){
              		console.log("Something wrong happens, check you're console status");
              });
                $('#calendar').fullCalendar('renderEvent', eventData, true); // stick = true
                $('#calendar').fullCalendar('refetchEvents');
                // not helping either
                $('#calendar').fullCalendar('rerenderEvents');
            }
            $('#calendar').fullCalendar('unselect');
        }
                
            });

            logger.info('doc ready');  
        }
       
        );
    }
})();
