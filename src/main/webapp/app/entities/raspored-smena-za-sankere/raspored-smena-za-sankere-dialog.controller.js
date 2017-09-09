(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaSankereDialogController', RasporedSmenaZaSankereDialogController);

    RasporedSmenaZaSankereDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RasporedSmenaZaSankere', 'Zaposleni', 'Restoran'];

    function RasporedSmenaZaSankereDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RasporedSmenaZaSankere, Zaposleni, Restoran) {
        var vm = this;

        vm.rasporedSmenaZaSankere = entity;
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
            if (vm.rasporedSmenaZaSankere.id !== null) {
                RasporedSmenaZaSankere.update(vm.rasporedSmenaZaSankere, onSaveSuccess, onSaveError);
            } else {
                RasporedSmenaZaSankere.save(vm.rasporedSmenaZaSankere, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:rasporedSmenaZaSankereUpdate', result);
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
