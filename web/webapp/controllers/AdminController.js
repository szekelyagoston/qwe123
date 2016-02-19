angular.module('myApp').controller('AdminController', function ($scope,$location,LoginService,LocalSpecialistService, UserService, SpecialistService, PictureService) {


        $scope.sessionStorage.isLoggedIn ? null : $location.path("/")


    var refresh = function () {
        UserService.getCredentials().then(function (data) {
            $scope.credentials = data;
            console.log(data)
            LocalSpecialistService.setCredentials($scope.credentials);
            $scope.$broadcast("credentials-arrived");
        });

        SpecialistService.getSpecialist().then(function (response) {
            console.log(angular.copy(response))
            $scope.specialist = response.data;
            setPicture();
            LocalSpecialistService.setSpecialist($scope.specialist);
        });
    };
    
    var setPicture = function(){
        $scope.specialist.picture === null ? $scope.specialist.picture = PictureService.getEmptyPictureBase64() : $scope.specialist.picture = PictureService.addPrefix($scope.specialist.picture);
    };


    $scope.refreshSpecialist = function () {
        refresh();
    };
    
    $scope.backToMainPage = function(){
        $location.path("/");
    };


    if (!LocalSpecialistService.getLoaded()) {
        refresh();
        LocalSpecialistService.setLoaded(true);
    } else {
        $scope.specialist = LocalSpecialistService.getSpecialist();
        $scope.credentials = LocalSpecialistService.getCredentials();
        console.log($scope.credentials);
        $scope.$broadcast("credentials-arrived");

    }




});