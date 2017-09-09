(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RezervacijaDialogController', RezervacijaDialogController);

    RezervacijaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Rezervacija', 'Stol', 'Porudzbina', 'Restoran', 'Gost'];

    function RezervacijaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Rezervacija, Stol, Porudzbina, Restoran, Gost) {
        var vm = this;

        vm.rezervacija = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.stols = Stol.query();
        vm.porudzbinas = Porudzbina.query({filter: 'zarezervaciju-is-null'});
        $q.all([vm.rezervacija.$promise, vm.porudzbinas.$promise]).then(function() {
            if (!vm.rezervacija.porudzbina || !vm.rezervacija.porudzbina.id) {
                return $q.reject();
            }
            return Porudzbina.get({id : vm.rezervacija.porudzbina.id}).$promise;
        }).then(function(porudzbina) {
            vm.porudzbinas.push(porudzbina);
        });
        vm.restorans = Restoran.query();
        vm.gosts = Gost.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rezervacija.id !== null) {
                Rezervacija.update(vm.rezervacija, onSaveSuccess, onSaveError);
            } else {
                Rezervacija.save(vm.rezervacija, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:rezervacijaUpdate', result);
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
