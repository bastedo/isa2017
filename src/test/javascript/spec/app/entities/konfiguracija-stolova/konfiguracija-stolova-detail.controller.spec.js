'use strict';

describe('Controller Tests', function() {

    describe('KonfiguracijaStolova Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockKonfiguracijaStolova, MockMenadzerRestorana, MockStol;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockKonfiguracijaStolova = jasmine.createSpy('MockKonfiguracijaStolova');
            MockMenadzerRestorana = jasmine.createSpy('MockMenadzerRestorana');
            MockStol = jasmine.createSpy('MockStol');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'KonfiguracijaStolova': MockKonfiguracijaStolova,
                'MenadzerRestorana': MockMenadzerRestorana,
                'Stol': MockStol
            };
            createController = function() {
                $injector.get('$controller')("KonfiguracijaStolovaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:konfiguracijaStolovaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
