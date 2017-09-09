(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PiceDialogController', PiceDialogController);

    PiceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Pice', 'KartaPica', 'Porudzbina'];

    function PiceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Pice, KartaPica, Porudzbina) {
        var vm = this;

        vm.pice = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.kartapicas = KartaPica.query();
        vm.porudzbinas = Porudzbina.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pice.id !== null) {
                Pice.update(vm.pice, onSaveSuccess, onSaveError);
            } else {
                Pice.save(vm.pice, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:piceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
