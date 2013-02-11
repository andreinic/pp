'use strict';

function ProductsCtrl($scope, $location){
    $scope.products = [{"name":"Geanta de umar", "price":"25"},
                       {"name":"Geanta de umar2", "price":"125"},
                       {"name":"Geanta de umar", "price":"25"},
                       {"name":"Geanta de umar", "price":"25"}];

    $scope.toDetail = function(){
        $location.path("/produs");
    }
}