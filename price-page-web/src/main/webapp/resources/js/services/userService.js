'use strict';

angular.module('price-page').service('UserService', function UserService(){
   var checkFbLoginStatus = function(){
       FB.getLoginStatus(function(response) {
         if (response.status === 'connected') {
           // connected
         } else if (response.status === 'not_authorized') {
           // not_authorized
         } else {
           // not_logged_in
         }
       });
   };
   return {
    login : function(){
        FB.login(function(response){
           if(response.authResponse){
            //connected
           } else {
            //canceled
           }
        });
    }
   }
});