(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PorudzbinaZANabavkuDeleteController',PorudzbinaZANabavkuDeleteController);

    PorudzbinaZANabavkuDeleteController.$inject = ['$uibModalInstance', 'entity', 'PorudzbinaZANabavku'];

    function PorudzbinaZANabavkuDeleteController($uibModalInstance, entity, PorudzbinaZANabavku) {
        var vm = this;

        vm.porudzbinaZANabavku = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PorudzbinaZANabavku.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
