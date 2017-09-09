(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RestoranDetailController', RestoranDetailController);

    RestoranDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Restoran', 'KonfiguracijaStolova', 'Jelovnik', 'KartaPica', 'Ocena', 'PozivZaPrikupljanjeN', 'Rezervacija', 'MenadzerRestorana', 'Zaposleni', 'RasporedSmenaZaSankere', 'RasporedSmenaZaKonobare', 'RasporedSmenaZaKuvare'];

    function RestoranDetailController($scope, $rootScope, $stateParams, previousState, entity, Restoran, KonfiguracijaStolova, Jelovnik, KartaPica, Ocena, PozivZaPrikupljanjeN, Rezervacija, MenadzerRestorana, Zaposleni, RasporedSmenaZaSankere, RasporedSmenaZaKonobare, RasporedSmenaZaKuvare) {
        var vm = this;

        vm.restoran = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:restoranUpdate', function(event, result) {
            vm.restoran = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
