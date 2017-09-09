'use strict';

describe('Controller Tests', function() {

    describe('RasporedSmenaZaKonobare Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRasporedSmenaZaKonobare, MockZaposleni, MockRestoran;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRasporedSmenaZaKonobare = jasmine.createSpy('MockRasporedSmenaZaKonobare');
            MockZaposleni = jasmine.createSpy('MockZaposleni');
            MockRestoran = jasmine.createSpy('MockRestoran');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'RasporedSmenaZaKonobare': MockRasporedSmenaZaKonobare,
                'Zaposleni': MockZaposleni,
                'Restoran': MockRestoran
            };
            createController = function() {
                $injector.get('$controller')("RasporedSmenaZaKonobareDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:rasporedSmenaZaKonobareUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
