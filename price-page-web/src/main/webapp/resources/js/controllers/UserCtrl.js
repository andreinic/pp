'use strict';

function UserCtrl($scope){
//    this.setModel = function(data){
////       $scope.$apply(function(){
//          $scope.auth = data;
////       });
//    }
//    $scope.setModel = this.setModel;

    $scope.choose = function(data){
        $scope.auth = data;
//        $scope.$apply();
    }
    $scope.close = function(){
        $('.modal').modal('hide');
    }
}