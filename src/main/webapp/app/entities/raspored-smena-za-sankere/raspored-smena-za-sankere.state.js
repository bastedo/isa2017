(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('raspored-smena-za-sankere', {
            parent: 'entity',
            url: '/raspored-smena-za-sankere',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RasporedSmenaZaSankeres'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/raspored-smena-za-sankere/raspored-smena-za-sankeres.html',
                    controller: 'RasporedSmenaZaSankereController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('raspored-smena-za-sankere-detail', {
            parent: 'raspored-smena-za-sankere',
            url: '/raspored-smena-za-sankere/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RasporedSmenaZaSankere'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/raspored-smena-za-sankere/raspored-smena-za-sankere-detail.html',
                    controller: 'RasporedSmenaZaSankereDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RasporedSmenaZaSankere', function($stateParams, RasporedSmenaZaSankere) {
                    return RasporedSmenaZaSankere.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'raspored-smena-za-sankere',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('raspored-smena-za-sankere-detail.edit', {
            parent: 'raspored-smena-za-sankere-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-sankere/raspored-smena-za-sankere-dialog.html',
                    controller: 'RasporedSmenaZaSankereDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RasporedSmenaZaSankere', function(RasporedSmenaZaSankere) {
                            return RasporedSmenaZaSankere.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('raspored-smena-za-sankere.new', {
            parent: 'raspored-smena-za-sankere',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-sankere/raspored-smena-za-sankere-dialog.html',
                    controller: 'RasporedSmenaZaSankereDialogController',
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
                    $state.go('raspored-smena-za-sankere', null, { reload: 'raspored-smena-za-sankere' });
                }, function() {
                    $state.go('raspored-smena-za-sankere');
                });
            }]
        })
        .state('raspored-smena-za-sankere.edit', {
            parent: 'raspored-smena-za-sankere',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-sankere/raspored-smena-za-sankere-dialog.html',
                    controller: 'RasporedSmenaZaSankereDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RasporedSmenaZaSankere', function(RasporedSmenaZaSankere) {
                            return RasporedSmenaZaSankere.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-sankere', null, { reload: 'raspored-smena-za-sankere' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('raspored-smena-za-sankere.delete', {
            parent: 'raspored-smena-za-sankere',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-sankere/raspored-smena-za-sankere-delete-dialog.html',
                    controller: 'RasporedSmenaZaSankereDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RasporedSmenaZaSankere', function(RasporedSmenaZaSankere) {
                            return RasporedSmenaZaSankere.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-sankere', null, { reload: 'raspored-smena-za-sankere' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
