'use strict'

angular.module('price-page').controller('StoreTypesCtrl', function StoreTypesCtrl($scope, $location, storeTypesService){
    $scope.fetch = function(){
        $scope.storeTypes = storeTypesService.getStoreTypes();
    }
     $scope.toProducts = function(storeTypeId){
       $location.path("/produse/tip-magazin/"+storeTypeId.toString());
    }
    $scope.fetch();
});