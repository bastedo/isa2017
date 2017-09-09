(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Stol', Stol);

    Stol.$inject = ['$resource'];

    function Stol ($resource) {
        var resourceUrl =  'api/stols/:id';

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
