(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('GostController', GostController);

    GostController.$inject = ['Gost'];

    function GostController(Gost) {

        var vm = this;

        vm.gosts = [];

        loadAll();

        function loadAll() {
            Gost.query(function(result) {
                vm.gosts = result;
                vm.searchQuery = null;
            });
        }
    }
})();
