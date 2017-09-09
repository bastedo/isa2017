(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Racun', Racun);

    Racun.$inject = ['$resource', 'DateUtils'];

    function Racun ($resource, DateUtils) {
        var resourceUrl =  'api/racuns/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datum = DateUtils.convertDateTimeFromServer(data.datum);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
