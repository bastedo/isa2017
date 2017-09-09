(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaKonobareDetailController', RasporedSmenaZaKonobareDetailController);

    RasporedSmenaZaKonobareDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RasporedSmenaZaKonobare', 'Zaposleni', 'Restoran'];

    function RasporedSmenaZaKonobareDetailController($scope, $rootScope, $stateParams, previousState, entity, RasporedSmenaZaKonobare, Zaposleni, Restoran) {
        var vm = this;

        vm.rasporedSmenaZaKonobare = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:rasporedSmenaZaKonobareUpdate', function(event, result) {
            vm.rasporedSmenaZaKonobare = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
