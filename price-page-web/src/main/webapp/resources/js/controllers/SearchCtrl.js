'use strict'

angular.module('price-page').controller('SearchCtrl', function SearchCtrl($scope, $location){
    $scope.search = function(){
        if($scope.queryString){
            var q = $scope.queryString;
            $scope.queryString = "";
            $location.path("/cauta?q="+q);
        }
    }
})

angular.module('price-page').controller('SearchResultsCtrl', function SearchResultsCtrl($scope, $routeParams, productsService){
    $scope.queryString = $routeParams.q
    if($scope.queryString){
        productsService.search($routeParams.q, 0, 16).query(function(result, responseHeaders){
            $scope.products = result;
        });
    }
});