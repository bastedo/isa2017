(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('ZahtevZaPrijateljstvoDeleteController',ZahtevZaPrijateljstvoDeleteController);

    ZahtevZaPrijateljstvoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ZahtevZaPrijateljstvo'];

    function ZahtevZaPrijateljstvoDeleteController($uibModalInstance, entity, ZahtevZaPrijateljstvo) {
        var vm = this;

        vm.zahtevZaPrijateljstvo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ZahtevZaPrijateljstvo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
