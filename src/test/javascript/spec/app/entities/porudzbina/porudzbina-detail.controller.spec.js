'use strict';

describe('Controller Tests', function() {

    describe('Porudzbina Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPorudzbina, MockJelo, MockPice, MockRezervacija;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPorudzbina = jasmine.createSpy('MockPorudzbina');
            MockJelo = jasmine.createSpy('MockJelo');
            MockPice = jasmine.createSpy('MockPice');
            MockRezervacija = jasmine.createSpy('MockRezervacija');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Porudzbina': MockPorudzbina,
                'Jelo': MockJelo,
                'Pice': MockPice,
                'Rezervacija': MockRezervacija
            };
            createController = function() {
                $injector.get('$controller')("PorudzbinaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:porudzbinaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
