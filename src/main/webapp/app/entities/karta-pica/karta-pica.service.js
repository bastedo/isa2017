(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('KartaPica', KartaPica);

    KartaPica.$inject = ['$resource'];

    function KartaPica ($resource) {
        var resourceUrl =  'api/karta-picas/:id';

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
