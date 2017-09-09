(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaSankereDeleteController',RasporedSmenaZaSankereDeleteController);

    RasporedSmenaZaSankereDeleteController.$inject = ['$uibModalInstance', 'entity', 'RasporedSmenaZaSankere'];

    function RasporedSmenaZaSankereDeleteController($uibModalInstance, entity, RasporedSmenaZaSankere) {
        var vm = this;

        vm.rasporedSmenaZaSankere = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RasporedSmenaZaSankere.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
