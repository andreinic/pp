'use strict';

angular.module('price-page').service('storeTypesService', function($resource){
    var resource = $resource('rest/store-chain/stores/types');
    var data;
    var storeTypes = function(){
        data = resource.query();
        return data;
    }
    var storeType = function(){
        return $resource('rest/store-chain/stores/types/:storeTypeId', {storeTypeId : '@id'});
    }
    return {
        getStoreTypes : function(){
            if(data){
                return data;
            } else {
                return storeTypes();
            }
        }
    }
});