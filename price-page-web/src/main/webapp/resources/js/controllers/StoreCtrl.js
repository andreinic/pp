'use strict';

function StoreCtrl($scope, $routeParams, $http){
    $scope.init = function(){
        var mapOptions = {
            zoom : 12,
            center : new google.maps.LatLng(45.661327, 25.610161),
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            panControl : false,
            streetViewControl : false
        }
        var map = new google.maps.Map(document.getElementById("shopChainMap"), mapOptions);
        $http({
           url : 'rest/store-chain/stores/'+$routeParams.storeId,
           method : 'GET'
        }).success(function(data, status, headers, configs){
           $scope.name = data['name'];
           $scope.description = 'no description';
           if(data['stores'] !== undefined){
                for(var s=0;s<data['stores'].length;++s){
                    var st = data['stores'][s];
                    var c = new google.maps.LatLng(st["latitude"], st["longitude"]);
                    new google.maps.Marker({
                        position : c,
                        icon : 'resources/images/client/b_poi_blue.png',
                        map : map
                    });
                }
           }
           var arr = [];
           if(data['topProducts'] !== undefined){
               for(var i=0; i < data['topProducts'].length ; ++i){
                   var product = {};
                   var p = data['topProducts'][i];
                   product.idx = i+1;
                   product.id = p["id"];
                   product.name = p["name"];
                   var priceArr = p["price"].toString().split(".");
                   product.bigPrice = priceArr[0];
                   product.smallPrice = priceArr.length != 0 ? priceArr[1] : null;
                   arr.push(product);
               }
           }
           $scope.products = arr;

        }).error(function(data, status, headers, configs){
            alert("unable to retrieve store chain information");
        });
    };
    $scope.init();
}