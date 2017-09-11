(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('MenadzerRestoranaDialogController', MenadzerRestoranaDialogController);

    MenadzerRestoranaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'MenadzerRestorana', 'User', 'KonfiguracijaStolova', 'Restoran'];

    function MenadzerRestoranaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, MenadzerRestorana, User, KonfiguracijaStolova, Restoran) {
        var vm = this;

        vm.menadzerRestorana = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.konfiguracijastolova = KonfiguracijaStolova.query();
        vm.restorans = Restoran.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.menadzerRestorana.id !== null) {
                MenadzerRestorana.update(vm.menadzerRestorana, onSaveSuccess, onSaveError);
            } else {
                MenadzerRestorana.save(vm.menadzerRestorana, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:menadzerRestoranaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
