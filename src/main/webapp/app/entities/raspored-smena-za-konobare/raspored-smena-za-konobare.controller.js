(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RasporedSmenaZaKonobareController', RasporedSmenaZaKonobareController);

    RasporedSmenaZaKonobareController.$inject = ['RasporedSmenaZaKonobare'];

    function RasporedSmenaZaKonobareController(RasporedSmenaZaKonobare) {

        var vm = this;

        vm.rasporedSmenaZaKonobares = [];

        loadAll();

        function loadAll() {
            RasporedSmenaZaKonobare.query(function(result) {
                vm.rasporedSmenaZaKonobares = result;
                vm.searchQuery = null;
            });
        }
    }
})();
