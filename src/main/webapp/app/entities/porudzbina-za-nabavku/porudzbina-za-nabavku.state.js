(function() {
    'use strict';

    angular
        .module('isa2017App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('porudzbina-za-nabavku', {
            parent: 'entity',
            url: '/porudzbina-za-nabavku',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PorudzbinaZANabavkus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/porudzbina-za-nabavku/porudzbina-za-nabavkus.html',
                    controller: 'PorudzbinaZANabavkuController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('porudzbina-za-nabavku-detail', {
            parent: 'porudzbina-za-nabavku',
            url: '/porudzbina-za-nabavku/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PorudzbinaZANabavku'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/porudzbina-za-nabavku/porudzbina-za-nabavku-detail.html',
                    controller: 'PorudzbinaZANabavkuDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PorudzbinaZANabavku', function($stateParams, PorudzbinaZANabavku) {
                    return PorudzbinaZANabavku.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'porudzbina-za-nabavku',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('porudzbina-za-nabavku-detail.edit', {
            parent: 'porudzbina-za-nabavku-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina-za-nabavku/porudzbina-za-nabavku-dialog.html',
                    controller: 'PorudzbinaZANabavkuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PorudzbinaZANabavku', function(PorudzbinaZANabavku) {
                            return PorudzbinaZANabavku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('porudzbina-za-nabavku.new', {
            parent: 'porudzbina-za-nabavku',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina-za-nabavku/porudzbina-za-nabavku-dialog.html',
                    controller: 'PorudzbinaZANabavkuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                porudzbina: null,
                                prihvacena: null,
                                vrednost: null,
                                dostava: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('porudzbina-za-nabavku', null, { reload: 'porudzbina-za-nabavku' });
                }, function() {
                    $state.go('porudzbina-za-nabavku');
                });
            }]
        })
        .state('porudzbina-za-nabavku.edit', {
            parent: 'porudzbina-za-nabavku',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina-za-nabavku/porudzbina-za-nabavku-dialog.html',
                    controller: 'PorudzbinaZANabavkuDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PorudzbinaZANabavku', function(PorudzbinaZANabavku) {
                            return PorudzbinaZANabavku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('porudzbina-za-nabavku', null, { reload: 'porudzbina-za-nabavku' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('porudzbina-za-nabavku.delete', {
            parent: 'porudzbina-za-nabavku',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/porudzbina-za-nabavku/porudzbina-za-nabavku-delete-dialog.html',
                    controller: 'PorudzbinaZANabavkuDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PorudzbinaZANabavku', function(PorudzbinaZANabavku) {
                            return PorudzbinaZANabavku.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('porudzbina-za-nabavku', null, { reload: 'porudzbina-za-nabavku' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
