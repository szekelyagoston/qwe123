angular.module('myApp').directive('loginDirective', function () {
    return {
        restrict: 'E',
        templateUrl: 'webapp/partials/login.html',
        controller: 'LoginController'
    };
});

angular.module('myApp')
        .directive('mapDirective', function () {
            return {
                restrict: 'E',
                templateUrl: 'webapp/partials/map.html',
                controller: 'MapController'
            };
        });

angular.module('myApp')
        .directive('resultDirective', function () {
            return {
                restrict: 'E',
                templateUrl: 'webapp/partials/result.html',
                controller: 'ResultController'
            };
        });
angular.module('myApp')
        .directive('searchDirective', function () {
            return {
                restrict: 'E',
                templateUrl: 'webapp/partials/search.html',
                controller: 'SearchController'
            };
        });

angular.module('myApp')
        .directive('sectionDirective', function () {
            return {
                restrict: 'E',
                templateUrl: 'webapp/partials/section.html'
            };
        });