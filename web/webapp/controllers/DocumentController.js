angular.module('myApp').controller('DocumentController', function ($scope, $timeout, FileUploader) {

    $scope.upload = {
        uploadComment : ""
    };


    var uploader = $scope.uploader = new FileUploader({
        url: 'szakemberkereso/specialist/current/update/document',
        type: 'post',
        //TODO: MAKE IT WORK
        success: function (resp) {
            console.log(resp);
        }
    });
    uploader.onBeforeUploadItem = function(item) {
        var formData = [{
            name: $scope.uploader.queue[0].file.name,
            comment: $scope.upload.uploadComment
        }];
    
        Array.prototype.push.apply(item.formData, formData);
    };
    

    $scope.uploader.url = "szakemberkereso/specialist/current/update/document";

    $scope.startUpload = function () {
        if (angular.isDefined($scope.uploader.queue[0])) {
            $scope.uploader.queue[0].upload();
        }
        
        $timeout(function(){
            $scope.refreshSpecialist();
        }, 1000);
    };
    
    

});