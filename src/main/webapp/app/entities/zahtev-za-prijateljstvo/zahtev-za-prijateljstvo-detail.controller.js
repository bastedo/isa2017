(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('ZahtevZaPrijateljstvoDetailController', ZahtevZaPrijateljstvoDetailController);

    ZahtevZaPrijateljstvoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ZahtevZaPrijateljstvo', 'Gost'];

    function ZahtevZaPrijateljstvoDetailController($scope, $rootScope, $stateParams, previousState, entity, ZahtevZaPrijateljstvo, Gost) {
        var vm = this;

        vm.zahtevZaPrijateljstvo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:zahtevZaPrijateljstvoUpdate', function(event, result) {
            vm.zahtevZaPrijateljstvo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
