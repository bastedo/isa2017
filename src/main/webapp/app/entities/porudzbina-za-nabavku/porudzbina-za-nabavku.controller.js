(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('PorudzbinaZANabavkuController', PorudzbinaZANabavkuController);

    PorudzbinaZANabavkuController.$inject = ['DataUtils', 'PorudzbinaZANabavku'];

    function PorudzbinaZANabavkuController(DataUtils, PorudzbinaZANabavku) {

        var vm = this;

        vm.porudzbinaZANabavkus = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            PorudzbinaZANabavku.query(function(result) {
                vm.porudzbinaZANabavkus = result;
                vm.searchQuery = null;
            });
        }
    }
})();
