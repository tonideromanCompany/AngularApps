(function() {
    'use strict';

    angular
        .module('app.widgets')
        .directive('htWidgetChatHeader', htWidgetChatHeader);

    /* @ngInject */
    function htWidgetChatHeader() {
        //Usage:
        //<div ht-widget-header title="vm.map.title"></div>
        // Creates:
        // <div ht-widget-header=""
        //      title="Movie"
        //      allow-collapse="true" </div>
        var directive = {
            scope: {
                'title': '@',
                'closeid': '@',
                'allowCollapse': '@',
                'collapseid': '@',
                'notification': '@',
                'function': '@'
            },
            templateUrl: 'app/widgets/widget-chat-header.html',
            restrict: 'EA'
        };
        return directive;
    }
})();
