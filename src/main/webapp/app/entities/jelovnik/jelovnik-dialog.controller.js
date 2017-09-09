(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('JelovnikDialogController', JelovnikDialogController);

    JelovnikDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Jelovnik', 'Jelo'];

    function JelovnikDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Jelovnik, Jelo) {
        var vm = this;

        vm.jelovnik = entity;
        vm.clear = clear;
        vm.save = save;
        vm.jelos = Jelo.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jelovnik.id !== null) {
                Jelovnik.update(vm.jelovnik, onSaveSuccess, onSaveError);
            } else {
                Jelovnik.save(vm.jelovnik, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:jelovnikUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
