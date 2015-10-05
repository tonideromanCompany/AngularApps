(function() {
    'use strict';

    angular
        .module('app.calendar')
        .run(appRun);

    appRun.$inject = ['routerHelper'];
    /* @ngInject */
    function appRun(routerHelper) {
        routerHelper.configureStates(getStates());
    }

    function getStates() {
        return [
            {
                state: 'calendar',
                config: {
                    url: '/calendar',
                    templateUrl: 'app/calendar/calendar.html',
                    controller: 'CalendarController',
                    controllerAs: 'vm',
                    title: 'Calendar',
                    settings: {
                        nav: 3,
                        content: '<i class="fa fa-calendar"></i> Calendar'
                    }
                }
            }
        ];
    }
})();
