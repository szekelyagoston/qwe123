angular.module('myApp').controller('VerifySpecialistController', function ($scope, $location, PictureService, AdminService) {
    $scope.$on("credentials-arrived", function () {
        if ($scope.credentials.secGroup !== "ADMIN")
            $location.path('/userpage');
    });


    var init = function () {
        AdminService.getAllNotVerifiedOrModifiedSpecialist().then(function (response) {
            $scope.specialists = response.data;
            console.log($scope.specialists[0])
            if ($scope.specialists.length > 0) {

                $scope.openSpecialist($scope.specialists[0]);

            }

        });
    };

    $scope.openSpecialist = function (specialist) {

        deselectAllSpecialist();
        specialist.selected = true;
        $scope.selectedSpecialist = angular.copy(specialist);
        ($scope.selectedSpecialist.picture === null || !angular.isDefined($scope.selectedSpecialist.picture)) ? $scope.selectedSpecialist.picture = PictureService.getEmptyPictureBase64() : $scope.selectedSpecialist.picture = PictureService.addPrefix($scope.selectedSpecialist.picture);
        ($scope.selectedSpecialist.modifiedSpecialist.picture === null || !angular.isDefined($scope.selectedSpecialist.modifiedSpecialist.picture)) ? $scope.selectedSpecialist.modifiedSpecialist.picture = PictureService.getEmptyPictureBase64() : $scope.selectedSpecialist.modifiedSpecialist.picture = PictureService.addPrefix($scope.selectedSpecialist.modifiedSpecialist.picture);
        filterProblems();
        filterQualifiations();
    };

    var filterProblems = function () {
        
        if ($scope.selectedSpecialist.modified) {
            $scope.problems = angular.copy($scope.selectedSpecialist.modifiedSpecialist.problems);

            for (var i = 0; i < $scope.problems.length; ++i) {
                $scope.problems[i].message = "Hozzáadott";
            }

        
            var oldProblems = [];
            for (var i = 0; i < $scope.selectedSpecialist.problems.length; ++i) {
                var l = false;
                var index = 0;
                for (var j = 0; j < $scope.problems.length && !l; ++j) {
                    l = $scope.problems[j].stringValue === $scope.selectedSpecialist.problems[i].stringValue;
                    index = j;
                }

                if (l) {
                    $scope.problems[index].message = null;
                } else {
                    oldProblems.push($scope.selectedSpecialist.problems[i]);
                    oldProblems[oldProblems.length - 1].message = "Törölt";
                }
            }
            $scope.problems = $scope.problems.concat(oldProblems);
        }else{
            $scope.problems = angular.copy($scope.selectedSpecialist.problems);

            for (var i = 0; i < $scope.problems.length; ++i) {
                $scope.problems[i].message = "Hozzáadott";
            }
        }

    };
    
    var filterQualifiations = function () {
        
        if ($scope.selectedSpecialist.modified) {
            $scope.qualifications = angular.copy($scope.selectedSpecialist.modifiedSpecialist.qualifications);

            for (var i = 0; i < $scope.qualifications.length; ++i) {
                $scope.qualifications[i].message = "Hozzáadott";
            }

        
            var oldQualifications = [];
            for (var i = 0; i < $scope.selectedSpecialist.qualifications.length; ++i) {
                var l = false;
                var index = 0;
                for (var j = 0; j < $scope.qualifications.length && !l; ++j) {
                    l = $scope.qualifications[j].stringValue === $scope.selectedSpecialist.qualifications[i].stringValue;
                    index = j;
                }

                if (l) {
                    $scope.qualifications[index].message = null;
                } else {
                    oldQualifications.push($scope.selectedSpecialist.qualifications[i]);
                    oldQualifications[oldQualifications.length - 1].message = "Törölt";
                }
            }
            $scope.qualifications = $scope.qualifications.concat(oldQualifications);
        }else{
            $scope.qualifications = angular.copy($scope.selectedSpecialist.qualifications);

            for (var i = 0; i < $scope.qualifications.length; ++i) {
                $scope.qualifications[i].message = "Hozzáadott";
            }
        }

    };

    var deselectAllSpecialist = function () {
        for (var i = 0; i < $scope.specialists.length; ++i) {
            $scope.specialists[i].selected = false;
        }
    };

    $scope.activate = function (specialist) {
        if (!specialist.verified) {
            AdminService.verifySpecialist(specialist.email).then(function (response) {
                $scope.specialists = response.data;
                $scope.openSpecialist($scope.specialists[0]);
            });
        } else {
            AdminService.verifyModification(specialist.email).then(function (response) {
                $scope.specialists = response.data;
                $scope.openSpecialist($scope.specialists[0]);
            });
        }



    };

    $scope.deny = function (specialist, message) {
        AdminService.denySpecialist(specialist.email, message).then(function (response) {

            $scope.specialists = response.data;
            $scope.openSpecialist($scope.specialists[0]);

        });
    };

    $scope.showModified = function (prop) {
        if (!angular.isDefined($scope.selectedSpecialist))
            return false;
        return $scope.selectedSpecialist.modified && $scope.selectedSpecialist.modifiedSpecialist !== null && $scope.selectedSpecialist.modifiedSpecialist[prop] !== $scope.selectedSpecialist[prop];
    };


    init();
});