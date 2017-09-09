(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('KartaPicaDetailController', KartaPicaDetailController);

    KartaPicaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'KartaPica', 'Pice'];

    function KartaPicaDetailController($scope, $rootScope, $stateParams, previousState, entity, KartaPica, Pice) {
        var vm = this;

        vm.kartaPica = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:kartaPicaUpdate', function(event, result) {
            vm.kartaPica = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
