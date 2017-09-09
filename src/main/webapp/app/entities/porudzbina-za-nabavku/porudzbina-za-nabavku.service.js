(function() {
    'use strict';
    angular
        .module('isa2017App')
        .factory('PorudzbinaZANabavku', PorudzbinaZANabavku);

    PorudzbinaZANabavku.$inject = ['$resource', 'DateUtils'];

    function PorudzbinaZANabavku ($resource, DateUtils) {
        var resourceUrl =  'api/porudzbina-za-nabavkus/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dostava = DateUtils.convertDateTimeFromServer(data.dostava);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
