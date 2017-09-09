(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('zahtev-za-prijateljstvo', {
            parent: 'entity',
            url: '/zahtev-za-prijateljstvo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ZahtevZaPrijateljstvos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zahtev-za-prijateljstvo/zahtev-za-prijateljstvos.html',
                    controller: 'ZahtevZaPrijateljstvoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('zahtev-za-prijateljstvo-detail', {
            parent: 'zahtev-za-prijateljstvo',
            url: '/zahtev-za-prijateljstvo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ZahtevZaPrijateljstvo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zahtev-za-prijateljstvo/zahtev-za-prijateljstvo-detail.html',
                    controller: 'ZahtevZaPrijateljstvoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ZahtevZaPrijateljstvo', function($stateParams, ZahtevZaPrijateljstvo) {
                    return ZahtevZaPrijateljstvo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'zahtev-za-prijateljstvo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('zahtev-za-prijateljstvo-detail.edit', {
            parent: 'zahtev-za-prijateljstvo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zahtev-za-prijateljstvo/zahtev-za-prijateljstvo-dialog.html',
                    controller: 'ZahtevZaPrijateljstvoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ZahtevZaPrijateljstvo', function(ZahtevZaPrijateljstvo) {
                            return ZahtevZaPrijateljstvo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zahtev-za-prijateljstvo.new', {
            parent: 'zahtev-za-prijateljstvo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zahtev-za-prijateljstvo/zahtev-za-prijateljstvo-dialog.html',
                    controller: 'ZahtevZaPrijateljstvoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idPodnosiocaZahteva: null,
                                postalanZahtev: null,
                                prihvacenZahtev: null,
                                prihvacen: null,
                                odbijen: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('zahtev-za-prijateljstvo', null, { reload: 'zahtev-za-prijateljstvo' });
                }, function() {
                    $state.go('zahtev-za-prijateljstvo');
                });
            }]
        })
        .state('zahtev-za-prijateljstvo.edit', {
            parent: 'zahtev-za-prijateljstvo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zahtev-za-prijateljstvo/zahtev-za-prijateljstvo-dialog.html',
                    controller: 'ZahtevZaPrijateljstvoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ZahtevZaPrijateljstvo', function(ZahtevZaPrijateljstvo) {
                            return ZahtevZaPrijateljstvo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zahtev-za-prijateljstvo', null, { reload: 'zahtev-za-prijateljstvo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zahtev-za-prijateljstvo.delete', {
            parent: 'zahtev-za-prijateljstvo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zahtev-za-prijateljstvo/zahtev-za-prijateljstvo-delete-dialog.html',
                    controller: 'ZahtevZaPrijateljstvoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ZahtevZaPrijateljstvo', function(ZahtevZaPrijateljstvo) {
                            return ZahtevZaPrijateljstvo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zahtev-za-prijateljstvo', null, { reload: 'zahtev-za-prijateljstvo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
