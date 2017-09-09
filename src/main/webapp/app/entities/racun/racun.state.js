(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('racun', {
            parent: 'entity',
            url: '/racun',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Racuns'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/racun/racuns.html',
                    controller: 'RacunController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('racun-detail', {
            parent: 'racun',
            url: '/racun/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Racun'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/racun/racun-detail.html',
                    controller: 'RacunDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Racun', function($stateParams, Racun) {
                    return Racun.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'racun',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('racun-detail.edit', {
            parent: 'racun-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/racun/racun-dialog.html',
                    controller: 'RacunDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Racun', function(Racun) {
                            return Racun.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('racun.new', {
            parent: 'racun',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/racun/racun-dialog.html',
                    controller: 'RacunDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                cena: null,
                                datum: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('racun', null, { reload: 'racun' });
                }, function() {
                    $state.go('racun');
                });
            }]
        })
        .state('racun.edit', {
            parent: 'racun',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/racun/racun-dialog.html',
                    controller: 'RacunDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Racun', function(Racun) {
                            return Racun.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('racun', null, { reload: 'racun' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('racun.delete', {
            parent: 'racun',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/racun/racun-delete-dialog.html',
                    controller: 'RacunDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Racun', function(Racun) {
                            return Racun.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('racun', null, { reload: 'racun' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
