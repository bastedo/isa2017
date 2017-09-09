(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('JeloDeleteController',JeloDeleteController);

    JeloDeleteController.$inject = ['$uibModalInstance', 'entity', 'Jelo'];

    function JeloDeleteController($uibModalInstance, entity, Jelo) {
        var vm = this;

        vm.jelo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Jelo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
