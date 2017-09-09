(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaKuvareDialogController', RasporedSmenaZaKuvareDialogController);

    RasporedSmenaZaKuvareDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RasporedSmenaZaKuvare', 'Zaposleni', 'Restoran'];

    function RasporedSmenaZaKuvareDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RasporedSmenaZaKuvare, Zaposleni, Restoran) {
        var vm = this;

        vm.rasporedSmenaZaKuvare = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.zaposlenis = Zaposleni.query();
        vm.restorans = Restoran.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rasporedSmenaZaKuvare.id !== null) {
                RasporedSmenaZaKuvare.update(vm.rasporedSmenaZaKuvare, onSaveSuccess, onSaveError);
            } else {
                RasporedSmenaZaKuvare.save(vm.rasporedSmenaZaKuvare, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:rasporedSmenaZaKuvareUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.start = false;
        vm.datePickerOpenStatus.end = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
