(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('StolDetailController', StolDetailController);

    StolDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stol', 'KonfiguracijaStolova', 'Racun', 'Rezervacija'];

    function StolDetailController($scope, $rootScope, $stateParams, previousState, entity, Stol, KonfiguracijaStolova, Racun, Rezervacija) {
        var vm = this;

        vm.stol = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:stolUpdate', function(event, result) {
            vm.stol = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
