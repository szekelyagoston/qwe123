angular.module('myApp').factory('LoginService', function($http) {

    var doLogin = function(credentials){
        return $http({
                url: 'szakemberkereso/user/login',
                method: "POST",
                data: credentials,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
       
    };
    var doOnlyAdmin = function(credentials){
        return $http({
                url: 'szakemberkereso/user/onlyAdmin',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
       
    };
    
    var doOnlyUser = function(credentials){
        return $http({
                url: 'szakemberkereso/user/onlyUser',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
       
    };
    
    
    var doLogout = function(){
        return $http({
                url: 'szakemberkereso/user/logout',
                method: "POST",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var doRegistration = function(credentials){
        return $http({
                url: 'szakemberkereso/user/register',
                method: "POST",
                data: credentials,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var getUserDetails = function(){
        return $http({
                url: 'szakemberkereso/user/userDetails',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var activateUser = function(regCode){
        return $http({
                url: 'szakemberkereso/user/activate',
                method: "POST",
                data: regCode,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    var newPassword = function(credentials){
        return $http({
                url: 'szakemberkereso/user/newpassword',
                method: "POST",
                data: credentials,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var changePassword = function(password){

        return $http({
                url: 'szakemberkereso/user/changepassword',
                method: "POST",
                data: password,
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    
    var isLoggedIn = function(password){

        return $http({
                url: 'szakemberkereso/user/isloggedin',
                method: "GET",
                headers: {
                    'Content-Type': 'application/json'
                }
            });
    };
    

  return {
    doLogin : doLogin,
    doOnlyAdmin: doOnlyAdmin,
    doOnlyUser: doOnlyUser,
    doLogout : doLogout,
    doRegistration : doRegistration,
    getUserDetails : getUserDetails,
    activateUser : activateUser,
    newPassword : newPassword,
    changePassword : changePassword,
    isLoggedIn : isLoggedIn
  };
});
