angular.module("price-page", ['ngSanitize'])
       .config(function($routeProvider){
            $routeProvider.when("/", {templateUrl : 'partials/first.html'})
                          .when("/despre-noi", {templateUrl : 'partials/despre-noi.html'})
                          .when("/magazine-promovate", {templateUrl : 'partials/magazine-promovate.html'})
                          .when("/contact", {templateUrl : 'partials/contact.html'})
       });

NavCntl.$inject = ['$scope', '$route'];
function NavCntl($scope, $route){
    $scope.$route = $route;
}