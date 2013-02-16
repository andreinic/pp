'use strict';

function ProductsCtrl($scope, $location, Product, Search){
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

    $scope.products = Product.query();

    $scope.toDetail = function(){
        $location.path("/produs");
    }

    $scope.search = function(){
        $scope.products = Search.query();
        $location.path("/cauta");
    }
}