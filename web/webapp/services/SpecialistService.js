angular.module('myApp').factory('SpecialistService', function($http) {
    
    var getSpecialist = function(){
        return $http({
                url: 'szakemberkereso/specialist/current/details',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var updateBaseData = function(data){
         console.log(angular.copy(data))
        console.log(angular.toJson(data));
        return $http({
                url: 'szakemberkereso/specialist/current/update',
                method: "POST",
                data: angular.toJson(data),
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var getOfficeById = function(id){
        return $http({
                url: 'szakemberkereso/office/getById/' + id,
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var saveNewOffice = function(dto){
        return $http({
                url: 'szakemberkereso/office/create',
                method: "POST",
                data: dto,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var getAllSpecialistIfOfficeExist = function(){
        return $http({
                url: 'szakemberkereso/specialist/all/officeExist',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    var modifyOffice = function(dto, id){

        return $http({
                url: 'szakemberkereso/office/modifyOffice/' + id,
                method: "POST",
                data: dto,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    
    return {
        getSpecialist : getSpecialist,
        updateBaseData : updateBaseData,
        getOfficeById : getOfficeById,
        saveNewOffice : saveNewOffice,
        getAllSpecialistIfOfficeExist : getAllSpecialistIfOfficeExist,
        modifyOffice :modifyOffice
    };
});

