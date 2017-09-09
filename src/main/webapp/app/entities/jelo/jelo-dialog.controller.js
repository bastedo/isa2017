(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('JeloDialogController', JeloDialogController);

    JeloDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Jelo', 'Jelovnik', 'Porudzbina'];

    function JeloDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Jelo, Jelovnik, Porudzbina) {
        var vm = this;

        vm.jelo = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.jelovniks = Jelovnik.query();
        vm.porudzbinas = Porudzbina.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.jelo.id !== null) {
                Jelo.update(vm.jelo, onSaveSuccess, onSaveError);
            } else {
                Jelo.save(vm.jelo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:jeloUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
