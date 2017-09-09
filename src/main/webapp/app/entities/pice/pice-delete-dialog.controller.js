(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PiceDeleteController',PiceDeleteController);

    PiceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pice'];

    function PiceDeleteController($uibModalInstance, entity, Pice) {
        var vm = this;

        vm.pice = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pice.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
