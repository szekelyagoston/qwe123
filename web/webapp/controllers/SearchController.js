angular.module('myApp').controller('SearchController', function ($scope, MainPageService, SearchService) {
    $scope.searchableSymptoms = [];
    
    $scope.searchObj = {
        symptom : null
    };
    
    var init = function(){
        MainPageService.getAllSymptoms().then(function(response){
            $scope.searchableSymptoms = response.data;
            //$scope.searchObj.symptom = $scope.searchableSymptoms[0];
            $scope.doSearch();
        });
    };
    
    $scope.doSearch = function(){
        console.log($scope.searchObj)
        SearchService.doSearch($scope.searchObj).then(function(response){
            $scope.results = response.data;
            $scope.$emit("results_changed");
        });
    };
   
    
    
    init();
    
});
