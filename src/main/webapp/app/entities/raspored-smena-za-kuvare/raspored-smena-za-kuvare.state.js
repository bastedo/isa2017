(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('raspored-smena-za-kuvare', {
            parent: 'entity',
            url: '/raspored-smena-za-kuvare',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RasporedSmenaZaKuvares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/raspored-smena-za-kuvare/raspored-smena-za-kuvares.html',
                    controller: 'RasporedSmenaZaKuvareController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('raspored-smena-za-kuvare-detail', {
            parent: 'raspored-smena-za-kuvare',
            url: '/raspored-smena-za-kuvare/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RasporedSmenaZaKuvare'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/raspored-smena-za-kuvare/raspored-smena-za-kuvare-detail.html',
                    controller: 'RasporedSmenaZaKuvareDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RasporedSmenaZaKuvare', function($stateParams, RasporedSmenaZaKuvare) {
                    return RasporedSmenaZaKuvare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'raspored-smena-za-kuvare',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('raspored-smena-za-kuvare-detail.edit', {
            parent: 'raspored-smena-za-kuvare-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-kuvare/raspored-smena-za-kuvare-dialog.html',
                    controller: 'RasporedSmenaZaKuvareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RasporedSmenaZaKuvare', function(RasporedSmenaZaKuvare) {
                            return RasporedSmenaZaKuvare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('raspored-smena-za-kuvare.new', {
            parent: 'raspored-smena-za-kuvare',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-kuvare/raspored-smena-za-kuvare-dialog.html',
                    controller: 'RasporedSmenaZaKuvareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                vrstaSmene: null,
                                start: null,
                                end: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-kuvare', null, { reload: 'raspored-smena-za-kuvare' });
                }, function() {
                    $state.go('raspored-smena-za-kuvare');
                });
            }]
        })
        .state('raspored-smena-za-kuvare.edit', {
            parent: 'raspored-smena-za-kuvare',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-kuvare/raspored-smena-za-kuvare-dialog.html',
                    controller: 'RasporedSmenaZaKuvareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RasporedSmenaZaKuvare', function(RasporedSmenaZaKuvare) {
                            return RasporedSmenaZaKuvare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-kuvare', null, { reload: 'raspored-smena-za-kuvare' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('raspored-smena-za-kuvare.delete', {
            parent: 'raspored-smena-za-kuvare',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/raspored-smena-za-kuvare/raspored-smena-za-kuvare-delete-dialog.html',
                    controller: 'RasporedSmenaZaKuvareDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RasporedSmenaZaKuvare', function(RasporedSmenaZaKuvare) {
                            return RasporedSmenaZaKuvare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('raspored-smena-za-kuvare', null, { reload: 'raspored-smena-za-kuvare' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
