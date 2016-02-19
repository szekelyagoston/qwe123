angular.module('myApp').controller('NavigationController', function ($scope,$location, LocalSpecialistService, LoginService, $sessionStorage) {
    $scope.atSearchPage = true;
    
    $scope.logout = function(){
        LoginService.doLogout().then(function(response){
            $scope.sessionStorage.isLoggedIn = false;
            LocalSpecialistService.setLoaded(false);
            $location.path("/");
        }, function(response){
            
        });
    };
    
    $scope.sessionStorage = $sessionStorage.$default({
        isLoggedIn : false
    });
});