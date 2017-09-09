(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Ocena', Ocena);

    Ocena.$inject = ['$resource'];

    function Ocena ($resource) {
        var resourceUrl =  'api/ocenas/:id';

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
