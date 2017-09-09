(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('StolDeleteController',StolDeleteController);

    StolDeleteController.$inject = ['$uibModalInstance', 'entity', 'Stol'];

    function StolDeleteController($uibModalInstance, entity, Stol) {
        var vm = this;

        vm.stol = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stol.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
