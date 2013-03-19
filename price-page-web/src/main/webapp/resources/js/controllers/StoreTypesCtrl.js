'use strict'

angular.module('price-page').controller('StoreTypesCtrl', function StoreTypesCtrl($scope, storeTypesService){
    $scope.fetch = function(){
        $scope.storeTypes = storeTypesService.getStoreTypes();
    }
    $scope.fetch();
});