(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PorudzbinaDialogController', PorudzbinaDialogController);

    PorudzbinaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Porudzbina', 'Jelo', 'Pice', 'Rezervacija'];

    function PorudzbinaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Porudzbina, Jelo, Pice, Rezervacija) {
        var vm = this;

        vm.porudzbina = entity;
        vm.clear = clear;
        vm.save = save;
        vm.jelos = Jelo.query();
        vm.pices = Pice.query();
        vm.rezervacijas = Rezervacija.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.porudzbina.id !== null) {
                Porudzbina.update(vm.porudzbina, onSaveSuccess, onSaveError);
            } else {
                Porudzbina.save(vm.porudzbina, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:porudzbinaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
