(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('KonfiguracijaStolovaDetailController', KonfiguracijaStolovaDetailController);

    KonfiguracijaStolovaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'KonfiguracijaStolova', 'MenadzerRestorana', 'Stol'];

    function KonfiguracijaStolovaDetailController($scope, $rootScope, $stateParams, previousState, entity, KonfiguracijaStolova, MenadzerRestorana, Stol) {
        var vm = this;

        vm.konfiguracijaStolova = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:konfiguracijaStolovaUpdate', function(event, result) {
            vm.konfiguracijaStolova = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
