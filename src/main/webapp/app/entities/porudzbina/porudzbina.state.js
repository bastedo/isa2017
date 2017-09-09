(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('porudzbina', {
            parent: 'entity',
            url: '/porudzbina',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Porudzbinas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/porudzbina/porudzbinas.html',
                    controller: 'PorudzbinaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('porudzbina-detail', {
            parent: 'porudzbina',
            url: '/porudzbina/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Porudzbina'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/porudzbina/porudzbina-detail.html',
                    controller: 'PorudzbinaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Porudzbina', function($stateParams, Porudzbina) {
                    return Porudzbina.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'porudzbina',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('porudzbina-detail.edit', {
            parent: 'porudzbina-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina/porudzbina-dialog.html',
                    controller: 'PorudzbinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Porudzbina', function(Porudzbina) {
                            return Porudzbina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('porudzbina.new', {
            parent: 'porudzbina',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina/porudzbina-dialog.html',
                    controller: 'PorudzbinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                prihvacenoPice: null,
                                spremnoPice: null,
                                prihvacenaHrana: null,
                                spremnaHrana: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('porudzbina', null, { reload: 'porudzbina' });
                }, function() {
                    $state.go('porudzbina');
                });
            }]
        })
        .state('porudzbina.edit', {
            parent: 'porudzbina',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina/porudzbina-dialog.html',
                    controller: 'PorudzbinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Porudzbina', function(Porudzbina) {
                            return Porudzbina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('porudzbina', null, { reload: 'porudzbina' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('porudzbina.delete', {
            parent: 'porudzbina',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina/porudzbina-delete-dialog.html',
                    controller: 'PorudzbinaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Porudzbina', function(Porudzbina) {
                            return Porudzbina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('porudzbina', null, { reload: 'porudzbina' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
