'use strict';

function UserCtrl($scope, UserService){
    $scope.choose = function(data){
        $scope.auth = data;
    }
    $scope.close = function(){
        $('.modal').modal('hide');
    }
    $scope.fbLogin = function(){
        UserService.login();
    }
}