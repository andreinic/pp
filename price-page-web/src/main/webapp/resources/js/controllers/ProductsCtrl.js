'use strict';

function ProductsCtrl($scope, $http, $location){
    if(!$scope.products){
        $http({
            url : 'rest/products',
            method : 'GET',
            params : {start : 0, count : 5}
        }).success(function(data, status, headers, configs){
            var arr = [];
            for(var i = 0 ; i < data.length ; ++i){
                var product = {};
                var p = data[i];
                product.id = p["id"];
                product.name = p["name"];
                product.bigPrice = Math.floor(p["price"]);
                product.smallPrice = p["price"] - product.bigPrice;
                arr.push(product);
            }
            $scope.products = arr;
        }).error(function(data, status, headers, configs){
           //todo handle error
        });
    }

    $scope.toDetail = function(productId){
        $location.path("/produs/"+productId.toString());
    }

    //maybe move this into a categoryCtrl??
    $scope.fetchForCateg = function(categoryId){
         $http({
             url : 'rest/products',
             method : 'GET',
             params : {categoryId: 2, start : 0, count : 5}
         }).success(function(data, status, headers, configs){
             var arr = [];
             for(var i = 0 ; i < data.length ; ++i){
                 var product = {};
                 var p = data[i];
                 product.id = p["id"];
                 product.name = p["name"];
                 product.bigPrice = Math.floor(p["price"]);
                 product.smallPrice = p["price"] - product.bigPrice;
                 arr.push(product);
             }
             $scope.products = arr;
             $location.path("/produse");
         }).error(function(data, status, headers, configs){
            //todo handle error
         });
    }
}

function ProductDetailsCtrl($scope, $routeParams, $http){
    $http({
       url : 'rest/products/' + $routeParams.productId,
       method : 'GET'
    }).success(function(data, status, headers, configs){
        var p = {};
        p.id = data["id"];
        p.name = data["name"];
        p.description = data["description"];
        $scope.product = p;
    }).error(function(data, status, headers, configs){

    });
    //make this global
    $scope.geolocationAvailable = navigator.geolocation ? true : false;

    if ($scope.geolocationAvailable) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var mapOptions = {
                zoom : 8,
                center : new google.maps.LatLng(position.coords.latitude, position.coords.longitude),
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            var map = new google.maps.Map(document.getElementById("ppMap"), mapOptions);
            var locationMarker = new google.maps.Marker({
                position : mapOptions.center,
                icon : 'resources/images/client/b_poi_man.png',
                map : map
            });

            //dummy
            var closestMarker = new google.maps.Marker({
                position : new google.maps.LatLng(position.coords.latitude+0.01, position.coords.longitude+0.01),
                icon : 'resources/images/client/b_poi_green.png',
                map : map
            });
            var cheapestMarker = new google.maps.Marker({
                position : new google.maps.LatLng(position.coords.latitude+0.06, position.coords.longitude+0.06),
                icon : 'resources/images/client/b_poi_red.png',
                map : map
            });
            var otherMarker = new google.maps.Marker({
                position : new google.maps.LatLng(position.coords.latitude-0.05, position.coords.longitude-0.05),
                icon : 'resources/images/client/b_poi_blue.png',
                map : map
            });
        }, function () {});
    } else {
        var map = new google.maps.Map($('#ppMap'));
    }

}