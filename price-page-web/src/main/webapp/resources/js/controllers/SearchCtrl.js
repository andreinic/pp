'use strict';

function SearchCtrl($scope, $location){
	$scope.search = function(){
		$location.path("/cauta");
	}
}