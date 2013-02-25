'use strict';

function ContactCtrl($scope, $http){
    $scope.send = function(){
        if($scope.contactForm.$invalid){
            $scope.httpError = true;
            $scope.success = false;
        } else {
            $scope.httpError = false;
            $scope.success = true;
        }
    }

}