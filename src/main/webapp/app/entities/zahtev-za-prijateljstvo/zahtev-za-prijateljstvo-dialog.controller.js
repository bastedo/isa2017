(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('ZahtevZaPrijateljstvoDialogController', ZahtevZaPrijateljstvoDialogController);

    ZahtevZaPrijateljstvoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ZahtevZaPrijateljstvo', 'Gost'];

    function ZahtevZaPrijateljstvoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ZahtevZaPrijateljstvo, Gost) {
        var vm = this;

        vm.zahtevZaPrijateljstvo = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.gosts = Gost.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.zahtevZaPrijateljstvo.id !== null) {
                ZahtevZaPrijateljstvo.update(vm.zahtevZaPrijateljstvo, onSaveSuccess, onSaveError);
            } else {
                ZahtevZaPrijateljstvo.save(vm.zahtevZaPrijateljstvo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:zahtevZaPrijateljstvoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.postalanZahtev = false;
        vm.datePickerOpenStatus.prihvacenZahtev = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
