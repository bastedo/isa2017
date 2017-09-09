(function() {
    'use strict';

    angular
        .module('isa2017App')
        .factory('LogsService', LogsService);

    LogsService.$inject = ['$resource'];

    function LogsService ($resource) {
        var service = $resource('management/logs', {}, {
            'findAll': { method: 'GET', isArray: true},
            'changeLevel': { method: 'PUT'}
        });

        return service;
    }
})();
