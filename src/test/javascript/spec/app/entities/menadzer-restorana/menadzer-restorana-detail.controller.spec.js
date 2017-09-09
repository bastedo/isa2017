'use strict';

describe('Controller Tests', function() {

    describe('MenadzerRestorana Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMenadzerRestorana, MockKonfiguracijaStolova, MockRestoran;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMenadzerRestorana = jasmine.createSpy('MockMenadzerRestorana');
            MockKonfiguracijaStolova = jasmine.createSpy('MockKonfiguracijaStolova');
            MockRestoran = jasmine.createSpy('MockRestoran');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MenadzerRestorana': MockMenadzerRestorana,
                'KonfiguracijaStolova': MockKonfiguracijaStolova,
                'Restoran': MockRestoran
            };
            createController = function() {
                $injector.get('$controller')("MenadzerRestoranaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:menadzerRestoranaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
