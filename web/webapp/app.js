var myApp = angular.module('myApp', ['ngRoute', 'angularFileUpload', 'ngStorage', 'uiGmapgoogle-maps', 'ui.bootstrap']);
myApp.config(function ($routeProvider, uiGmapGoogleMapApiProvider) {
    uiGmapGoogleMapApiProvider.configure({
                key: 'my-api-key',
                v: '3.20', //defaults to latest 3.X anyhow
                libraries: 'weather,geometry,visualization'
            });
    $routeProvider

            // route for the home page
            .when('/', {
                templateUrl: 'webapp/partials/main.html',
                controller: 'MainController'
            })
            .when('/activate', {
                templateUrl: 'webapp/partials/activate.html',
                controller: 'ActivationController'
                
                
            })
            .when('/userpage', {
                templateUrl: 'webapp/partials/users/userPage.html',
                controller: 'UserPageController',
                auth: true
            })
            
            .when('/changepassword', {
                templateUrl: 'webapp/partials/users/changepassword.html',
                controller: 'ChangePasswordController',
                auth: true
            })
            .when('/officeDetail', {
                templateUrl: 'webapp/partials/users/officedetail.html',
                controller: 'OfficeDetailController',
                auth: true
            }).when('/documents', {
                templateUrl: 'webapp/partials/users/documents.html',
                controller: 'DocumentController',
                auth: true
            }).when('/problem_symtphom', {
                templateUrl: 'webapp/partials/admin/problem_symtphom.html',
                controller: 'ProblemSymtphomController',
                auth: true
            })
            .when('/verify_office', {
                templateUrl: 'webapp/partials/admin/verify_office.html',
                controller: 'VerifyOfficeController',
                auth: true
            })
            .when('/verify_specialist', {
                templateUrl: 'webapp/partials/admin/verify_specialist.html',
                controller: 'VerifySpecialistController',
                auth: true
            })
            .when('/value_lists', {
                templateUrl: 'webapp/partials/admin/value_lists.html',
                controller: 'ValueListsController',
                auth: true
            })
            .otherwise({ redirectTo: '/' });
    
            
           
           
            
});
myApp.run(function ($location, $routeParams, $rootScope, $anchorScroll, $route) {
    
    $rootScope.$on('$routeChangeSuccess', function (newRoute, oldRoute) {
        $location.hash($routeParams.scrollTo);
        $anchorScroll();
    });
});

