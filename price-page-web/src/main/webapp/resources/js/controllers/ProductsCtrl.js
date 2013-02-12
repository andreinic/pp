'use strict';

function ProductsCtrl($scope, $location){
	$scope.center = {
		lat : 45,
		lng : -73
	};
	$scope.markers = [];
	$scope.zoom = 8;

	//move outside of method and test
	$scope.findMe = function () {
		if ($scope.geolocationAvailable) {
			navigator.geolocation.getCurrentPosition(function (position) {
				$scope.center = {
					lat: position.coords.latitude,
					lng: position.coords.longitude
				};
				$scope.$apply();
			}, function () {});
		}
	}; 

    $scope.products = [{"name":"Geanta de umar", "price":"25"},
                       {"name":"Geanta de umar2", "price":"125"},
                       {"name":"Geanta de umar", "price":"25"},
                       {"name":"Geanta de umar", "price":"25"}];

    $scope.toDetail = function(){
        $location.path("/produs");
    }
}