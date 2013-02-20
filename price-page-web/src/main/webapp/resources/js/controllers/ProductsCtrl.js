'use strict';

function ProductsCtrl($scope, $location, $http){
    //make this global
    $scope.geolocationAvailable = navigator.geolocation ? true : false;

	$scope.markers = [];
	$scope.zoom = 8;

	$scope.center = {
        lat : 45,
        lng : -73
    };

    if ($scope.geolocationAvailable) {
        navigator.geolocation.getCurrentPosition(function (position) {
            $scope.center = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            $scope.markers.push({
                latitude : parseFloat($scope.center.lat),
                longitude : parseFloat($scope.center.lng)
            });
            $scope.$apply();
        }, function () {});
    } else {
    }

    $http({
        url : 'rest/products',
        method : 'GET',
        params : {start : 0, count : 5}
    }).success(function(data, status, headers, configs){
        var arr = [];
        for(var i = 0 ; i < data.length ; ++i){
            var product = {};
            var p = data[i];
            product.id = p["id"];
            product.name = p["name"];
            product.bigPrice = Math.floor(p["price"]);
            product.smallPrice = p["price"] - product.bigPrice;
            arr.push(product);
        }
        $scope.products = arr;
    }).error(function(data, status, headers, configs){
       //todo handle error
    });

    $scope.toDetail = function(){
        $location.path("/produs");
    }
}