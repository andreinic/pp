'use strict';

function CategoryCtrl($scope, $http, $location, productsService){
    $scope.fetch = function(){
        $http({
            url : 'rest/categories',
            method : 'GET'
        }).success(function(data, status, headers, configs){
            $scope.categories = data;
        }).error(function(data, status, headers, configs){
            alert('error retrieving categories');
        });
    }

    $scope.fetchStoreTypes = function(){
        $http({
            url : 'rest/store-chain/stores/types',
            method : 'GET'
        }).success(function(data, status, headers, configs){
           $scope.storeTypes = data;
       }).error(function(data, status, headers, configs){
           alert('error retrieving store types');
       });
    }

    $scope.init = function(){
        if($scope.categories === undefined){
            $scope.fetch();
        }

        if($scope.storeTypes === undefined){
            $scope.fetchStoreTypes();
        }
    }

    $scope.init();


    $scope.fetchForCateg = function(catId){
       productsService.fetchForCateg(catId);
       $location.path("/produse");
    }

    $scope.fetchForStoreType = function(storeTypeId){
        productsService.fetchForStoreType(storeTypeId);
        $location.path("/produse");
    }
}