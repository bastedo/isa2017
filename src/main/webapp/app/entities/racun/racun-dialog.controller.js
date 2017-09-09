(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RacunDialogController', RacunDialogController);

    RacunDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Racun', 'Zaposleni', 'Stol'];

    function RacunDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Racun, Zaposleni, Stol) {
        var vm = this;

        vm.racun = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.zaposlenis = Zaposleni.query();
        vm.stols = Stol.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.racun.id !== null) {
                Racun.update(vm.racun, onSaveSuccess, onSaveError);
            } else {
                Racun.save(vm.racun, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:racunUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datum = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
