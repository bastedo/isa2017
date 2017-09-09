(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('ZahtevZaPrijateljstvoController', ZahtevZaPrijateljstvoController);

    ZahtevZaPrijateljstvoController.$inject = ['ZahtevZaPrijateljstvo'];

    function ZahtevZaPrijateljstvoController(ZahtevZaPrijateljstvo) {

        var vm = this;

        vm.zahtevZaPrijateljstvos = [];

        loadAll();

        function loadAll() {
            ZahtevZaPrijateljstvo.query(function(result) {
                vm.zahtevZaPrijateljstvos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
