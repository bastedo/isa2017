(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaSankereDetailController', RasporedSmenaZaSankereDetailController);

    RasporedSmenaZaSankereDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RasporedSmenaZaSankere', 'Zaposleni', 'Restoran'];

    function RasporedSmenaZaSankereDetailController($scope, $rootScope, $stateParams, previousState, entity, RasporedSmenaZaSankere, Zaposleni, Restoran) {
        var vm = this;

        vm.rasporedSmenaZaSankere = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:rasporedSmenaZaSankereUpdate', function(event, result) {
            vm.rasporedSmenaZaSankere = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
