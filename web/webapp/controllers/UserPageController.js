angular.module('myApp').controller('UserPageController', function ($scope, $timeout, FileUploader, SpecialistService, AdminService, $q) {
    $scope.inEditMode = false;
    $scope.pictureToUpload = null;

    $scope.uploader = new FileUploader();
    //not working. why?
    $scope.uploader.onSuccessItem(function () {
        $scope.refreshSpecialist();
    });

    $scope.origUser = null;
    $scope.modifiedEntity = false;
    $scope.problems = null;

    $scope.uploader.url = "szakemberkereso/specialist/current/update/picture"
    //removing data binding
    $scope.$watch('specialist', function () {
        $scope.currentUser = angular.copy($scope.specialist);
        console.log($scope.currentUser)
        console.log($scope.specialist)
        //currentUser-modified -> modified mehet currentbe
        //nem modified, marad minden a régiben
        if (angular.isDefined($scope.currentUser)) {
            if ($scope.currentUser.modified) {
                //modified-ot fogjuk változtatni
                $scope.origUser = angular.copy($scope.currentUser);
                $scope.currentUser = angular.copy($scope.currentUser.modifiedSpecialist);
                $scope.modifiedEntity = true;
            }
            init();
        }

    });

    //could be in the directive
    var getProblems = function () {
        var deferred = $q.defer();

        AdminService.getAllVerifiedProblems().then(function (response) {
            $scope.problems = response.data;
            deferred.resolve();
        });

        return deferred.promise;
    };
    
    var getQualifications = function () {
        var deferred = $q.defer();

        AdminService.getAllVerifiedQualifications().then(function (response) {
            $scope.qualifications = response.data;
            deferred.resolve();
        });

        return deferred.promise;
    };

    var init = function () {
        getProblems();
        getQualifications();
    };

    $scope.startEdit = function () {
        $scope.inEditMode = true;
    };

    $scope.setDirectiveFnAddProblems = function (directiveFn) {
        $scope.beforeSaveAddProblems = directiveFn;
    };

     $scope.setDirectiveFnAddQualifications = function (directiveFn) {
        $scope.beforeSaveAddQualifications = directiveFn;
    };

    $scope.commitEdit = function () {


        //addProblems();
        $scope.beforeSaveAddProblems();
        $scope.beforeSaveAddQualifications();
        console.log($scope.uploader.queue[0])
        //uploading everything else
        console.log(angular.copy($scope.currentUser));
        delete $scope.currentUser.picture;
        $timeout(function () {
            SpecialistService.updateBaseData($scope.currentUser).then(function () {
                $scope.inEditMode = false;
                //ng-file-upload should have sth callback, so timeout would not be needed
                //uploading picture
                if (angular.isDefined($scope.uploader.queue[0])) {
                    $scope.uploader.queue[0].upload();
                }
                $timeout(function () {
                    $scope.refreshSpecialist();
                }, 500);



            }, function () {
                //error
                $scope.inEditMode = false;
                $scope.refreshSpecialist();

            });
        }, 200);

    };



    $scope.showModified = function (prop) {
        return $scope.origUser !== null && $scope.currentUser[prop] !== $scope.origUser[prop];
    };





});