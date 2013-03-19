'use strict';

function ProductsCtrl($scope, $location, $routeParams, $http, productsService){
    $scope.toDetail = function(productId){
        $location.path("/produs/"+productId.toString());
    }
    var makeProducts = function(data){
        var products = [];
        for(var i = 0 ; i < data.length ; ++i){
            var product = {};
            var p = data[i];
            product.id = p["id"];
            product.name = p["name"];
            var priceArr = p["price"].toString().split(".");
            product.bigPrice = priceArr[0];
            product.smallPrice = priceArr.length != 0 ? priceArr[1] : 0;
            product.imgPaths = p["imagesPaths"] ? p["imagesPaths"] : null;
            product.imgPath = p["headImagePath"] ? p["headImagePath"] : 'resources/images/client/no_image.jpg';
            products.push(product);
        }
        return products;
    }
    if($routeParams.catId){
        productsService.getProductsByCateg($routeParams.catId, 0, 16).query(function(result, responseHeaders){
            $scope.products = makeProducts(result);
        });
    } else if($routeParams.shopId){
       productsService.getProductsByStoreType($routeParams.shopId, 0, 16).query(function(result, responseHeaders){
           $scope.products = makeProducts(result);
       });
    }
//    $scope.start = 0;
//    $scope.max = 62;
//    $scope.currentPage = 0;
//    $scope.ps = 16;
//    $scope.$on("productsFetched", function(){
//        $scope.products = arguments[1];
//    });
//    $scope.numberOfPages = function(){
//        return Math.ceil($scope.max / $scope.ps);
//    }
//    if($routeParams.catId){
//        //TODO Add listener for countForCateg once correctly implemented on server side.
////        max = productsService.countForCateg($routeParams.catId);
//        if($scope.max > 0){
//            productsService.fetchForCateg($routeParams.catId, $scope.start, $scope.ps);
//            $scope.start += $scope.ps;
//        }
//    } else if($routeParams.shopId){
//        if($scope.max > 0){
//            productsService.fetchForStoreType($routeParams.shopId, $scope.start, $scope.ps);
//            $scope.start += $scope.ps;
//        }
//    }
//
//    $scope.next = function(){
//        if($scope.start < $scope.max){
//            var count;
//            $scope.start += $scope.ps;
//            if($scope.start > $scope.max) $scope.start = $scope.max;
//            if($scope.start != $scope.max){
//                count = $scope.ps;
//            } else {
//                count = 0;
//            }
//            if($routeParams.catId){
//                productsService.fetchForCateg($routeParams.catId, $scope.start, count);
//            } else if($routeParams.shopId){
//                productsService.fetchForStoreType($routeParams.shopId, $scope.start, count);
//            }
//            ++$scope.currentPage;
//        }
//    }
//    $scope.previous = function(){
//        if($scope.start > 0){
//            $scope.start = $scope.start - $scope.ps > 0 ? $scope.start - $scope.ps : 0;
//            if($routeParams.catId){
//                productsService.fetchForCateg($routeParams.catId, $scope.start, $scope.ps);
//            } else if($routeParams.shopId){
//                productsService.fetchForStoreType($routeParams.shopId, $scope.start, $scope.ps);
//            }
//            --$scope.currentPage;
//        }
//    }
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
               product.idx = i+1;
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

function ProductDetailsCtrl($rootScope, $scope, $routeParams, $http, $location){
    $scope.$on('geoActivated', function(){
       if($scope.product) $scope.drawMarkers();
    });
    $scope.toStoreChain = function(storeId){
        $location.path("/magazin/"+storeId.toString());
    }
    $scope.drawMarkers = function(){
        var stores = $scope.product.stores;
        if(stores.length > 0){
            var map = new google.maps.Map(document.getElementById("ppMap"), $rootScope.mapOptions);
            new google.maps.Marker({
                position : $rootScope.mapOptions.center,
                icon : 'resources/images/client/b_poi_man.png',
                map : map
            });
            var arr = [];
            var bestIdx = 0, closestIdx = 0;
            for(var i = 0 ; i < stores.length ; ++i){
                var s = stores[i];
                s.marker =  new google.maps.Marker({
                    position : s.coordinates,
                    icon : 'resources/images/client/b_poi_blue.png',
                    map : map
                });
                arr.push(s);
                if(i > 0){
                    if(s.price < arr[i-1].price) bestIdx = i;
                    if(google.maps.geometry.spherical.computeDistanceBetween($rootScope.userCoordinates, s.coordinates) < google.maps.geometry.spherical.computeDistanceBetween($rootScope.userCoordinates, arr[i-1].coordinates)){
                        closestIdx = i;
                    }
                }
            }
            arr[closestIdx].marker.icon = 'resources/images/client/b_poi_green.png';
            arr[bestIdx].best = true;
            arr[bestIdx].marker.icon = 'resources/images/client/b_poi_red.png';
            $scope.product.stores = arr;
        }
    }
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
                        if(google.maps.geometry.spherical.computeDistanceBetween($rootScope.userCoordinates, store.coordinates) < google.maps.geometry.spherical.computeDistanceBetween($rootScope.userCoordinates, arr[i-1].coordinates)){
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