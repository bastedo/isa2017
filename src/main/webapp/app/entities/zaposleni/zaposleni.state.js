(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('zaposleni', {
            parent: 'entity',
            url: '/zaposleni',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Zaposlenis'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zaposleni/zaposlenis.html',
                    controller: 'ZaposleniController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('zaposleni-detail', {
            parent: 'zaposleni',
            url: '/zaposleni/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Zaposleni'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/zaposleni/zaposleni-detail.html',
                    controller: 'ZaposleniDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Zaposleni', function($stateParams, Zaposleni) {
                    return Zaposleni.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'zaposleni',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('zaposleni-detail.edit', {
            parent: 'zaposleni-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zaposleni.new', {
            parent: 'zaposleni',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                ime: null,
                                vrstaZaposlenja: null,
                                zaduzenjeZaSegment: null,
                                prezime: null,
                                email: null,
                                lozinka: null,
                                datumRodjenja: null,
                                konfekciskiBroj: null,
                                velicinaObuce: null,
                                firstLogin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: 'zaposleni' });
                }, function() {
                    $state.go('zaposleni');
                });
            }]
        })
        .state('zaposleni.edit', {
            parent: 'zaposleni',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-dialog.html',
                    controller: 'ZaposleniDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: 'zaposleni' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('zaposleni.delete', {
            parent: 'zaposleni',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/zaposleni/zaposleni-delete-dialog.html',
                    controller: 'ZaposleniDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Zaposleni', function(Zaposleni) {
                            return Zaposleni.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('zaposleni', null, { reload: 'zaposleni' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
