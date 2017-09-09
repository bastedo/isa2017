(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('KonfiguracijaStolovaDialogController', KonfiguracijaStolovaDialogController);

    KonfiguracijaStolovaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KonfiguracijaStolova', 'MenadzerRestorana', 'Stol'];

    function KonfiguracijaStolovaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KonfiguracijaStolova, MenadzerRestorana, Stol) {
        var vm = this;

        vm.konfiguracijaStolova = entity;
        vm.clear = clear;
        vm.save = save;
        vm.menadzerrestoranas = MenadzerRestorana.query();
        vm.stols = Stol.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.konfiguracijaStolova.id !== null) {
                KonfiguracijaStolova.update(vm.konfiguracijaStolova, onSaveSuccess, onSaveError);
            } else {
                KonfiguracijaStolova.save(vm.konfiguracijaStolova, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:konfiguracijaStolovaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
