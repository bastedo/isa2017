(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PozivZaPrikupljanjeNDialogController', PozivZaPrikupljanjeNDialogController);

    PozivZaPrikupljanjeNDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'PozivZaPrikupljanjeN', 'Restoran', 'PorudzbinaZANabavku'];

    function PozivZaPrikupljanjeNDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, PozivZaPrikupljanjeN, Restoran, PorudzbinaZANabavku) {
        var vm = this;

        vm.pozivZaPrikupljanjeN = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.restorans = Restoran.query();
        vm.porudzbinazanabavkus = PorudzbinaZANabavku.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pozivZaPrikupljanjeN.id !== null) {
                PozivZaPrikupljanjeN.update(vm.pozivZaPrikupljanjeN, onSaveSuccess, onSaveError);
            } else {
                PozivZaPrikupljanjeN.save(vm.pozivZaPrikupljanjeN, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:pozivZaPrikupljanjeNUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
