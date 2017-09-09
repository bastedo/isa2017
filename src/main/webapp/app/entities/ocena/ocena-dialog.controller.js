(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('OcenaDialogController', OcenaDialogController);

    OcenaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Ocena', 'Gost', 'Restoran'];

    function OcenaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Ocena, Gost, Restoran) {
        var vm = this;

        vm.ocena = entity;
        vm.clear = clear;
        vm.save = save;
        vm.gosts = Gost.query({filter: 'ocena-is-null'});
        $q.all([vm.ocena.$promise, vm.gosts.$promise]).then(function() {
            if (!vm.ocena.gost || !vm.ocena.gost.id) {
                return $q.reject();
            }
            return Gost.get({id : vm.ocena.gost.id}).$promise;
        }).then(function(gost) {
            vm.gosts.push(gost);
        });
        vm.restorans = Restoran.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ocena.id !== null) {
                Ocena.update(vm.ocena, onSaveSuccess, onSaveError);
            } else {
                Ocena.save(vm.ocena, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:ocenaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
