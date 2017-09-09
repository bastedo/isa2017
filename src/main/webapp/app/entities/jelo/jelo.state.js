(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('jelo', {
            parent: 'entity',
            url: '/jelo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Jelos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jelo/jelos.html',
                    controller: 'JeloController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('jelo-detail', {
            parent: 'jelo',
            url: '/jelo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Jelo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jelo/jelo-detail.html',
                    controller: 'JeloDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Jelo', function($stateParams, Jelo) {
                    return Jelo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'jelo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('jelo-detail.edit', {
            parent: 'jelo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelo/jelo-dialog.html',
                    controller: 'JeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jelo', function(Jelo) {
                            return Jelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jelo.new', {
            parent: 'jelo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelo/jelo-dialog.html',
                    controller: 'JeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imeJela: null,
                                kratkiTekst: null,
                                cena: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('jelo', null, { reload: 'jelo' });
                }, function() {
                    $state.go('jelo');
                });
            }]
        })
        .state('jelo.edit', {
            parent: 'jelo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelo/jelo-dialog.html',
                    controller: 'JeloDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jelo', function(Jelo) {
                            return Jelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jelo', null, { reload: 'jelo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jelo.delete', {
            parent: 'jelo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelo/jelo-delete-dialog.html',
                    controller: 'JeloDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Jelo', function(Jelo) {
                            return Jelo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jelo', null, { reload: 'jelo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
