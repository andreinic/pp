'use strict';

angular.module("price-page", ['ngResource'])
	   .directive('opendialog', function(){
       		var openDialog = {
	        link : function(scope, element, attrs) {
	            	function openDialog() {
	              		var element = angular.element('#authModal');
	              		var ctrl = element.controller();
	              		ctrl.setModel(arguments[0].currentTarget.name == 'login');
	              		element.modal('show');
	            	}
	            	element.bind('click', openDialog);
	       		}
	        }
       		return openDialog;})
       .config(function($routeProvider){
            $routeProvider.when("/", {templateUrl : 'partials/first.html'})
                          .when("/despre-noi", {templateUrl : 'partials/despre-noi.html'})
                          .when("/magazine-promovate", {templateUrl : 'partials/magazine-promovate.html'})
                          .when("/contact", {templateUrl : 'partials/contact.html'})
                          .when("/produs/:productId", {templateUrl : 'partials/product-details.html', controller : 'ProductDetailsCtrl'})
                          .when("/cauta", {templateUrl : 'partials/search.html', controller : 'SearchCtrl'})
                          .when("/produse", {templateUrl : 'partials/search.html', controller : 'ProductsCtrl'})
                          .when("/magazin", {templateUrl : 'partials/store.html'})
       })
       .run(function($rootScope){
            var mapOptions = {
                zoom : 8,
                center : new google.maps.LatLng(45.661327, 25.610161),
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                panControl : false,
                streetViewControl : false
            }

            if(navigator.geolocation){
                navigator.geolocation.getCurrentPosition(function (position) {
                    var userCoordinates = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                    mapOptions = {
                        zoom : 8,
                        center : userCoordinates,
                        mapTypeId: google.maps.MapTypeId.ROADMAP,
                        panControl : false,
                        streetViewControl : false
                    }
                    $rootScope.mapOptions = mapOptions;
//                    $rootScope.locationMarker = locationMarker;
                    $rootScope.hasGeo = true;
                }, function () {});
            }

            $rootScope.mapOptions = mapOptions;
       });