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
                          .when("/magazin", {templateUrl : 'partials/store.html'})
       });