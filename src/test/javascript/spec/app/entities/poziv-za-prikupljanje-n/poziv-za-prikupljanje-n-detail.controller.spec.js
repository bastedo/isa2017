'use strict';

describe('Controller Tests', function() {

    describe('PozivZaPrikupljanjeN Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPozivZaPrikupljanjeN, MockRestoran, MockPorudzbinaZANabavku;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPozivZaPrikupljanjeN = jasmine.createSpy('MockPozivZaPrikupljanjeN');
            MockRestoran = jasmine.createSpy('MockRestoran');
            MockPorudzbinaZANabavku = jasmine.createSpy('MockPorudzbinaZANabavku');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PozivZaPrikupljanjeN': MockPozivZaPrikupljanjeN,
                'Restoran': MockRestoran,
                'PorudzbinaZANabavku': MockPorudzbinaZANabavku
            };
            createController = function() {
                $injector.get('$controller')("PozivZaPrikupljanjeNDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:pozivZaPrikupljanjeNUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
