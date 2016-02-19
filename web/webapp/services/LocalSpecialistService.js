angular.module('myApp').factory('LocalSpecialistService', function() {
    var specialist = null;
    var credentials = null;
    var loaded = false;
    
    var getSpecialist = function(){
        return specialist;
    };
    
    var setSpecialist = function(sp){
        specialist = sp;
    };
    
    var getCredentials = function(){
        return credentials;
    };
    
    var setCredentials = function(cr){
        credentials = cr;
    };
    
    var setLoaded = function(isLoaded){
        loaded = isLoaded;
    };
    
    var getLoaded = function(){
        return loaded;
    };
    
    
    
    return {
        getSpecialist : getSpecialist,
        setSpecialist : setSpecialist,
        getCredentials : getCredentials,
        setCredentials : setCredentials,
        setLoaded: setLoaded,
        getLoaded: getLoaded
    };
});