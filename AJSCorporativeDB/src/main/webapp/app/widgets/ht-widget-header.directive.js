(function() {
    'use strict';

    angular
        .module('app.widgets')
        .directive('htWidgetHeader', htWidgetHeader);

    /* @ngInject */
    function htWidgetHeader() {
        //Usage:
        //<div ht-widget-header title="vm.map.title"></div>
        // Creates:
        // <div ht-widget-header=""
        //      title="Movie"
        //      allow-collapse="true" </div>
        var directive = {
            scope: {
            	'icon':'@',
                'title': '@',
                'subtitle': '@',
                'rightText': '@',
                'allowCollapse': '@',
                'collapseid': '@',
                'notification': '@'
            },
            templateUrl: 'app/widgets/widget-header.html',
            restrict: 'EA'
        };
        return directive;
    }
})();
