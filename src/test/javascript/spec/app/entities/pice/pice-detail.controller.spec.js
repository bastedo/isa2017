'use strict';

describe('Controller Tests', function() {

    describe('Pice Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPice, MockKartaPica, MockPorudzbina;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPice = jasmine.createSpy('MockPice');
            MockKartaPica = jasmine.createSpy('MockKartaPica');
            MockPorudzbina = jasmine.createSpy('MockPorudzbina');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Pice': MockPice,
                'KartaPica': MockKartaPica,
                'Porudzbina': MockPorudzbina
            };
            createController = function() {
                $injector.get('$controller')("PiceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:piceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
