(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PorudzbinaZANabavkuDialogController', PorudzbinaZANabavkuDialogController);

    PorudzbinaZANabavkuDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'PorudzbinaZANabavku', 'PozivZaPrikupljanjeN'];

    function PorudzbinaZANabavkuDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, PorudzbinaZANabavku, PozivZaPrikupljanjeN) {
        var vm = this;

        vm.porudzbinaZANabavku = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.pozivzaprikupljanjens = PozivZaPrikupljanjeN.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.porudzbinaZANabavku.id !== null) {
                PorudzbinaZANabavku.update(vm.porudzbinaZANabavku, onSaveSuccess, onSaveError);
            } else {
                PorudzbinaZANabavku.save(vm.porudzbinaZANabavku, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:porudzbinaZANabavkuUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dostava = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
