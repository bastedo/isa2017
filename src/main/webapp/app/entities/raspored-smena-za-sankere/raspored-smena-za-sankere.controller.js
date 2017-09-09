(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaSankereController', RasporedSmenaZaSankereController);

    RasporedSmenaZaSankereController.$inject = ['RasporedSmenaZaSankere'];

    function RasporedSmenaZaSankereController(RasporedSmenaZaSankere) {

        var vm = this;

        vm.rasporedSmenaZaSankeres = [];

        loadAll();

        function loadAll() {
            RasporedSmenaZaSankere.query(function(result) {
                vm.rasporedSmenaZaSankeres = result;
                vm.searchQuery = null;
            });
        }
    }
})();
