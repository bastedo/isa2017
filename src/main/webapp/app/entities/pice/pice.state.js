(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pice', {
            parent: 'entity',
            url: '/pice',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pices'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pice/pices.html',
                    controller: 'PiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('pice-detail', {
            parent: 'pice',
            url: '/pice/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Pice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pice/pice-detail.html',
                    controller: 'PiceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Pice', function($stateParams, Pice) {
                    return Pice.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pice',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pice-detail.edit', {
            parent: 'pice-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pice/pice-dialog.html',
                    controller: 'PiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pice', function(Pice) {
                            return Pice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pice.new', {
            parent: 'pice',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pice/pice-dialog.html',
                    controller: 'PiceDialogController',
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
                    $state.go('pice', null, { reload: 'pice' });
                }, function() {
                    $state.go('pice');
                });
            }]
        })
        .state('pice.edit', {
            parent: 'pice',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pice/pice-dialog.html',
                    controller: 'PiceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Pice', function(Pice) {
                            return Pice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pice', null, { reload: 'pice' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pice.delete', {
            parent: 'pice',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pice/pice-delete-dialog.html',
                    controller: 'PiceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Pice', function(Pice) {
                            return Pice.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pice', null, { reload: 'pice' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
