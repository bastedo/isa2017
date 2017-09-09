(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gost', {
            parent: 'entity',
            url: '/gost',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gosts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gost/gosts.html',
                    controller: 'GostController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('gost-detail', {
            parent: 'gost',
            url: '/gost/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Gost'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gost/gost-detail.html',
                    controller: 'GostDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Gost', function($stateParams, Gost) {
                    return Gost.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gost',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gost-detail.edit', {
            parent: 'gost-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gost/gost-dialog.html',
                    controller: 'GostDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gost', function(Gost) {
                            return Gost.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gost.new', {
            parent: 'gost',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gost/gost-dialog.html',
                    controller: 'GostDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ime: null,
                                prezime: null,
                                email: null,
                                lozinka: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gost', null, { reload: 'gost' });
                }, function() {
                    $state.go('gost');
                });
            }]
        })
        .state('gost.edit', {
            parent: 'gost',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gost/gost-dialog.html',
                    controller: 'GostDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gost', function(Gost) {
                            return Gost.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gost', null, { reload: 'gost' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gost.delete', {
            parent: 'gost',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gost/gost-delete-dialog.html',
                    controller: 'GostDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gost', function(Gost) {
                            return Gost.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gost', null, { reload: 'gost' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
