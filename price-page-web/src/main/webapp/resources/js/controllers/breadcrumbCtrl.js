'use strict';

angular.module('price-page').controller('BreadcrumbCtrl', function BreadcrumbCtrl($rootScope, $scope){
   $rootScope.$on('breadcrumb-set', function(){
    $scope.items = arguments[1];
   });
});