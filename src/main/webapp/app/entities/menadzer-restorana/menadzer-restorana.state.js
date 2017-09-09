(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('menadzer-restorana', {
            parent: 'entity',
            url: '/menadzer-restorana',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MenadzerRestoranas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/menadzer-restorana/menadzer-restoranas.html',
                    controller: 'MenadzerRestoranaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('menadzer-restorana-detail', {
            parent: 'menadzer-restorana',
            url: '/menadzer-restorana/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MenadzerRestorana'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/menadzer-restorana/menadzer-restorana-detail.html',
                    controller: 'MenadzerRestoranaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MenadzerRestorana', function($stateParams, MenadzerRestorana) {
                    return MenadzerRestorana.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'menadzer-restorana',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('menadzer-restorana-detail.edit', {
            parent: 'menadzer-restorana-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-restorana/menadzer-restorana-dialog.html',
                    controller: 'MenadzerRestoranaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MenadzerRestorana', function(MenadzerRestorana) {
                            return MenadzerRestorana.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('menadzer-restorana.new', {
            parent: 'menadzer-restorana',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-restorana/menadzer-restorana-dialog.html',
                    controller: 'MenadzerRestoranaDialogController',
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
                    $state.go('menadzer-restorana', null, { reload: 'menadzer-restorana' });
                }, function() {
                    $state.go('menadzer-restorana');
                });
            }]
        })
        .state('menadzer-restorana.edit', {
            parent: 'menadzer-restorana',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-restorana/menadzer-restorana-dialog.html',
                    controller: 'MenadzerRestoranaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MenadzerRestorana', function(MenadzerRestorana) {
                            return MenadzerRestorana.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('menadzer-restorana', null, { reload: 'menadzer-restorana' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('menadzer-restorana.delete', {
            parent: 'menadzer-restorana',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/menadzer-restorana/menadzer-restorana-delete-dialog.html',
                    controller: 'MenadzerRestoranaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MenadzerRestorana', function(MenadzerRestorana) {
                            return MenadzerRestorana.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('menadzer-restorana', null, { reload: 'menadzer-restorana' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
