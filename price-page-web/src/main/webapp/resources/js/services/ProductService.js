'use strict';

angular.module('price-page').service('productsService', function($resource){
	this.products = $resource('rest/products');
});