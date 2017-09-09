(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('stol', {
            parent: 'entity',
            url: '/stol',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Stols'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stol/stols.html',
                    controller: 'StolController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('stol-detail', {
            parent: 'stol',
            url: '/stol/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Stol'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stol/stol-detail.html',
                    controller: 'StolDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Stol', function($stateParams, Stol) {
                    return Stol.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'stol',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('stol-detail.edit', {
            parent: 'stol-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stol/stol-dialog.html',
                    controller: 'StolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Stol', function(Stol) {
                            return Stol.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stol.new', {
            parent: 'stol',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stol/stol-dialog.html',
                    controller: 'StolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                xpozicijaStola: null,
                                ypozicijaStola: null,
                                pripadaSegmentu: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('stol', null, { reload: 'stol' });
                }, function() {
                    $state.go('stol');
                });
            }]
        })
        .state('stol.edit', {
            parent: 'stol',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stol/stol-dialog.html',
                    controller: 'StolDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Stol', function(Stol) {
                            return Stol.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stol', null, { reload: 'stol' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stol.delete', {
            parent: 'stol',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stol/stol-delete-dialog.html',
                    controller: 'StolDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Stol', function(Stol) {
                            return Stol.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stol', null, { reload: 'stol' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
