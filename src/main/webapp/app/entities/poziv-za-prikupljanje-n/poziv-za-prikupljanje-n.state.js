(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('poziv-za-prikupljanje-n', {
            parent: 'entity',
            url: '/poziv-za-prikupljanje-n',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PozivZaPrikupljanjeNS'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/poziv-za-prikupljanje-n/poziv-za-prikupljanje-ns.html',
                    controller: 'PozivZaPrikupljanjeNController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('poziv-za-prikupljanje-n-detail', {
            parent: 'poziv-za-prikupljanje-n',
            url: '/poziv-za-prikupljanje-n/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PozivZaPrikupljanjeN'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/poziv-za-prikupljanje-n/poziv-za-prikupljanje-n-detail.html',
                    controller: 'PozivZaPrikupljanjeNDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PozivZaPrikupljanjeN', function($stateParams, PozivZaPrikupljanjeN) {
                    return PozivZaPrikupljanjeN.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'poziv-za-prikupljanje-n',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('poziv-za-prikupljanje-n-detail.edit', {
            parent: 'poziv-za-prikupljanje-n-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv-za-prikupljanje-n/poziv-za-prikupljanje-n-dialog.html',
                    controller: 'PozivZaPrikupljanjeNDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PozivZaPrikupljanjeN', function(PozivZaPrikupljanjeN) {
                            return PozivZaPrikupljanjeN.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('poziv-za-prikupljanje-n.new', {
            parent: 'poziv-za-prikupljanje-n',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv-za-prikupljanje-n/poziv-za-prikupljanje-n-dialog.html',
                    controller: 'PozivZaPrikupljanjeNDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                spisakPotreba: null,
                                startDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('poziv-za-prikupljanje-n', null, { reload: 'poziv-za-prikupljanje-n' });
                }, function() {
                    $state.go('poziv-za-prikupljanje-n');
                });
            }]
        })
        .state('poziv-za-prikupljanje-n.edit', {
            parent: 'poziv-za-prikupljanje-n',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv-za-prikupljanje-n/poziv-za-prikupljanje-n-dialog.html',
                    controller: 'PozivZaPrikupljanjeNDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PozivZaPrikupljanjeN', function(PozivZaPrikupljanjeN) {
                            return PozivZaPrikupljanjeN.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('poziv-za-prikupljanje-n', null, { reload: 'poziv-za-prikupljanje-n' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('poziv-za-prikupljanje-n.delete', {
            parent: 'poziv-za-prikupljanje-n',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/poziv-za-prikupljanje-n/poziv-za-prikupljanje-n-delete-dialog.html',
                    controller: 'PozivZaPrikupljanjeNDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PozivZaPrikupljanjeN', function(PozivZaPrikupljanjeN) {
                            return PozivZaPrikupljanjeN.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('poziv-za-prikupljanje-n', null, { reload: 'poziv-za-prikupljanje-n' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
