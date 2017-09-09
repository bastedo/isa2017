'use strict';

describe('Controller Tests', function() {

    describe('Ocena Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOcena, MockGost, MockRestoran;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOcena = jasmine.createSpy('MockOcena');
            MockGost = jasmine.createSpy('MockGost');
            MockRestoran = jasmine.createSpy('MockRestoran');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Ocena': MockOcena,
                'Gost': MockGost,
                'Restoran': MockRestoran
            };
            createController = function() {
                $injector.get('$controller')("OcenaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:ocenaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
