angular.module('myApp').factory('SearchService', function($http) {
     var doSearch = function(searchObject){
        return $http({
                url: 'szakemberkereso/search',
                method: "POST",
                data: searchObject,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
       
    };
    
    return {
        doSearch: doSearch
    };
});

