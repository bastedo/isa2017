(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('ZaposleniDeleteController',ZaposleniDeleteController);

    ZaposleniDeleteController.$inject = ['$uibModalInstance', 'entity', 'Zaposleni'];

    function ZaposleniDeleteController($uibModalInstance, entity, Zaposleni) {
        var vm = this;

        vm.zaposleni = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Zaposleni.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
