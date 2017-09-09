(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RezervacijaDetailController', RezervacijaDetailController);

    RezervacijaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Rezervacija', 'Stol', 'Porudzbina', 'Restoran', 'Gost'];

    function RezervacijaDetailController($scope, $rootScope, $stateParams, previousState, entity, Rezervacija, Stol, Porudzbina, Restoran, Gost) {
        var vm = this;

        vm.rezervacija = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:rezervacijaUpdate', function(event, result) {
            vm.rezervacija = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
