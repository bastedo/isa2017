'use strict';

describe('Controller Tests', function() {

    describe('Stol Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockStol, MockKonfiguracijaStolova, MockRacun, MockRezervacija;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockStol = jasmine.createSpy('MockStol');
            MockKonfiguracijaStolova = jasmine.createSpy('MockKonfiguracijaStolova');
            MockRacun = jasmine.createSpy('MockRacun');
            MockRezervacija = jasmine.createSpy('MockRezervacija');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Stol': MockStol,
                'KonfiguracijaStolova': MockKonfiguracijaStolova,
                'Racun': MockRacun,
                'Rezervacija': MockRezervacija
            };
            createController = function() {
                $injector.get('$controller')("StolDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:stolUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
