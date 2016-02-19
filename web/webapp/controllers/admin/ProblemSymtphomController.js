angular.module('myApp').controller('ProblemSymtphomController', function ($scope, $location, AdminService, $q) {
    $scope.$on("credentials-arrived", function(){
        if ($scope.credentials.secGroup !=="ADMIN") $location.path('/userpage');
        
    });    
    
    $scope.problems = [];
    $scope.symptoms = [];
    $scope.selectedSymptoms = [];
   
    var getProblems = function(){
        var deferred = $q.defer();
        AdminService.getProblemsWithSymptoms().then(function(response){
            $scope.problems = response.data;

            for (var i = 1; i < $scope.problems.length; ++i){
                $scope.problems[i]['selected'] = false;
            }
            deferred.resolve();
        });
        
        return deferred.promise;
    };
    
    var getSymptoms = function(){
        var deferred = $q.defer();
        AdminService.getAllSymptoms().then(function(response){
            
            $scope.symptoms = response.data;
            for (var i = 0; i < $scope.symptoms.length; ++i){
                $scope.symptoms[i]['selected'] = false;
            }
            deferred.resolve();
        });
        return deferred.promise;
    };
    

    
    var init = function(){
        var promises = [];
        promises.push(getProblems());
        promises.push(getSymptoms());
        
        $q.all(promises).then(function(){
            console.log($scope.problems)
            $scope.selectProblem($scope.problems[0]);
        });
        
        
    };
    
    $scope.selectProblem = function(problem){
        for (var i = 0; i < $scope.problems.length; ++i){
            $scope.problems[i]['selected'] = false;
        }
        problem.selected = true;
        $scope.selectedSymptoms =  problem.connectedSymptom;
        $scope.selectSymptoms();
    };
    
    $scope.selectSymptoms = function(){
        for (var i = 0; i < $scope.symptoms.length; ++i){
            $scope.symptoms[i]['selected'] = false;
        }
       
        for (var i = 0; i < $scope.selectedSymptoms.length; ++i){
           for (var j = 0; j < $scope.symptoms.length; ++j){
               if ($scope.selectedSymptoms[i].stringValue === $scope.symptoms[j].stringValue){
                   $scope.symptoms[j].selected = true;
               }
           }
            
        }
    };
    
    $scope.selectSymptom = function(symptom){
        if (symptom.selected){
            
            for (var i = 0; i < $scope.selectedSymptoms.length; ++i){
                if ($scope.selectedSymptoms[i].stringValue === symptom.stringValue){
                    $scope.selectedSymptoms.splice(i, 1);
                };
            }
            
            symptom.selected = false;
        }else{
            $scope.selectedSymptoms.push(symptom);
            symptom.selected = true;
        }
    };
    
    $scope.addSymptom = function(item){
         $scope.symptoms.push({
            stringValue : item,
            symptomId: null,
            selected : false
        });
    };
    
    $scope.addProblem = function(item){
        $scope.problems.push({
            stringValue : item,
            problemId: null,
            selected : false,
            connectedSymptom : []
        });
    };
    
    $scope.save = function(){

        AdminService.saveProblemSymptomRelation($scope.problems).then(function(response){
            init();
        });
    };
    
    init();
});