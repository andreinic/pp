'use strict';

angular.module("price-page", ['ngResource', "google-maps"])
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
                          .when("/produs", {templateUrl : 'partials/product-details.html', controller : 'ProductsCtrl'})
                          .when("/cauta", {templateUrl : 'partials/search.html', controller : 'SearchCtrl'})
       })
       .factory('Product', function($resource){
            return $resource('rest/products?start=:start&count=:count', {}, {
                query : {method : 'GET', params:{start:0, count:5}, isArray:true}
            })
       })
       .factory('Search', function($resource){
            return $resource('rest/search/text?q=:query,first=:start&last=:count',{},{
                query : {method:'GET', isArray:true}
            })
       });