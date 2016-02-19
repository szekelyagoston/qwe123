angular.module('myApp').controller('ValueListsController', function ($scope, AdminService){
    
    
    
    var init = function(){
        AdminService.getAllNotVerifiedProblems().then(function(response){
            $scope.problems = response.data;
        });
        AdminService.getAllNotVerifiedQualifications().then(function(response){
            console.log(response.data)
            $scope.qualifications = response.data;
        });
    };
    
   
    
    //problems. could also be a directive
    $scope.problemToAdd = null;
    $scope.addProblem = function(item){
        $scope.problems.push({
            stringValue : item,
            //TODO: CHECK
            id: null,
            selected : true,
            verified : false
        });
    };
    
    $scope.selectProblem = function(problem){
        problem.selected = !problem.selected;
    };
    
    $scope.verifyProblems = function(){
        var selectedProblems = [];
        for (var i = 0; i < $scope.problems.length; ++i){
            if ($scope.problems[i].selected){
                selectedProblems.push($scope.problems[i]);
            }
        }

        AdminService.verifyProblems(selectedProblems).then(function(response){
            $scope.problems = response.data;
        });
    };
    
    //QUALIFICATIONS
    $scope.qualificationToAdd = null;
    $scope.addQualification = function(item){
        $scope.qualifications.push({
            stringValue : item,
            id: null,
            selected : true,
            verified : false
        });
    };
    
    $scope.selectQualification = function(qual){
        qual.selected = !qual.selected;
    };
    
    $scope.verifyQualifications = function(){
        var selectedQualifications = [];
        for (var i = 0; i < $scope.qualifications.length; ++i){
            if ($scope.qualifications[i].selected){
                selectedQualifications.push($scope.qualifications[i]);
            }
        }

        AdminService.verifyQualifications(selectedQualifications).then(function(response){
            $scope.qualifications = response.data;
        });
    };
    
    init();
});
