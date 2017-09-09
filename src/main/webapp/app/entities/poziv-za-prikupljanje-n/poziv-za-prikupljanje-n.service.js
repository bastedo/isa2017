(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('PozivZaPrikupljanjeN', PozivZaPrikupljanjeN);

    PozivZaPrikupljanjeN.$inject = ['$resource', 'DateUtils'];

    function PozivZaPrikupljanjeN ($resource, DateUtils) {
        var resourceUrl =  'api/poziv-za-prikupljanje-ns/:id';

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
