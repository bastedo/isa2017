(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PorudzbinaDetailController', PorudzbinaDetailController);

    PorudzbinaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Porudzbina', 'Jelo', 'Pice', 'Rezervacija'];

    function PorudzbinaDetailController($scope, $rootScope, $stateParams, previousState, entity, Porudzbina, Jelo, Pice, Rezervacija) {
        var vm = this;

        vm.porudzbina = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:porudzbinaUpdate', function(event, result) {
            vm.porudzbina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
