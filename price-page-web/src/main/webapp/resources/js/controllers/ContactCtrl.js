'use strict';

function ContactCtrl($scope, $http){
    $scope.send = function(){
        if($scope.contactForm.$invalid){
        } else {
            var data = {
                contact_message : {
                    name : $scope.msg.name,
                    email : $scope.msg.email,
                    phone : $scope.msg.phone,
                    message : $scope.msg.text
                }
            };
            $http({
                headers: {'Content-Type': 'application/json'},
                url : 'rest/mail/contact',
                params : angular.toJson(data),
                method : 'PUT'
            }).success(function(data, status, headers, configs){
                $scope.msg.name = '';
                $scope.msg.email = '';
                $scope.msg.phone = '';
                $scope.msg.text = '';
                alert('Mesajul a fost trimis');
            }).error(function(data, status, headers, configs){
                alert('no');
            });
        }
    }

}