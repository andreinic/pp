'use strict';

function ContactCtrl($scope, $resource){
    $scope.send = function(){
        if($scope.contactForm.$invalid){
        } else {
            var Msg = $resource('rest/mail/contact', {});
            var msg = new Msg();
            msg.name = $scope.msg.name;
            msg.email = $scope.msg.email;
            msg.phone = $scope.msg.phone;
            msg.message = $scope.msg.text;
            msg.$save(function(u, putResponseHeaders){
                alert("Mesajul a fost trimis");
                $scope.msg.name = "";
                $scope.msg.email = "";
                $scope.msg.phone = "";
                $scope.msg.text = "";
            });
        }
    }
}

function ProposalCtrl($scope, $resource){
    $scope.init = function(){
        $scope.prop = {};
        $scope.prop.storeType = "Hypermarket";
        $scope.prop.category = "Toate";
    }

    $scope.sendProposal = function(){
        if($scope.recommendForm.$invalid){

        } else {
            var Msg = $resource('rest/mail/proposal', {});
            var msg = new Msg();
            msg.storeName = $scope.prop.name;
            msg.storeType = $scope.prop.storeType;
            msg.address = $scope.prop.address;
            msg.phone = $scope.prop.phone;
            msg.category = $scope.prop.category;
            msg.$save(function(u, putResponseHeaders){
                alert('Propunerea a fost trimisa');
                $scope.prop.name = "";
                $scope.prop.storeType = "Hypermarket";
                $scope.prop.address = "";
                $scope.prop.phone = "";
                $scope.prop.category = "Toate";
            });
        }
    }

    $scope.init();
}