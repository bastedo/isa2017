(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('ZaposleniDetailController', ZaposleniDetailController);

    ZaposleniDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Zaposleni', 'User', 'KonfiguracijaStolova', 'RasporedSmenaZaSankere', 'RasporedSmenaZaKonobare', 'RasporedSmenaZaKuvare', 'Racun', 'Restoran'];

    function ZaposleniDetailController($scope, $rootScope, $stateParams, previousState, entity, Zaposleni, User, KonfiguracijaStolova, RasporedSmenaZaSankere, RasporedSmenaZaKonobare, RasporedSmenaZaKuvare, Racun, Restoran) {
        var vm = this;

        vm.zaposleni = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:zaposleniUpdate', function(event, result) {
            vm.zaposleni = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
