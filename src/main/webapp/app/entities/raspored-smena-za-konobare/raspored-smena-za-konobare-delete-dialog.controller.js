(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaKonobareDeleteController',RasporedSmenaZaKonobareDeleteController);

    RasporedSmenaZaKonobareDeleteController.$inject = ['$uibModalInstance', 'entity', 'RasporedSmenaZaKonobare'];

    function RasporedSmenaZaKonobareDeleteController($uibModalInstance, entity, RasporedSmenaZaKonobare) {
        var vm = this;

        vm.rasporedSmenaZaKonobare = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RasporedSmenaZaKonobare.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
