(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('KonfiguracijaStolova', KonfiguracijaStolova);

    KonfiguracijaStolova.$inject = ['$resource'];

    function KonfiguracijaStolova ($resource) {
        var resourceUrl =  'api/konfiguracija-stolova/:id';

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
