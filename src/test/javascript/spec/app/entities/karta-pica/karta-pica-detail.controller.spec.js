'use strict';

describe('Controller Tests', function() {

    describe('KartaPica Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockKartaPica, MockPice;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockKartaPica = jasmine.createSpy('MockKartaPica');
            MockPice = jasmine.createSpy('MockPice');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'KartaPica': MockKartaPica,
                'Pice': MockPice
            };
            createController = function() {
                $injector.get('$controller')("KartaPicaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:kartaPicaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
