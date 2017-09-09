(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('KartaPicaDialogController', KartaPicaDialogController);

    KartaPicaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'KartaPica', 'Pice'];

    function KartaPicaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, KartaPica, Pice) {
        var vm = this;

        vm.kartaPica = entity;
        vm.clear = clear;
        vm.save = save;
        vm.pices = Pice.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.kartaPica.id !== null) {
                KartaPica.update(vm.kartaPica, onSaveSuccess, onSaveError);
            } else {
                KartaPica.save(vm.kartaPica, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:kartaPicaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
