'use strict';

function ProductsCtrl($scope, $http, $location){
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
                 var priceArr = p["price"].toString().split(".");
                 product.bigPrice = priceArr[0];
                product.smallPrice = priceArr.length != 0 ? priceArr[1] : 0;
                 arr.push(product);
             }
             $scope.products = arr;
             $location.path("/produse");
         }).error(function(data, status, headers, configs){
            //todo handle error
         });
    }
}

function PromotionsCtrl($scope, $location, $http){
    $scope.toDetail = function(productId){
        $location.path("/produs/"+productId.toString());
    }

    $scope.fetchPromotions = function(){
        $http({
              url : 'rest/products/promotions',
              method : 'GET',
              params : {start : 0, count : 8}
        }).success(function(data, status, headers, configs){
            var arr = [];
            for(var i = 0 ; i < data.length ; ++i){
               var product = {};
               var p = data[i];
               product.id = p["id"];
               product.name = p["name"];
               var priceArr = p["price"].toString().split(".");
               product.bigPrice = priceArr[0];
               product.smallPrice = priceArr.length != 0 ? priceArr[1] : null;
               arr.push(product);
            }
            $scope.products = arr;
        }).error(function(data, status, headers, configs){
             //todo handle error
        });
    }

    $scope.fetchPromotions();
}

function ProductDetailsCtrl($rootScope, $scope, $routeParams, $http){
    $http({
       url : 'rest/products/' + $routeParams.productId,
       method : 'GET'
    }).success(function(data, status, headers, configs){
        var p = {};
        p.id = data["id"];
        p.name = data["name"];
        p.description = data["description"];

        var stores = data["stores"];
        if(stores.length > 0){
            p.stores = {};

            var map = new google.maps.Map(document.getElementById("ppMap"), $rootScope.mapOptions);
//            if($rootScope.locationMarker !== undefined && $rootScope.locationMarker.map === undefined) $rootScope.locationMarker.map = map;
            if($rootScope.hasGeo){
                var locationMarker = new google.maps.Marker({
                    position : $rootScope.mapOptions.center,
                    icon : 'resources/images/client/b_poi_man.png',
                    map : map
                })
            }
            var arr = [], prices = [];
            var min, bestIdx = 0, closestIdx = 0;
            for(var i = 0 ; i < stores.length ; ++i){
                var s = stores[i];
                var store = {};
                store.id = s["id"];
                store.name = s["name"];
                store.address = s["address"];
                store.zip = s["zip"];
                store.price = s["price"];
                prices.push(store.price);
                store.coordinates = new google.maps.LatLng(s["latitude"], s["longitude"]);
                store.marker =  new google.maps.Marker({
                    position : store.coordinates,
                    icon : 'resources/images/client/b_poi_blue.png',
                    map : map
                });
                arr.push(store);
                if(i > 0){
                    if(store.price < arr[i-1].price) bestIdx = i;
                    if($rootScope.hasGeo){
                        if(google.maps.geometry.spherical.computeDistanceBetween(userCoordinates, store.coordinates) < google.maps.geometry.spherical.computeDistanceBetween(userCoordinates, arr[i-1].coordinates)){
                            closestIdx = i;
                        }
                    }
                }
            }
            arr[bestIdx].best = true;
            arr[bestIdx].marker.icon = 'resources/images/client/b_poi_red.png';
            if($rootScope.hasGeo && bestIdx != closestIdx) arr[closestIdx].marker.icon = 'resources/images/client/b_poi_green.png';
            p.stores = arr;
            prices.sort();

            var min = prices[0];
            var max = prices[prices.length - 1];

            var maxArr = max.toString().split(".");
            p.maxBigPrice = maxArr[0];
            p.maxSmallPrice = maxArr.length != 0 ? maxArr[1] : null;
//            if(min != max){ need to figure out angularjs stuff.
                var minArr = min.toString().split(".");
                p.minBigPrice = minArr[0];
                p.minSmallPrice = minArr.length != 0 ? minArr[1] : null;
//            }
        } else {
            //TODO Display that product is not present in any shop
        }

        $scope.product = p;
    }).error(function(data, status, headers, configs){

    });
}