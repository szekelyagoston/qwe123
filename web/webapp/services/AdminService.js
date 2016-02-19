angular.module('myApp').factory('AdminService', function($http) {
    var getProblemsWithSymptoms = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllProblemWithSymptoms',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
    
    var getAllNotVerifiedProblems = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllNotVerifiedProblems',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
    var getAllSymptoms = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllSymptoms',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
    
     var getAllVerifiedProblems = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllVerifiedProblems',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
    var saveProblemSymptomRelation = function(data){
        return $http({
                url: 'szakemberkereso/admin/saveProblemSymptomRelation',
                method: "POST",
                data : data,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        

        
    var getAllNotVerifiedOrModifiedOfficeWithSpecialist = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllNotVerifiedOrModifiedOfficeWithSpecialist',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
     
    var verifyOffice = function(id){
        return $http({
                url: 'szakemberkereso/admin/verifyOffice/' +id,
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
    var denyOffice = function(id, message){
        return $http({
                url: 'szakemberkereso/admin/denyOffice/' +id,
                method: "POST",
                data:message,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
           
    var getAllNotVerifiedOrModifiedSpecialist = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllNotVerifiedOrModifiedSpecialist',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };

        
    var verifySpecialist = function(id){
        return $http({
                url: 'szakemberkereso/admin/verifySpecialist/' +id,
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
    var denySpecialist = function(email, message){
        return $http({
                url: 'szakemberkereso/admin/denySpecialist/' +email,
                method: "POST",
                data:message,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
    var verifyModification = function(email){
        return $http({
                url: 'szakemberkereso/admin/verifyModification/' +email,
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
    var verifyOfficeModification = function(id){
        return $http({
                url: 'szakemberkereso/admin/verifyOfficeModification/' +id,
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
        
    var verifyProblems = function(problems){
        return $http({
                url: 'szakemberkereso/admin/verifyProblems',
                method: "POST",
                data:problems,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
    var verifyQualifications = function(problems){
        return $http({
                url: 'szakemberkereso/admin/verifyQualifications',
                method: "POST",
                data:problems,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
        
        
    var getAllVerifiedQualifications = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllVerifiedQualifications',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
    var getAllNotVerifiedQualifications = function(){
        return $http({
                url: 'szakemberkereso/admin/getAllNotVerifiedQualifications',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
        };
        
        
        
    
    
    return {
        getProblemsWithSymptoms : getProblemsWithSymptoms,
        getAllSymptoms : getAllSymptoms,
        saveProblemSymptomRelation : saveProblemSymptomRelation,
        verifyOffice : verifyOffice,
        denyOffice : denyOffice,
        getAllNotVerifiedOrModifiedSpecialist : getAllNotVerifiedOrModifiedSpecialist,
        verifySpecialist : verifySpecialist,
        denySpecialist : denySpecialist,
        getAllNotVerifiedProblems : getAllNotVerifiedProblems,
        verifyProblems : verifyProblems,
        getAllVerifiedProblems : getAllVerifiedProblems,
        verifyModification : verifyModification,
        getAllNotVerifiedOrModifiedOfficeWithSpecialist : getAllNotVerifiedOrModifiedOfficeWithSpecialist,
        verifyOfficeModification : verifyOfficeModification,
        getAllVerifiedQualifications : getAllVerifiedQualifications,
        getAllNotVerifiedQualifications : getAllNotVerifiedQualifications,
        verifyQualifications : verifyQualifications
    };
});