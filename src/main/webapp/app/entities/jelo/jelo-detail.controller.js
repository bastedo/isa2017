(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('JeloDetailController', JeloDetailController);

    JeloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Jelo', 'Jelovnik', 'Porudzbina'];

    function JeloDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Jelo, Jelovnik, Porudzbina) {
        var vm = this;

        vm.jelo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('isa2017App:jeloUpdate', function(event, result) {
            vm.jelo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
