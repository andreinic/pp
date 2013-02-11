'use strict';

function ContactCtrl($scope, $http){
    $scope.httpError = false;
    $scope.success = false;
    $scope.send = function(){
        $scope.success = true;
    }
}