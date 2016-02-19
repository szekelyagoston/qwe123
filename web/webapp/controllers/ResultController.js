angular.module('myApp').controller('ResultController', function ($scope, $uibModal, $log) {


    $scope.openDetail = function (result) {
        console.log(result);
    };

    $scope.openDetail = function (result) {

        var modalInstance = $uibModal.open({
            animation: true,
            templateUrl: "webapp/partials/modals/result.html",
            controller: 'ResultModalController',
            size: "lg",
            resolve: {
                result: function () {
                    return result;
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {

        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});

