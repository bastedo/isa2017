(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('OcenaDeleteController',OcenaDeleteController);

    OcenaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Ocena'];

    function OcenaDeleteController($uibModalInstance, entity, Ocena) {
        var vm = this;

        vm.ocena = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Ocena.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
