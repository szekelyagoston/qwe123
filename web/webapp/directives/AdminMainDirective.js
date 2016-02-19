angular.module('myApp').directive('adminDirective', function () {
    return {
        restrict: 'E',
        templateUrl: 'webapp/partials/users/adminDirective.html',
        controller: 'AdminController',
        transclude: true,
        
    };
});