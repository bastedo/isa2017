(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('karta-pica', {
            parent: 'entity',
            url: '/karta-pica',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KartaPicas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/karta-pica/karta-picas.html',
                    controller: 'KartaPicaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('karta-pica-detail', {
            parent: 'karta-pica',
            url: '/karta-pica/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KartaPica'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/karta-pica/karta-pica-detail.html',
                    controller: 'KartaPicaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KartaPica', function($stateParams, KartaPica) {
                    return KartaPica.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'karta-pica',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('karta-pica-detail.edit', {
            parent: 'karta-pica-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/karta-pica/karta-pica-dialog.html',
                    controller: 'KartaPicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KartaPica', function(KartaPica) {
                            return KartaPica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('karta-pica.new', {
            parent: 'karta-pica',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/karta-pica/karta-pica-dialog.html',
                    controller: 'KartaPicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imeKartePica: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('karta-pica', null, { reload: 'karta-pica' });
                }, function() {
                    $state.go('karta-pica');
                });
            }]
        })
        .state('karta-pica.edit', {
            parent: 'karta-pica',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/karta-pica/karta-pica-dialog.html',
                    controller: 'KartaPicaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KartaPica', function(KartaPica) {
                            return KartaPica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('karta-pica', null, { reload: 'karta-pica' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('karta-pica.delete', {
            parent: 'karta-pica',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/karta-pica/karta-pica-delete-dialog.html',
                    controller: 'KartaPicaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KartaPica', function(KartaPica) {
                            return KartaPica.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('karta-pica', null, { reload: 'karta-pica' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
