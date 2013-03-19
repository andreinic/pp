'use strict';

angular.module('price-page').controller('CategoryCtrl', function CategoryCtrl($scope, $location, categoriesService){
    $scope.fetch = function(){
        $scope.categories = categoriesService.getCategories();
    }
    $scope.toProducts = function(catId){
       $location.path("/produse/categorie/"+catId.toString());
    }
    $scope.fetch();
});