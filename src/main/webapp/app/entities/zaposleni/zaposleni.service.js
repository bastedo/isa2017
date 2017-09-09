(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('Zaposleni', Zaposleni);

    Zaposleni.$inject = ['$resource', 'DateUtils'];

    function Zaposleni ($resource, DateUtils) {
        var resourceUrl =  'api/zaposlenis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datumRodjenja = DateUtils.convertLocalDateFromServer(data.datumRodjenja);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datumRodjenja = DateUtils.convertLocalDateToServer(copy.datumRodjenja);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datumRodjenja = DateUtils.convertLocalDateToServer(copy.datumRodjenja);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
