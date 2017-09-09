(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('StolDialogController', StolDialogController);

    StolDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Stol', 'KonfiguracijaStolova', 'Racun', 'Rezervacija'];

    function StolDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Stol, KonfiguracijaStolova, Racun, Rezervacija) {
        var vm = this;

        vm.stol = entity;
        vm.clear = clear;
        vm.save = save;
        vm.konfiguracijastolova = KonfiguracijaStolova.query();
        vm.racuns = Racun.query();
        vm.rezervacijas = Rezervacija.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stol.id !== null) {
                Stol.update(vm.stol, onSaveSuccess, onSaveError);
            } else {
                Stol.save(vm.stol, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:stolUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
