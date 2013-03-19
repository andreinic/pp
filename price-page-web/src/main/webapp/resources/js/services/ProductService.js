'use strict';

angular.module('pp').service('productsService', function($resource){
	this.products = $resource('rest/products');
});