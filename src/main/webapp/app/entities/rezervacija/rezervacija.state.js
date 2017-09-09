(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rezervacija', {
            parent: 'entity',
            url: '/rezervacija',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rezervacijas'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervacija/rezervacijas.html',
                    controller: 'RezervacijaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('rezervacija-detail', {
            parent: 'rezervacija',
            url: '/rezervacija/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Rezervacija'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rezervacija/rezervacija-detail.html',
                    controller: 'RezervacijaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Rezervacija', function($stateParams, Rezervacija) {
                    return Rezervacija.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rezervacija',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rezervacija-detail.edit', {
            parent: 'rezervacija-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija/rezervacija-dialog.html',
                    controller: 'RezervacijaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rezervacija', function(Rezervacija) {
                            return Rezervacija.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rezervacija.new', {
            parent: 'rezervacija',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija/rezervacija-dialog.html',
                    controller: 'RezervacijaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                ocenjeno: null,
                                potvrdjeno: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rezervacija', null, { reload: 'rezervacija' });
                }, function() {
                    $state.go('rezervacija');
                });
            }]
        })
        .state('rezervacija.edit', {
            parent: 'rezervacija',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija/rezervacija-dialog.html',
                    controller: 'RezervacijaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rezervacija', function(Rezervacija) {
                            return Rezervacija.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervacija', null, { reload: 'rezervacija' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rezervacija.delete', {
            parent: 'rezervacija',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rezervacija/rezervacija-delete-dialog.html',
                    controller: 'RezervacijaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rezervacija', function(Rezervacija) {
                            return Rezervacija.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rezervacija', null, { reload: 'rezervacija' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
