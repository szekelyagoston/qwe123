angular.module('myApp')
    .directive('openingdaysDirective', function () {
        return {
            restrict: 'E',
            templateUrl: 'webapp/partials/directives/openingdays.html',
            scope : {
                openingdays : "=",
                disabled:'=?ngDisabled'
            },
            controller : 'OpeningDaysController',
            
        };
    });
