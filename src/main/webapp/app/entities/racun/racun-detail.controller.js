(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RacunDetailController', RacunDetailController);

    RacunDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Racun', 'Zaposleni', 'Stol'];

    function RacunDetailController($scope, $rootScope, $stateParams, previousState, entity, Racun, Zaposleni, Stol) {
        var vm = this;

        vm.racun = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:racunUpdate', function(event, result) {
            vm.racun = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
