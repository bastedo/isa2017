(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PiceController', PiceController);

    PiceController.$inject = ['DataUtils', 'Pice'];

    function PiceController(DataUtils, Pice) {

        var vm = this;

        vm.pices = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Pice.query(function(result) {
                vm.pices = result;
                vm.searchQuery = null;
            });
        }
    }
})();
