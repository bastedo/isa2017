(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('StolController', StolController);

    StolController.$inject = ['Stol'];

    function StolController(Stol) {

        var vm = this;

        vm.stols = [];

        loadAll();

        function loadAll() {
            Stol.query(function(result) {
                vm.stols = result;
                vm.searchQuery = null;
            });
        }
    }
})();
