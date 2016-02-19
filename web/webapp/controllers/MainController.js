angular.module('myApp').controller('MainController', function ($scope, SpecialistService) {

    $scope.state = {
        registration: false,
        login: true,
        loggedIn: false,
        newPass : false
    };
    
    $scope.results = [];
    
    $scope.switchToState = function(state){
        switch(state){
            case "REGISTRATION" : {
                $scope.state.registration = true;
                $scope.state.login = false;
                $scope.state.loggedIn = false;
                $scope.state.newPass = false;
                break;
            }
            case "LOGIN" : {
                $scope.state.registration = false;
                $scope.state.login = true;
                $scope.state.loggedIn = false;
                $scope.state.newPass = false;
                break;
            }
            case "LOGGEDIN" : {
                $scope.state.registration = false;
                $scope.state.login = false;
                $scope.state.loggedIn = true;
                $scope.state.newPass = false;
                break;
            }
            case "NEWPASS" : {
                $scope.state.registration = false;
                $scope.state.login = false;
                $scope.state.loggedIn = false;
                $scope.state.newPass = true;
                break;
            }
        }
    };
    
//    SpecialistService.getAllSpecialistIfOfficeExist().then(function(response){
//       $scope.results = response.data;
//       $scope.$broadcast("offices_arrived");
//    });


    $scope.$on("results_changed", function(){
        $scope.$broadcast("offices_arrived");
    });
    

    
});