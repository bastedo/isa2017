(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PorudzbinaDeleteController',PorudzbinaDeleteController);

    PorudzbinaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Porudzbina'];

    function PorudzbinaDeleteController($uibModalInstance, entity, Porudzbina) {
        var vm = this;

        vm.porudzbina = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Porudzbina.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
