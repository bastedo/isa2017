(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('OcenaController', OcenaController);

    OcenaController.$inject = ['Ocena'];

    function OcenaController(Ocena) {

        var vm = this;

        vm.ocenas = [];

        loadAll();

        function loadAll() {
            Ocena.query(function(result) {
                vm.ocenas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
