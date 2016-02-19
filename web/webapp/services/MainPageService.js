angular.module('myApp').factory('MainPageService', function($http) {
    
    var getAllSymptoms = function(){
        return $http({
                url: 'szakemberkereso/public/getAllSymptoms',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
        return {
            getAllSymptoms : getAllSymptoms
        };
});