angular.module('myApp').controller('VerifyOfficeController', function ($scope, $location,$timeout, AdminService, uiGmapGoogleMapApi, PictureService) {
    $scope.$on("credentials-arrived", function(){
        if ($scope.credentials.secGroup !=="ADMIN") $location.path('/userpage');     
    });  
    
    $scope.offices = [];
    $scope.selectedOffice = {};
    $scope.message = "OOOo";
    
    var init = function(){
        AdminService.getAllNotVerifiedOrModifiedOfficeWithSpecialist().then(function(response){
            $scope.offices = response.data;
            console.log(response.data)
            if ($scope.offices.length > 0){
                $scope.openOffice($scope.offices[0]);
            }
            
        });
    };
    
    $scope.openOffice = function(office){
        deselectAllOffice();
        office.selected = true;
        $scope.selectedOffice = angular.copy(office);
        if (!angular.isDefined($scope.selectedOffice.specialist)){
            $scope.selectedOffice.specialist = [];
            $scope.selectedOffice.specialist.picture = PictureService.getEmptyPictureBase64();
        }else{
            $scope.selectedOffice.specialist.picture === null ? $scope.selectedOffice.specialist.picture = PictureService.getEmptyPictureBase64() : $scope.selectedOffice.specialist.picture = PictureService.addPrefix($scope.selectedOffice.specialist.picture);

        }
        console.log($scope.selectedOffice)
        
        var marker = {
            coords: {
                latitude: $scope.selectedOffice.lat,
                longitude: $scope.selectedOffice.lng
            },
            id: {
                office: $scope.selectedOffice,
            }
        };
        $scope.map.markers = [marker];
        if ($scope.selectedOffice.modifiedOffice.lat !== $scope.selectedOffice.lat || $scope.selectedOffice.modifiedOffice.lng !== $scope.selectedOffice.lng){
                        var origMarker = {
                        coords: {
                            latitude: $scope.selectedOffice.modifiedOffice.lat,
                            longitude: $scope.selectedOffice.modifiedOffice.lng
                        },
                        id: 2,
                        options : {icon:'assets/img/specmarker.png'}
                    };
                    $scope.map.markers.push(origMarker)
                }
        $scope.mapControl.getGMap().setCenter(new google.maps.LatLng($scope.selectedOffice.lat, $scope.selectedOffice.lng));
    };
    
    var deselectAllOffice = function(){
        for (var i = 0; i < $scope.offices.length; ++i){
            $scope.offices[i].selected = false;
        }
    };
    
    $scope.activate = function(office){
        if (office.verified){
            AdminService.verifyOfficeModification(office.id).then(function(response){
                $scope.offices = response.data;
                $scope.openOffice($scope.offices[0]);
            });
        }else{
            AdminService.verifyOffice(office.id).then(function(response){
                $scope.offices = response.data;
                $scope.openOffice($scope.offices[0]);

            });
        }
        
    };
    
    $scope.deny = function(office, message){
        AdminService.denyOffice(office.id, message).then(function(response){
            $scope.offices = response.data;
            $scope.openOffice($scope.offices[0]);
            
        });
    };
    
    init();
    
    //map. ahould make a directive for basic map showing but i am tired so copypaste
    $scope.map = {
        center: {latitude: 0,
            longitude: 0
        },
        zoom: 15,
        markers: []

    };
    $scope.mapControl = {};
    
    uiGmapGoogleMapApi.then(function (maps) {
        $timeout(function () {
            $scope.mapControl.getGMap().setCenter(new google.maps.LatLng($scope.selectedOffice.lat, $scope.selectedOffice.lng));
        }, 500);

    });
    
    $scope.showModified = function (prop) {
        if (!angular.isDefined($scope.selectedOffice))
            return false;
        return $scope.selectedOffice.modified && $scope.selectedOffice.modifiedOffice !== null && $scope.selectedOffice.modifiedOffice[prop] !== $scope.selectedOffice[prop];
    };
    
});