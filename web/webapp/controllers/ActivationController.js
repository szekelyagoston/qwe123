angular.module('myApp').controller('ActivationController', function ($scope,$routeParams,$location,LoginService, UserService) {
    
    $scope.activated = false;
    $scope.error = false;
    $scope.code = $routeParams.link;
    console.log($scope.code);
    LoginService.activateUser($scope.code).then(function(response){
        console.log("HERE")
        $scope.activated = true;
    }, function(response){
         console.log(response.data)
        if (response.data === "User not found"){
            $scope.message = "Érvénytelen kód";
            $scope.error = true;
        }
    });
    
    $scope.toLogin = function(){
        $location.path("/")
    }
});