(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('JeloController', JeloController);

    JeloController.$inject = ['DataUtils', 'Jelo'];

    function JeloController(DataUtils, Jelo) {

        var vm = this;

        vm.jelos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Jelo.query(function(result) {
                vm.jelos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
