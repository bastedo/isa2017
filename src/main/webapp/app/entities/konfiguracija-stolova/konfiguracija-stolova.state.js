(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('konfiguracija-stolova', {
            parent: 'entity',
            url: '/konfiguracija-stolova',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KonfiguracijaStolova'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/konfiguracija-stolova/konfiguracija-stolova.html',
                    controller: 'KonfiguracijaStolovaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('konfiguracija-stolova-detail', {
            parent: 'konfiguracija-stolova',
            url: '/konfiguracija-stolova/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'KonfiguracijaStolova'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/konfiguracija-stolova/konfiguracija-stolova-detail.html',
                    controller: 'KonfiguracijaStolovaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'KonfiguracijaStolova', function($stateParams, KonfiguracijaStolova) {
                    return KonfiguracijaStolova.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'konfiguracija-stolova',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('konfiguracija-stolova-detail.edit', {
            parent: 'konfiguracija-stolova-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/konfiguracija-stolova/konfiguracija-stolova-dialog.html',
                    controller: 'KonfiguracijaStolovaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KonfiguracijaStolova', function(KonfiguracijaStolova) {
                            return KonfiguracijaStolova.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('konfiguracija-stolova.new', {
            parent: 'konfiguracija-stolova',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/konfiguracija-stolova/konfiguracija-stolova-dialog.html',
                    controller: 'KonfiguracijaStolovaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('konfiguracija-stolova', null, { reload: 'konfiguracija-stolova' });
                }, function() {
                    $state.go('konfiguracija-stolova');
                });
            }]
        })
        .state('konfiguracija-stolova.edit', {
            parent: 'konfiguracija-stolova',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/konfiguracija-stolova/konfiguracija-stolova-dialog.html',
                    controller: 'KonfiguracijaStolovaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['KonfiguracijaStolova', function(KonfiguracijaStolova) {
                            return KonfiguracijaStolova.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('konfiguracija-stolova', null, { reload: 'konfiguracija-stolova' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('konfiguracija-stolova.delete', {
            parent: 'konfiguracija-stolova',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/konfiguracija-stolova/konfiguracija-stolova-delete-dialog.html',
                    controller: 'KonfiguracijaStolovaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['KonfiguracijaStolova', function(KonfiguracijaStolova) {
                            return KonfiguracijaStolova.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('konfiguracija-stolova', null, { reload: 'konfiguracija-stolova' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
