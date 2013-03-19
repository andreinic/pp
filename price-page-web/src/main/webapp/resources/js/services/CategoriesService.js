'use strict';

angular.module('price-page').service('categoriesService', function($resource){
    var resource = $resource('rest/categories');
    var data;
    var categories = function(){
        data = resource.query();
        return data;
    };
    return {
        getCategories : function(){
            if(data){
                return data;
            } else {
                return categories();
            }
        }
    }
});