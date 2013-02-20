'use strict'

function SearchCtrl($scope, $location, $http){
    $scope.search = function(){
        if($scope.queryString){
            $http({
                url : 'rest/search/text',
                method : "GET",
                params : {q : $scope.queryString, start : 0, count : 5}
            }).success(function(data, status, headers, configs){
                $scope.products = data;
            }).error(function(data, status, headers, configs){
                //todo - handle error.
                window.alert('error during search');
            });
            $location.path("/cauta");
            $scope.queryString = "";
        }
    }
}