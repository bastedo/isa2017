(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('RasporedSmenaZaKuvare', RasporedSmenaZaKuvare);

    RasporedSmenaZaKuvare.$inject = ['$resource', 'DateUtils'];

    function RasporedSmenaZaKuvare ($resource, DateUtils) {
        var resourceUrl =  'api/raspored-smena-za-kuvares/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.start = DateUtils.convertDateTimeFromServer(data.start);
                        data.end = DateUtils.convertDateTimeFromServer(data.end);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
