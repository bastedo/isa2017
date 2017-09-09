(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('KartaPicaController', KartaPicaController);

    KartaPicaController.$inject = ['KartaPica'];

    function KartaPicaController(KartaPica) {

        var vm = this;

        vm.kartaPicas = [];

        loadAll();

        function loadAll() {
            KartaPica.query(function(result) {
                vm.kartaPicas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
