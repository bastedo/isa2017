(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaKuvareController', RasporedSmenaZaKuvareController);

    RasporedSmenaZaKuvareController.$inject = ['RasporedSmenaZaKuvare'];

    function RasporedSmenaZaKuvareController(RasporedSmenaZaKuvare) {

        var vm = this;

        vm.rasporedSmenaZaKuvares = [];

        loadAll();

        function loadAll() {
            RasporedSmenaZaKuvare.query(function(result) {
                vm.rasporedSmenaZaKuvares = result;
                vm.searchQuery = null;
            });
        }
    }
})();
