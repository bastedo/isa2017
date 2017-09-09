(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('jelovnik', {
            parent: 'entity',
            url: '/jelovnik',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Jelovniks'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jelovnik/jelovniks.html',
                    controller: 'JelovnikController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('jelovnik-detail', {
            parent: 'jelovnik',
            url: '/jelovnik/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Jelovnik'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jelovnik/jelovnik-detail.html',
                    controller: 'JelovnikDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Jelovnik', function($stateParams, Jelovnik) {
                    return Jelovnik.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'jelovnik',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('jelovnik-detail.edit', {
            parent: 'jelovnik-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelovnik/jelovnik-dialog.html',
                    controller: 'JelovnikDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jelovnik', function(Jelovnik) {
                            return Jelovnik.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jelovnik.new', {
            parent: 'jelovnik',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelovnik/jelovnik-dialog.html',
                    controller: 'JelovnikDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imeJelovnika: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('jelovnik', null, { reload: 'jelovnik' });
                }, function() {
                    $state.go('jelovnik');
                });
            }]
        })
        .state('jelovnik.edit', {
            parent: 'jelovnik',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelovnik/jelovnik-dialog.html',
                    controller: 'JelovnikDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jelovnik', function(Jelovnik) {
                            return Jelovnik.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jelovnik', null, { reload: 'jelovnik' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jelovnik.delete', {
            parent: 'jelovnik',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jelovnik/jelovnik-delete-dialog.html',
                    controller: 'JelovnikDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Jelovnik', function(Jelovnik) {
                            return Jelovnik.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jelovnik', null, { reload: 'jelovnik' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
