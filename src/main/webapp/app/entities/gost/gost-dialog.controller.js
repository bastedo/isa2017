(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('GostDialogController', GostDialogController);

    GostDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Gost', 'Rezervacija', 'ZahtevZaPrijateljstvo'];

    function GostDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Gost, Rezervacija, ZahtevZaPrijateljstvo) {
        var vm = this;

        vm.gost = entity;
        vm.clear = clear;
        vm.save = save;
        vm.rezervacijas = Rezervacija.query();
        vm.zahtevzaprijateljstvos = ZahtevZaPrijateljstvo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gost.id !== null) {
                Gost.update(vm.gost, onSaveSuccess, onSaveError);
            } else {
                Gost.save(vm.gost, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:gostUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
