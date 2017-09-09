'use strict';

describe('Controller Tests', function() {

    describe('Jelovnik Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJelovnik, MockJelo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJelovnik = jasmine.createSpy('MockJelovnik');
            MockJelo = jasmine.createSpy('MockJelo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Jelovnik': MockJelovnik,
                'Jelo': MockJelo
            };
            createController = function() {
                $injector.get('$controller')("JelovnikDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:jelovnikUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
