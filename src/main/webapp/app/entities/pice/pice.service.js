(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Pice', Pice);

    Pice.$inject = ['$resource'];

    function Pice ($resource) {
        var resourceUrl =  'api/pices/:id';

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
