(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('MenadzerRestorana', MenadzerRestorana);

    MenadzerRestorana.$inject = ['$resource'];

    function MenadzerRestorana ($resource) {
        var resourceUrl =  'api/menadzer-restoranas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
