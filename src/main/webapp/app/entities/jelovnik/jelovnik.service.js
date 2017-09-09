(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Jelovnik', Jelovnik);

    Jelovnik.$inject = ['$resource'];

    function Jelovnik ($resource) {
        var resourceUrl =  'api/jelovniks/:id';

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
