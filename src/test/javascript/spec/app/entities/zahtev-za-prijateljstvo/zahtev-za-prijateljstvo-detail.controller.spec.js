'use strict';

describe('Controller Tests', function() {

    describe('ZahtevZaPrijateljstvo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockZahtevZaPrijateljstvo, MockGost;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockZahtevZaPrijateljstvo = jasmine.createSpy('MockZahtevZaPrijateljstvo');
            MockGost = jasmine.createSpy('MockGost');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ZahtevZaPrijateljstvo': MockZahtevZaPrijateljstvo,
                'Gost': MockGost
            };
            createController = function() {
                $injector.get('$controller')("ZahtevZaPrijateljstvoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:zahtevZaPrijateljstvoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
