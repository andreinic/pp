'use strict';

angular.module("price-page", ['ngResource'])
	   .directive('opendialog', function(){
       		var openDialog = {
	        link : function(scope, element, attrs) {
	            	function openDialog() {
	              		var element = angular.element('#authModal');
	              		var ctrl = element.controller();
	              		element.modal('show');
	            	}
	            	element.bind('click', openDialog);
	       		}
	        }
       		return openDialog;})
       .directive('simple-captcha', function() {
           return {
               restrict: 'E',
               scope: { valid: '=' },
               template: '<input ng-model="a.value" ng-show="a.input" style="width:2em; text-align: center;"><span ng-hide="a.input">{{a.value}}</span>&nbsp;{{operation}}&nbsp;<input ng-model="b.value" ng-show="b.input" style="width:2em; text-align: center;"><span ng-hide="b.input">{{b.value}}</span>&nbsp;=&nbsp;{{result}}',
               controller: function($scope) {

                   var show = Math.random() > 0.5;

                   var value = function(max){
                       return Math.floor(max * Math.random());
                   };

                   var int = function(str){
                       return parseInt(str, 10);
                   };

                   $scope.a = {
                       value: show? undefined : 1 + value(4),
                       input: show
                   };
                   $scope.b = {
                       value: !show? undefined : 1 + value(4),
                       input: !show
                   };
                   $scope.operation = '+';

                   $scope.result = 5 + value(5);

                   var a = $scope.a;
                   var b = $scope.b;
                   var result = $scope.result;

                   var checkValidity = function(){
                       if (a.value && b.value) {
                           var calc = int(a.value) + int(b.value);
                           $scope.valid = calc == result;
                       } else {
                           $scope.valid = false;
                       }
//                       $scope.$apply(); // needed to solve 2 cycle delay problem;
                   };


                   $scope.$watch('a.value', function(){
                       checkValidity();
                   });

                   $scope.$watch('b.value', function(){
                       checkValidity();
                   });



               }
           };
       }).factory('productsService', function($rootScope, $http){
            var build = function(data){
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
                return arr;
            }
            var productsService = {};
            productsService.fetchForCateg = function(catId){
                 $http({
                     url : 'rest/products',
                     method : 'GET',
                     params : {categoryId: catId, start : 0, count : 5}
                 }).success(function(data, status, headers, configs){
                     $rootScope.$broadcast("productsChanged", build(data));
                 }).error(function(data, status, headers, configs){
                    alert('error retrieving products');
                 });
            }
            productsService.fetchForStoreType = function(stId){
                 $http({
                     url : 'rest/products',
                     method : 'GET',
                     params : {storeTypeId: stId, start : 0, count : 5}
                 }).success(function(data, status, headers, configs){
                     $rootScope.$broadcast("productsChanged", build(data));
                 }).error(function(data, status, headers, configs){
                    alert('error retrieving products');
                 });
            }
            return productsService;
       }).config(function($routeProvider){
            $routeProvider.when("/", {templateUrl : 'partials/first.html'})
                          .when("/despre-noi", {templateUrl : 'partials/despre-noi.html'})
                          .when("/magazine-promovate", {templateUrl : 'partials/magazine-promovate.html'})
                          .when("/contact", {templateUrl : 'partials/contact.html'})
                          .when("/produs/:productId", {templateUrl : 'partials/product-details.html', controller : 'ProductDetailsCtrl'})
                          .when("/cauta", {templateUrl : 'partials/search.html', controller : 'SearchCtrl'})
                          .when("/produse", {templateUrl : 'partials/products.html', controller : 'ProductsCtrl'})
                          .when("/magazin", {templateUrl : 'partials/store.html'})
                          .when("/cum-compar", {templateUrl : 'partials/compare.html'})
                          .when("/termeni-si-conditii", {templateUrl : 'partials/terms.html'})
                          .when("/browser-incompatibil", {templateUrl : 'partials/warning.html'})
       })
       .run(function($rootScope, $location){
            var BrowserDetect = {
                init: function () {
                    this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
                    this.version = this.searchVersion(navigator.userAgent)
                        || this.searchVersion(navigator.appVersion)
                        || "an unknown version";
                    this.OS = this.searchString(this.dataOS) || "an unknown OS";
                },
                searchString: function (data) {
                    for (var i=0;i<data.length;i++)	{
                        var dataString = data[i].string;
                        var dataProp = data[i].prop;
                        this.versionSearchString = data[i].versionSearch || data[i].identity;
                        if (dataString) {
                            if (dataString.indexOf(data[i].subString) != -1)
                                return data[i].identity;
                        }
                        else if (dataProp)
                            return data[i].identity;
                    }
                },
                searchVersion: function (dataString) {
                    var index = dataString.indexOf(this.versionSearchString);
                    if (index == -1) return;
                    return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
                },
                dataBrowser: [
                    {
                        string: navigator.userAgent,
                        subString: "Chrome",
                        identity: "Chrome"
                    },
                    { 	string: navigator.userAgent,
                        subString: "OmniWeb",
                        versionSearch: "OmniWeb/",
                        identity: "OmniWeb"
                    },
                    {
                        string: navigator.vendor,
                        subString: "Apple",
                        identity: "Safari",
                        versionSearch: "Version"
                    },
                    {
                        prop: window.opera,
                        identity: "Opera",
                        versionSearch: "Version"
                    },
                    {
                        string: navigator.vendor,
                        subString: "iCab",
                        identity: "iCab"
                    },
                    {
                        string: navigator.vendor,
                        subString: "KDE",
                        identity: "Konqueror"
                    },
                    {
                        string: navigator.userAgent,
                        subString: "Firefox",
                        identity: "Firefox"
                    },
                    {
                        string: navigator.vendor,
                        subString: "Camino",
                        identity: "Camino"
                    },
                    {		// for newer Netscapes (6+)
                        string: navigator.userAgent,
                        subString: "Netscape",
                        identity: "Netscape"
                    },
                    {
                        string: navigator.userAgent,
                        subString: "MSIE",
                        identity: "Explorer",
                        versionSearch: "MSIE"
                    },
                    {
                        string: navigator.userAgent,
                        subString: "Gecko",
                        identity: "Mozilla",
                        versionSearch: "rv"
                    },
                    { 		// for older Netscapes (4-)
                        string: navigator.userAgent,
                        subString: "Mozilla",
                        identity: "Netscape",
                        versionSearch: "Mozilla"
                    }
                ],
                dataOS : [
                    {
                        string: navigator.platform,
                        subString: "Win",
                        identity: "Windows"
                    },
                    {
                        string: navigator.platform,
                        subString: "Mac",
                        identity: "Mac"
                    },
                    {
                           string: navigator.userAgent,
                           subString: "iPhone",
                           identity: "iPhone/iPod"
                    },
                    {
                        string: navigator.platform,
                        subString: "Linux",
                        identity: "Linux"
                    }
                ]

            };
            BrowserDetect.init();

            if(BrowserDetect.browser === "Explorer" && BrowserDetect.version < 9){
                $location.path("/browser-incompatibil")
            }

            var mapOptions = {
                zoom : 12,
                center : new google.maps.LatLng(45.661327, 25.610161),
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                panControl : false,
                streetViewControl : false
            }

            if(navigator.geolocation){
                navigator.geolocation.getCurrentPosition(function (position) {
                    var userCoordinates = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                    mapOptions = {
                        zoom : 12,
                        center : userCoordinates,
                        mapTypeId: google.maps.MapTypeId.ROADMAP,
                        panControl : false,
                        streetViewControl : false
                    }
                    $rootScope.mapOptions = mapOptions;
//                    $rootScope.locationMarker = locationMarker;
                    $rootScope.hasGeo = true;
                }, function () {});
            }

            $rootScope.mapOptions = mapOptions;
       });