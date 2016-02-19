angular.module('myApp').controller('OfficeDetailController', function ($scope, $location, $timeout, SpecialistService, uiGmapGoogleMapApi, AdminService, $q) {
    var id = $location.search().id;

    $scope.mapControl = {};
    var geocoder = new google.maps.Geocoder;
    
    $scope.origOffice = null;
    $scope.modifiedEntity = false;
    
    
    
    var getOffice = function(){
        var deferred = $q.defer();
        SpecialistService.getOfficeById(id).then(function (response) {
            console.log(response.data)
            if (response.data.modified){
                $scope.office = response.data.modifiedOffice;
                $scope.origOffice = response.data;
                $scope.modifiedEntity = true;
            }else{
                $scope.office = response.data;
                $scope.origOffice = null;
            }
            console.log($scope.office)
            $scope.office.userGivenAddress !== null ? $scope.title = $scope.office.userGivenAddress : $scope.title = $scope.office.address;

            deferred.resolve();
        });
        return deferred.promise;
    };

    $scope.refresh = function () {
        var promises = [];
        promises.push(getOffice());
        
        $q.all(promises).then(function(){

        });
       
    };
    
    
    

    
    $scope.lat = 47.5053699;
    $scope.lng = 19.0487995;
    $scope.map = {
        center: {latitude: $scope.lat,
            longitude: $scope.lng
        },
        zoom: 15,
        markers: [],
        events: {
            click: function (map, eventName, originalEventArgs) {
                var e = originalEventArgs[0];
                $scope.office.lat = e.latLng.lat();
                $scope.office.lng = e.latLng.lng();
                console.log($scope.office.lat)
                var marker = {
                    coords: {
                        latitude: $scope.office.lat,
                        longitude: $scope.office.lng
                    },
                    id: 1
                };
                
                $scope.map.markers = [marker];
                if ($scope.origOffice !== null && ($scope.origOffice.lat !== $scope.office.lat || $scope.origOffice.lng !== $scope.office.lng)){
                        var origMarker = {
                        coords: {
                            latitude: $scope.origOffice.lat,
                            longitude: $scope.origOffice.lng
                        },
                        id: 2,
                        options : {icon:'assets/img/specmarker.png'}
                    };
                    $scope.map.markers.push(origMarker)
                }
                $scope.$apply();
                
                geocodeLatLng();
            }
        }
    };


    uiGmapGoogleMapApi.then(function (maps) {

        var success = function (geoposition) {
            $scope.mapControl.getGMap().setCenter(new google.maps.LatLng(geoposition.coords.latitude, geoposition.coords.longitude));
        };
        if (!(angular.isDefined(id))) {
            navigator.geolocation.getCurrentPosition(success);
        } else {
            //TODO: waiting for mapControl arrive
            $timeout(function () {
                $scope.mapControl.getGMap().setCenter(new google.maps.LatLng($scope.office.lat, $scope.office.lng));
                var marker = {
                    coords: {
                        latitude: $scope.office.lat,
                        longitude: $scope.office.lng
                    },
                    id: 1
                };
                $scope.map.markers = [marker];
                if ($scope.origOffice !== null && ($scope.origOffice.lat !== $scope.office.lat || $scope.origOffice.lng !== $scope.office.lng)){
                        var origMarker = {
                        coords: {
                            latitude: $scope.origOffice.lat,
                            longitude: $scope.origOffice.lng
                        },
                        id: 2,
                        options : {icon:'assets/img/specmarker.png'}
                    };
                    $scope.map.markers.push(origMarker)
                }
                
                $scope.$apply();
            }, 1000);

        }


    });

    function geocodeLatLng() {

        var latlng = {lat: $scope.office.lat, lng: $scope.office.lng};
        geocoder.geocode({'location': latlng}, function (results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
                if (results[0]) {
                    
                    $scope.office.address = results[0].formatted_address;
                    $scope.$apply();
                } else {
                    console.log("NORESULT")
                }
            } else {
                console.log("STATUS")
            }
        });
    }
    
    $scope.save = function(){
        removeInactiveOpeningDays();
        
        if (!angular.isDefined($scope.office.id)){
            //save new
                SpecialistService.saveNewOffice($scope.office).then(function(response){
                $location.path("officeDetail").search("id", response.data.id);
            });
        }else{
            //modify
            var id = ($scope.origOffice !== null ? $scope.origOffice.id : $scope.office.id);
            SpecialistService.modifyOffice($scope.office, id).then(function(response){
                $scope.office = response.data;


            });
        }

    };
    
    var removeInactiveOpeningDays = function(){
        var newArray = [];
        for (var i = 0; i < $scope.office.openingHours.length; ++i){
            if ($scope.office.openingHours[i].active && $scope.office.openingHours[i].fromClock !== null && $scope.office.openingHours[i].toClock !== null){
                newArray.push($scope.office.openingHours[i]);
            }
        }
        $scope.office.openingHours = angular.copy(newArray);
    };
    
    


    
    
    $scope.newEntity = false;
    if (angular.isDefined(id)) {
        $scope.refresh();

    } else {
        $scope.office = {
            address: null,
            lat: null,
            lng: null,
            userGivenAddress: null,
            openingHours : []
        };
        $scope.title = "Új rendelés felvétele";
        $scope.newEntity = true;

    }
    
    $scope.showModified = function(prop){
        return $scope.origOffice !== null && $scope.office[prop] !== $scope.origOffice[prop];
    };

});

