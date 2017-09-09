'use strict';

describe('Controller Tests', function() {

    describe('PorudzbinaZANabavku Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPorudzbinaZANabavku, MockPozivZaPrikupljanjeN;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPorudzbinaZANabavku = jasmine.createSpy('MockPorudzbinaZANabavku');
            MockPozivZaPrikupljanjeN = jasmine.createSpy('MockPozivZaPrikupljanjeN');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PorudzbinaZANabavku': MockPorudzbinaZANabavku,
                'PozivZaPrikupljanjeN': MockPozivZaPrikupljanjeN
            };
            createController = function() {
                $injector.get('$controller')("PorudzbinaZANabavkuDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:porudzbinaZANabavkuUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
