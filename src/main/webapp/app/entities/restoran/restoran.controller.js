(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RestoranController', RestoranController);

    RestoranController.$inject = ['Restoran'];

    function RestoranController(Restoran) {

        var vm = this;

        vm.restorans = [];

        loadAll();

        function loadAll() {
            Restoran.query(function(result) {
                vm.restorans = result;
                vm.searchQuery = null;
            });
        }
    }
})();
