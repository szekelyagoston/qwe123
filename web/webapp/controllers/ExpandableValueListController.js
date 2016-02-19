angular.module('myApp').controller('ExpandableValueListController', function ($scope) {

    $scope.itemToAdd = null;
    
    var init = function(){
        console.log($scope.allitems)
        for (var i = 1; i < $scope.allitems.length; ++i) {
            $scope.allitems[i]['selected'] = false;
        }
        selectArrivedItems();
    };
    
    
    
    var selectArrivedItems = function () {

        for (var i = 0; i < $scope.useritems.length; ++i) {
            var l = false;
            var index = 0;
            for (var j = 0; j < $scope.allitems.length && !l; ++j) {
                l = $scope.useritems[i].id === $scope.allitems[j].id;
                index = j;
            }

            if (l) {
                $scope.allitems[index].selected = true;
                
            } else {
                //not verified problems
                $scope.allitems.push({
                    stringValue: $scope.useritems[i].stringValue,
                    id: $scope.useritems[i].id,
                    selected: true,
                    //connectedSymptom: $scope.useritems[i].connectedSymptom, //is it neccessary? maybe not, we do not need this
                    verified: false,
                    extraMessage : null
                });
            }
        }
        
        
        //if ($scope.origUser !== null){
        if (angular.isDefined($scope.origuseritems)){
            for (var i = 0; i < $scope.allitems.length; ++i){
                var inOrig = false;
                var inNew = false;

                for (var j = 0; j < $scope.origuseritems.length && !inOrig; ++j){
                    inOrig =  $scope.origuseritems[j].stringValue ===  $scope.allitems[i].stringValue;
                }

                for (var j = 0; j < $scope.useritems.length && !inNew; ++j){
                    inNew =  $scope.useritems[j].stringValue ===  $scope.allitems[i].stringValue;
                }

                if (!inOrig && inNew){
                    $scope.allitems[i].extraMessage = "hozzáadás jóváhagyásra vár";
                }else if (inOrig && !inNew){
                    $scope.allitems[i].extraMessage = "törlés jóváhagyásra vár";
                }else{
                    $scope.allitems[i].extraMessage = null;
                }

            }
        }else{
            for (var i = 0; i < $scope.allitems.length; ++i){
                $scope.allitems[i].extraMessage = null;
            }
        }

        
    };
    
    
    
    
    
    var addItems = function () {
        
        $scope.useritems = [];
        for (var i = 0; i < $scope.allitems.length; ++i) {
            if ($scope.allitems[i].selected) {
                $scope.useritems.push($scope.allitems[i]);
            }
        }
        
        console.log($scope.useritems)

    };
    
    $scope.beforeSave({beforeSave: addItems});
    
    $scope.selectItem = function (item) {
        item.selected = !item.selected;
    };

    $scope.addItem = function (item) {
        console.log(item)
        $scope.allitems.push({
            stringValue: item,
            id: null,
            selected: true,
            verified: false
        });
    };


    $scope.$watch("allitems", function () {
        console.log($scope.allitems);
        if (angular.isDefined($scope.allitems) && $scope.allitems !== null) {
            init();
        }
    });

});
