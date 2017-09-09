(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Rezervacija', Rezervacija);

    Rezervacija.$inject = ['$resource', 'DateUtils'];

    function Rezervacija ($resource, DateUtils) {
        var resourceUrl =  'api/rezervacijas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
