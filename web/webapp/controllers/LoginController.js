angular.module('myApp').controller('LoginController', function ($scope,$location,LoginService,LocalSpecialistService, UserService) {
    
    $scope.loginFailed = false;
    $scope.regError = false;
    $scope.errorMessage = null;
    $scope.justRegistered = false;
    $scope.registeredEmail = false;
    
    $scope.credentials = {
        email: null,
        password : null
    };
    
    $scope.passwordAgain = null;
    
    $scope.login= function(){
        LoginService.doLogin($scope.credentials).then(function(response){
            $scope.loginFailed = false;
            $scope.sessionStorage.isLoggedIn = true;
            UserService.setCredentials(response.data);
            $scope.switchToState("LOGGEDIN");
            LocalSpecialistService.setLoaded(false);
            $location.path("/userpage");
            $scope.atSearchPage = false;
        }, function(response){
            $scope.loginFailed = true;
            $scope.errorMessage = response.data;
            
            $scope.atSearchPage = true;
        });
    };
    

    $scope.startReagistrationProcess = function(){
        resetCredentials();
        $scope.switchToState("REGISTRATION");
    };
    
    $scope.backToLogin = function(){
        resetCredentials();
        $scope.switchToState("LOGIN");
    };
    
    $scope.registration = function(){
        if (emailsMatching()){
            LoginService.doRegistration($scope.credentials).then(function(response){
                $scope.registeredEmail = response.data;
                $scope.justRegistered = true;
            }, function(response){
               $scope.regError = true;
               $scope.errorMessage = response.data;
                //e.g. email already in use 
            });
               
            
        }else{
            $scope.regError = true;
            $scope.errorMessage = "A két jelszó nem egyezik!";
        }
    };
    
    var emailsMatching = function(){
        return $scope.credentials.password === $scope.passwordAgain;
    };
    
    var resetCredentials = function(){
        $scope.credentials.email = null;
        $scope.credentials.password = null;
        $scope.passwordAgain = null;
    };
    
    $scope.newPassword = function(){
         $scope.switchToState("NEWPASS");
    };
    
    $scope.sendNewPass = function(){
        LoginService.newPassword($scope.credentials.email).then(function(response){
            
            $scope.newPassSent = true;
            $scope.newPassMessage = response.data;
        }, function(response){
            $scope.newPassError = true;
            $scope.newPassMessage = response.data;
        });
    };
    
    
   
});


