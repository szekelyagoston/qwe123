angular.module('myApp').controller('ResultModalController', function ($scope, $timeout, result, uiGmapGoogleMapApi) {
    $scope.result = result;
    //http://stackoverflow.com/questions/24275977/showing-a-google-map-in-angular-ui-modal
    $scope.render = true;

    $scope.map = {
        center: {latitude: 0,
            longitude: 0
        },
        zoom: 15,
        markers: []

    };
    $scope.mapControl = {};

    $scope.changeTab = function (office) {

        for (var i = 0; i < result.officeList.length; ++i) {
            result.officeList[i].active = false;
        }

        office.active = true;
        $scope.officeToShow = office;

        var marker = {
            coords: {
                latitude: $scope.officeToShow.lat,
                longitude: $scope.officeToShow.lng
            },
            id: {
                office: $scope.officeToShow,
                specialist: $scope.result
            }
        };
        $scope.map.markers = [marker];

    };

    for (var i = 1; i < result.officeList.length; ++i) {
        result.officeList[i].active = false;
    }
    result.officeList[0].active = true;
    $scope.officeToShow = result.officeList[0];


    console.log(result)

    uiGmapGoogleMapApi.then(function (maps) {
        $timeout(function () {

            $scope.mapControl.getGMap().setCenter(new google.maps.LatLng($scope.officeToShow.lat, $scope.officeToShow.latlng));
        }, 500);

    });

    window.setTimeout(function () {
        google.maps.event.trigger($scope.mapControl.getGMap(), 'resize')
    }, 300);


});