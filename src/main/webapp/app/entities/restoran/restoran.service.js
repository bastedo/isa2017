(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Restoran', Restoran);

    Restoran.$inject = ['$resource'];

    function Restoran ($resource) {
        var resourceUrl =  'api/restorans/:id';

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
