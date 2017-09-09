(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RezervacijaDeleteController',RezervacijaDeleteController);

    RezervacijaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Rezervacija'];

    function RezervacijaDeleteController($uibModalInstance, entity, Rezervacija) {
        var vm = this;

        vm.rezervacija = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Rezervacija.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
