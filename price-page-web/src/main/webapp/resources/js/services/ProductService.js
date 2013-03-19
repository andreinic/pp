'use strict';

angular.module('price-page').service('productsService', function($resource){
    return {
        getProductsByCateg : function(cid, s, c){
            return $resource('rest/products/category/get', {categoryId : cid, start : s, count : c});
        },
        getProductsByStoreType : function(sid, s, c){
            return $resource('rest/products', {storeTypeId : sid, start : s, count : c});
        },
        search : function(q, s, c){
            return $resource('rest/search/text', {q : q, start : s, count : c})
        }
    }
});