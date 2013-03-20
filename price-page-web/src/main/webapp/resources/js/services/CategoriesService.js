'use strict';

angular.module('price-page').service('categoriesService', function($resource){
//    var resource = $resource('rest/categories');
    var data;
    var c;
    var categories = function(){
        data = $resource('rest/categories').query();
        return data;
    };
    var category = function(){
        return $resource('rest/categories/:categId', {categId : '@id'});
    }
    return {
        getCategories : function(){
            if(data){
                return data;
            } else {
                return categories();
            }
        },
        getCategory : function(){
            return category();
        }
    }
});