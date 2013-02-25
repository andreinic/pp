'use strict';

function TosCtrl($scope){
    $scope.idx = 0;
    $scope.choose = function(index){
        if(index > 2){
            $scope.idx = 0;
            return;
        }
        $scope.idx = index;
    }
}