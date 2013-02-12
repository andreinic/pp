'use strict';

angular.module("price-page", ['ngResource', "google-maps", "ui"])
       .config(function($routeProvider){
            $routeProvider.when("/", {templateUrl : 'partials/first.html'})
                          .when("/despre-noi", {templateUrl : 'partials/despre-noi.html'})
                          .when("/magazine-promovate", {templateUrl : 'partials/magazine-promovate.html'})
                          .when("/contact", {templateUrl : 'partials/contact.html'})
                          .when("/produs", {templateUrl : 'partials/product-details.html', controller : 'ProductsCtrl'})
                          .when("/cauta", {templateUrl : 'partials/search.html', controller : 'SearchCtrl'})
       });