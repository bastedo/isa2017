(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('KonfiguracijaStolovaController', KonfiguracijaStolovaController);

    KonfiguracijaStolovaController.$inject = ['KonfiguracijaStolova'];

    function KonfiguracijaStolovaController(KonfiguracijaStolova) {

        var vm = this;

        vm.konfiguracijaStolova = [];

        loadAll();

        function loadAll() {
            KonfiguracijaStolova.query(function(result) {
                vm.konfiguracijaStolova = result;
                vm.searchQuery = null;
            });
        }
    }
})();
