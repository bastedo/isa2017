(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('raspored-smena-za-konobare', {
            parent: 'entity',
            url: '/raspored-smena-za-konobare',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RasporedSmenaZaKonobares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/raspored-smena-za-konobare/raspored-smena-za-konobares.html',
                    controller: 'RasporedSmenaZaKonobareController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('raspored-smena-za-konobare-detail', {
            parent: 'raspored-smena-za-konobare',
            url: '/raspored-smena-za-konobare/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RasporedSmenaZaKonobare'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/raspored-smena-za-konobare/raspored-smena-za-konobare-detail.html',
                    controller: 'RasporedSmenaZaKonobareDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RasporedSmenaZaKonobare', function($stateParams, RasporedSmenaZaKonobare) {
                    return RasporedSmenaZaKonobare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'raspored-smena-za-konobare',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('raspored-smena-za-konobare-detail.edit', {
            parent: 'raspored-smena-za-konobare-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-konobare/raspored-smena-za-konobare-dialog.html',
                    controller: 'RasporedSmenaZaKonobareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RasporedSmenaZaKonobare', function(RasporedSmenaZaKonobare) {
                            return RasporedSmenaZaKonobare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('raspored-smena-za-konobare.new', {
            parent: 'raspored-smena-za-konobare',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-konobare/raspored-smena-za-konobare-dialog.html',
                    controller: 'RasporedSmenaZaKonobareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                vrstaSmene: null,
                                start: null,
                                end: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-konobare', null, { reload: 'raspored-smena-za-konobare' });
                }, function() {
                    $state.go('raspored-smena-za-konobare');
                });
            }]
        })
        .state('raspored-smena-za-konobare.edit', {
            parent: 'raspored-smena-za-konobare',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-konobare/raspored-smena-za-konobare-dialog.html',
                    controller: 'RasporedSmenaZaKonobareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RasporedSmenaZaKonobare', function(RasporedSmenaZaKonobare) {
                            return RasporedSmenaZaKonobare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-konobare', null, { reload: 'raspored-smena-za-konobare' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('raspored-smena-za-konobare.delete', {
            parent: 'raspored-smena-za-konobare',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-konobare/raspored-smena-za-konobare-delete-dialog.html',
                    controller: 'RasporedSmenaZaKonobareDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RasporedSmenaZaKonobare', function(RasporedSmenaZaKonobare) {
                            return RasporedSmenaZaKonobare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-konobare', null, { reload: 'raspored-smena-za-konobare' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
