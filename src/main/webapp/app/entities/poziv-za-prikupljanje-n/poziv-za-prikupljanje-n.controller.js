(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PozivZaPrikupljanjeNController', PozivZaPrikupljanjeNController);

    PozivZaPrikupljanjeNController.$inject = ['DataUtils', 'PozivZaPrikupljanjeN'];

    function PozivZaPrikupljanjeNController(DataUtils, PozivZaPrikupljanjeN) {

        var vm = this;

        vm.pozivZaPrikupljanjeNS = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            PozivZaPrikupljanjeN.query(function(result) {
                vm.pozivZaPrikupljanjeNS = result;
                vm.searchQuery = null;
            });
        }
    }
})();
