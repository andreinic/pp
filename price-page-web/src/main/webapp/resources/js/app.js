'use strict';

angular.module("price-page", ['ngResource'])
	   .directive('opendialog', function(){
       		var openDialog = {
	        link : function(scope, element, attrs) {
	            	function openDialog() {
	              		var element = angular.element('#authModal');
	              		var ctrl = element.controller();
	              		element.modal('show');
	            	}
	            	element.bind('click', openDialog);
	       		}
	        }
       		return openDialog;})
       .directive('simpleCaptcha', function() {
           return {
               restrict: 'E',
               scope: { valid: '=' },
               template: '<input ng-model="a.value" ng-show="a.input" style="width:2em; text-align: center;"><span ng-hide="a.input">{{a.value}}</span>&nbsp;{{operation}}&nbsp;<input ng-model="b.value" ng-show="b.input" style="width:2em; text-align: center;"><span ng-hide="b.input">{{b.value}}</span>&nbsp;=&nbsp;{{result}}',
               controller: function($scope) {

                   var show = Math.random() > 0.5;

                   var value = function(max){
                       return Math.floor(max * Math.random());
                   };

                   var int = function(str){
                       return parseInt(str, 10);
                   };

                   $scope.a = {
                       value: show? undefined : 1 + value(4),
                       input: show
                   };
                   $scope.b = {
                       value: !show? undefined : 1 + value(4),
                       input: !show
                   };
                   $scope.operation = '+';

                   $scope.result = 5 + value(5);

                   var a = $scope.a;
                   var b = $scope.b;
                   var result = $scope.result;

                   var checkValidity = function(){
                       if (a.value && b.value) {
                           var calc = int(a.value) + int(b.value);
                           $scope.valid = calc == result;
                       } else {
                           $scope.valid = false;
                       }
                       $scope.$apply(); // needed to solve 2 cycle delay problem;
                   };


                   $scope.$watch('a.value', function(){
                       checkValidity();
                   });

                   $scope.$watch('b.value', function(){
                       checkValidity();
                   });



               }
           };
       })
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
                zoom : 12,
                center : new google.maps.LatLng(45.661327, 25.610161),
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                panControl : false,
                streetViewControl : false
            }

            if(navigator.geolocation){
                navigator.geolocation.getCurrentPosition(function (position) {
                    var userCoordinates = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                    mapOptions = {
                        zoom : 12,
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