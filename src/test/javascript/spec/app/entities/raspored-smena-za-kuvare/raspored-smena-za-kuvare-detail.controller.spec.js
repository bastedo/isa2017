'use strict';

describe('Controller Tests', function() {

    describe('RasporedSmenaZaKuvare Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRasporedSmenaZaKuvare, MockZaposleni, MockRestoran;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRasporedSmenaZaKuvare = jasmine.createSpy('MockRasporedSmenaZaKuvare');
            MockZaposleni = jasmine.createSpy('MockZaposleni');
            MockRestoran = jasmine.createSpy('MockRestoran');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RasporedSmenaZaKuvare': MockRasporedSmenaZaKuvare,
                'Zaposleni': MockZaposleni,
                'Restoran': MockRestoran
            };
            createController = function() {
                $injector.get('$controller')("RasporedSmenaZaKuvareDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:rasporedSmenaZaKuvareUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
