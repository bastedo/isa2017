(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Jelo', Jelo);

    Jelo.$inject = ['$resource'];

    function Jelo ($resource) {
        var resourceUrl =  'api/jelos/:id';

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
