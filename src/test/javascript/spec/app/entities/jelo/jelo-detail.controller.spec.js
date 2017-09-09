'use strict';

describe('Controller Tests', function() {

    describe('Jelo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJelo, MockJelovnik, MockPorudzbina;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJelo = jasmine.createSpy('MockJelo');
            MockJelovnik = jasmine.createSpy('MockJelovnik');
            MockPorudzbina = jasmine.createSpy('MockPorudzbina');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Jelo': MockJelo,
                'Jelovnik': MockJelovnik,
                'Porudzbina': MockPorudzbina
            };
            createController = function() {
                $injector.get('$controller')("JeloDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:jeloUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
