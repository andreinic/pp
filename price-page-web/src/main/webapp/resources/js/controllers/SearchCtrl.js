'use strict'

function SearchCtrl($scope, $location, Search){
    $scope.search = function(){
        if($scope.queryString){
            $scope.products = Search.query({query : $scope.queryString, start: 0, count : 5});
            $location.path("/cauta");
            $scope.queryString = "";
        }
    }
}