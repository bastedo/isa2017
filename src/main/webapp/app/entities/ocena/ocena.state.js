(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ocena', {
            parent: 'entity',
            url: '/ocena',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ocenas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ocena/ocenas.html',
                    controller: 'OcenaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('ocena-detail', {
            parent: 'ocena',
            url: '/ocena/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Ocena'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ocena/ocena-detail.html',
                    controller: 'OcenaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Ocena', function($stateParams, Ocena) {
                    return Ocena.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ocena',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ocena-detail.edit', {
            parent: 'ocena-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ocena/ocena-dialog.html',
                    controller: 'OcenaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ocena', function(Ocena) {
                            return Ocena.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ocena.new', {
            parent: 'ocena',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ocena/ocena-dialog.html',
                    controller: 'OcenaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ocena: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ocena', null, { reload: 'ocena' });
                }, function() {
                    $state.go('ocena');
                });
            }]
        })
        .state('ocena.edit', {
            parent: 'ocena',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ocena/ocena-dialog.html',
                    controller: 'OcenaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ocena', function(Ocena) {
                            return Ocena.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ocena', null, { reload: 'ocena' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ocena.delete', {
            parent: 'ocena',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ocena/ocena-delete-dialog.html',
                    controller: 'OcenaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ocena', function(Ocena) {
                            return Ocena.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ocena', null, { reload: 'ocena' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
