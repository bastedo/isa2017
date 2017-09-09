'use strict';

describe('Controller Tests', function() {

    describe('Racun Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRacun, MockZaposleni, MockStol;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRacun = jasmine.createSpy('MockRacun');
            MockZaposleni = jasmine.createSpy('MockZaposleni');
            MockStol = jasmine.createSpy('MockStol');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Racun': MockRacun,
                'Zaposleni': MockZaposleni,
                'Stol': MockStol
            };
            createController = function() {
                $injector.get('$controller')("RacunDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:racunUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
