angular.module('myApp').controller('MapController', function ($scope, uiGmapGoogleMapApi, PictureService) {
    
    $scope.mapControl = {};
    var geocoder = new google.maps.Geocoder;
    
    
    uiGmapGoogleMapApi.then(function (maps) {

        var success = function (geoposition) {
            $scope.mapControl.getGMap().setCenter(new google.maps.LatLng(geoposition.coords.latitude, geoposition.coords.longitude));
        };
        
        navigator.geolocation.getCurrentPosition(success);

    });
    
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
                
                $scope.$apply();
                
                
            }
        },
        markersEvents: {
            click: function(marker, eventName, model, args) {
               console.log(marker);
            }
      }
    };
    
    $scope.$on('offices_arrived', function(){
        $scope.map.markers = [];
        for (var i = 0; i < $scope.results.length; ++i){
            setPicture($scope.results[i]);
            for (var j = 0; j < $scope.results[i].officeList.length; ++j){
                 var marker = {
                    coords: {
                        latitude: $scope.results[i].officeList[j].lat,
                        longitude: $scope.results[i].officeList[j].lng
                    },
                    id: {
                        office: $scope.results[i].officeList[j],
                        specialist: $scope.results[i]
                    },
                    windowOptions : {
                        visible: false
                    }
                };
                
                $scope.map.markers.push(marker);
            }
           
        }
        
       console.log($scope.results); 
    });
   
    $scope.closeClick = function(object) {
        console.log(object)
        //$scope.windowOptions.visible = false;
    };
    
    var setPicture = function(specialist){
        specialist.picture === null ? specialist.picture = PictureService.getEmptyPictureBase64() : specialist.picture = PictureService.addPrefix(specialist.picture);
    };
  
});



