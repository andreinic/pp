'use strict';

angular.module('price-page').controller('CategoryCtrl', function CategoryCtrl($scope, $location, categoriesService){
    $scope.fetch = function(){
        $scope.categories = categoriesService.getCategories();
    }
    $scope.toProducts = function(cat){
       var cUrl = "/produse/categorie/"+cat.id.toString();
       $location.path(cUrl);
    }
    $scope.fetch();
});