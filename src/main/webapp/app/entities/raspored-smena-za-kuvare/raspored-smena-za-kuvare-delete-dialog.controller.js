(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaKuvareDeleteController',RasporedSmenaZaKuvareDeleteController);

    RasporedSmenaZaKuvareDeleteController.$inject = ['$uibModalInstance', 'entity', 'RasporedSmenaZaKuvare'];

    function RasporedSmenaZaKuvareDeleteController($uibModalInstance, entity, RasporedSmenaZaKuvare) {
        var vm = this;

        vm.rasporedSmenaZaKuvare = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RasporedSmenaZaKuvare.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
