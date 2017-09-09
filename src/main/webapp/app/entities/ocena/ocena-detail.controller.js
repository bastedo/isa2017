(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('OcenaDetailController', OcenaDetailController);

    OcenaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Ocena', 'Gost', 'Restoran'];

    function OcenaDetailController($scope, $rootScope, $stateParams, previousState, entity, Ocena, Gost, Restoran) {
        var vm = this;

        vm.ocena = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:ocenaUpdate', function(event, result) {
            vm.ocena = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
