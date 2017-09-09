(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RacunController', RacunController);

    RacunController.$inject = ['Racun'];

    function RacunController(Racun) {

        var vm = this;

        vm.racuns = [];

        loadAll();

        function loadAll() {
            Racun.query(function(result) {
                vm.racuns = result;
                vm.searchQuery = null;
            });
        }
    }
})();
