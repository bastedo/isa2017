(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaKonobareDialogController', RasporedSmenaZaKonobareDialogController);

    RasporedSmenaZaKonobareDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RasporedSmenaZaKonobare', 'Zaposleni', 'Restoran'];

    function RasporedSmenaZaKonobareDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RasporedSmenaZaKonobare, Zaposleni, Restoran) {
        var vm = this;

        vm.rasporedSmenaZaKonobare = entity;
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
            if (vm.rasporedSmenaZaKonobare.id !== null) {
                RasporedSmenaZaKonobare.update(vm.rasporedSmenaZaKonobare, onSaveSuccess, onSaveError);
            } else {
                RasporedSmenaZaKonobare.save(vm.rasporedSmenaZaKonobare, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:rasporedSmenaZaKonobareUpdate', result);
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
