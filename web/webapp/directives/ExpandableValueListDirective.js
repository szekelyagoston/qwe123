angular.module('myApp')
    .directive('expandableValulistDirective', function () {
        return {
            restrict: 'E',
            templateUrl: 'webapp/partials/directives/expandablevaluelist.html',
            scope : {
                allitems : "=",
                useritems : "=",
                origuseritems : "=",
                disabled:'=?ngDisabled',
                title : "@",
                beforeSave : "&"
            },
            controller : 'ExpandableValueListController',
            
        };
    });
