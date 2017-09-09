(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('GostDetailController', GostDetailController);

    GostDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gost', 'Rezervacija', 'ZahtevZaPrijateljstvo'];

    function GostDetailController($scope, $rootScope, $stateParams, previousState, entity, Gost, Rezervacija, ZahtevZaPrijateljstvo) {
        var vm = this;

        vm.gost = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:gostUpdate', function(event, result) {
            vm.gost = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
