(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('KonfiguracijaStolovaDeleteController',KonfiguracijaStolovaDeleteController);

    KonfiguracijaStolovaDeleteController.$inject = ['$uibModalInstance', 'entity', 'KonfiguracijaStolova'];

    function KonfiguracijaStolovaDeleteController($uibModalInstance, entity, KonfiguracijaStolova) {
        var vm = this;

        vm.konfiguracijaStolova = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            KonfiguracijaStolova.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
