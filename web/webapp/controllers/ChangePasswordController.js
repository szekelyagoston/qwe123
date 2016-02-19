angular.module('myApp').controller('ChangePasswordController', function ($scope,$location,LoginService) {
    
    $scope.credentials = {
        oldpassword: null,
        newpassword : null,
        newpasswordagain : null
    };
    
    $scope.doChange = function(){
        
        if (emailsMatching()){
            LoginService.changePassword($scope.credentials).then(function(response){
                $scope.success = true;
                $scope.error = false;
                $scope.message="Sikeres jelszóváltoztatás!";
            }, function(response){
               $scope.error = true;
               $scope.success = false;
               $scope.errorMessage = response.data;

            });
               
            
        }else{
            $scope.error = true;
            $scope.errorMessage = "A két jelszó nem egyezik!";
        }
    }
    
    var emailsMatching = function(){
        return $scope.credentials.newpassword === $scope.credentials.newpasswordagain;
    };
    
});