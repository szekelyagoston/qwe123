angular.module('myApp').factory('UserService', function (LoginService, $q) {
    var credentials = {
        email: null,
        secGroup: null
    };

    var getDetails = function () {
        var deferred = $q.defer();
        LoginService.getUserDetails().then(function (response) {
            credentials = response.data;
            deferred.resolve(response);
        }, function(response){
            deferred.resolve(response);
        });

        return deferred.promise;
    };

    var getEmail = function () {
        var deferred = $q.defer();
        if (credentials.email === null) {
            getDetails().then(function () {
                deferred.resolve(credentials.email);
            });
        }
        return deferred.promise;

    };

    var getSecGroup = function () {
        var deferred = $q.defer();
        if (credentials.secGroup === null) {
            getDetails().then(function () {
                deferred.resolve(credentials.secGroup);
            });
        }
        return deferred.promise;
    };

  
    var setCredentials = function (data) {
        credentials = data;
    };
    

    var getCredentials = function () {
        var deferred = $q.defer();

        if (credentials.email === null) {
            getDetails().then(function (response) {
                if (response.status === 412){
                    deferred.resolve("NOT_AUTHORIZED");
                }else{
                    deferred.resolve(credentials);       
                }             
            }, function(response){
                console.log(response)
                deferred.resolve(response);
            });
        }else{
            deferred.resolve(credentials);
        }
        return deferred.promise;
    };
    
   

    return {
        getEmail: getEmail,
        getSecGroup: getSecGroup,
        setCredentials: setCredentials,
        getCredentials: getCredentials
        
    };
});