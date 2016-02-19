angular.module('myApp').controller('OpeningDaysController', function ($scope, $filter) {


    $scope.formattedOpeningDays = [];


    $scope.changeSelection = function (openingDay) {
        if (!$scope.disabled) {
            if (openingDay.active) {
                openingDay.active = false;
            } else {
                openingDay.active = true;
            }
            for (var i = 0; i < $scope.openingdays.length; ++i) {
                $scope.openingdays[i].active = $scope.formattedOpeningDays[i].active;
            }
        }

    };


    var sortOpeninghours = function () {
        $scope.openingdays.sort(function (a, b) {
            return a.sortNumber > b.sortNumber;
        });
        if ($scope.openingdays.length !== 7) {
            //dont do it at school
            for (var i = 0; i < 7; ++i) {
                if (!angular.isDefined($scope.openingdays[i]) || $scope.openingdays[i].sortNumber !== i + 1 || ($scope.openingdays[i].fromClock === null && $scope.openingdays[i].toClock === null)) {

                    $scope.openingdays.splice(i, 0, {fromClock: null, openingDay: getDayFromSortNumber(i + 1), sortNumber: i + 1, toClock: null, active: false});
                } else {
                    //when changing model, it will set everything active
                    $scope.openingdays[i]["active"] = true;
                }

            }
        }

    };




    var getDayFromSortNumber = function (index) {
        switch (index) {
            case 1 :
            {
                return "MONDAY";
                break;
            }
            case 2 :
            {
                return "TUESDAY";
                break;
            }
            case 3 :
            {
                return "WEDNESDAY";
                break;
            }
            case 4 :
            {
                return "THURSDAY";
                break;
            }
            case 5 :
            {
                return "FRIDAY";
                break;
            }
            case 6 :
            {
                return "SATURDAY";
                break;
            }
            case 7 :
            {
                return "SUNDAY";
                break;
            }
        }
    };

    var formatOpeningDays = function () {
        $scope.formattedOpeningDays = [];
        for (var i = 0; i < $scope.openingdays.length; ++i) {


            var fromClockHour = ($scope.openingdays[i].fromClock === null ? null : parseInt($filter('number')($scope.openingdays[i].fromClock / 100, 0)));
            var fromClockMin = ($scope.openingdays[i].fromClock === null ? null : parseInt($filter('number')($scope.openingdays[i].fromClock % 100, 0)));
            var toClockHour = ($scope.openingdays[i].toClock === null ? null : parseInt($filter('number')($scope.openingdays[i].toClock / 100, 0)));
            var toClockMin = ($scope.openingdays[i].toClock === null ? null : parseInt($filter('number')($scope.openingdays[i].toClock % 100, 0)));

            $scope.formattedOpeningDays.push({fromClockHour: fromClockHour, fromClockMin: fromClockMin, openingDay: $scope.openingdays[i].openingDay, sortNumber: $scope.openingdays[i].sortNumber, toClockHour: toClockHour, toClockMin: toClockMin, active: $scope.openingdays[i].active});
        }

    };

    $scope.formattedOpeningDaysChanged = function () {
        refreshModel();
    };

    var refreshModel = function () {
        for (var i = 0; i < $scope.openingdays.length; ++i) {
            $scope.openingdays[i].active = $scope.formattedOpeningDays[i].active;
            $scope.openingdays[i].fromClock = $scope.formattedOpeningDays[i].fromClockHour * 100 + $scope.formattedOpeningDays[i].fromClockMin;
            $scope.openingdays[i].toClock = $scope.formattedOpeningDays[i].toClockHour * 100 + $scope.formattedOpeningDays[i].toClockMin;
        }
    };

    $scope.$watch("openingdays", function () {
        if (angular.isDefined($scope.openingdays)) {
            sortOpeninghours();
            formatOpeningDays();
        }
    });

});
