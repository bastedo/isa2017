(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('MenadzerRestoranaDetailController', MenadzerRestoranaDetailController);

    MenadzerRestoranaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MenadzerRestorana', 'User', 'KonfiguracijaStolova', 'Restoran'];

    function MenadzerRestoranaDetailController($scope, $rootScope, $stateParams, previousState, entity, MenadzerRestorana, User, KonfiguracijaStolova, Restoran) {
        var vm = this;

        vm.menadzerRestorana = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('isa2017App:menadzerRestoranaUpdate', function(event, result) {
            vm.menadzerRestorana = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
