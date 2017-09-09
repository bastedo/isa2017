(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RestoranDeleteController',RestoranDeleteController);

    RestoranDeleteController.$inject = ['$uibModalInstance', 'entity', 'Restoran'];

    function RestoranDeleteController($uibModalInstance, entity, Restoran) {
        var vm = this;

        vm.restoran = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Restoran.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
