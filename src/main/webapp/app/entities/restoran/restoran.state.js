(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('restoran', {
            parent: 'entity',
            url: '/restoran',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Restorans'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/restoran/restorans.html',
                    controller: 'RestoranController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('restoran-detail', {
            parent: 'restoran',
            url: '/restoran/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Restoran'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/restoran/restoran-detail.html',
                    controller: 'RestoranDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Restoran', function($stateParams, Restoran) {
                    return Restoran.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'restoran',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('restoran-detail.edit', {
            parent: 'restoran-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restoran/restoran-dialog.html',
                    controller: 'RestoranDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Restoran', function(Restoran) {
                            return Restoran.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('restoran.new', {
            parent: 'restoran',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restoran/restoran-dialog.html',
                    controller: 'RestoranDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                naziv: null,
                                vrsta: null,
                                ocenaRestorana: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('restoran', null, { reload: 'restoran' });
                }, function() {
                    $state.go('restoran');
                });
            }]
        })
        .state('restoran.edit', {
            parent: 'restoran',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restoran/restoran-dialog.html',
                    controller: 'RestoranDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Restoran', function(Restoran) {
                            return Restoran.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('restoran', null, { reload: 'restoran' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('restoran.delete', {
            parent: 'restoran',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/restoran/restoran-delete-dialog.html',
                    controller: 'RestoranDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Restoran', function(Restoran) {
                            return Restoran.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('restoran', null, { reload: 'restoran' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
