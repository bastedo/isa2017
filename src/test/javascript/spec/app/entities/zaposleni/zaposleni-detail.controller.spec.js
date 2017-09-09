'use strict';

describe('Controller Tests', function() {

    describe('Zaposleni Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockZaposleni, MockKonfiguracijaStolova, MockRasporedSmenaZaSankere, MockRasporedSmenaZaKonobare, MockRasporedSmenaZaKuvare, MockRacun, MockRestoran;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockZaposleni = jasmine.createSpy('MockZaposleni');
            MockKonfiguracijaStolova = jasmine.createSpy('MockKonfiguracijaStolova');
            MockRasporedSmenaZaSankere = jasmine.createSpy('MockRasporedSmenaZaSankere');
            MockRasporedSmenaZaKonobare = jasmine.createSpy('MockRasporedSmenaZaKonobare');
            MockRasporedSmenaZaKuvare = jasmine.createSpy('MockRasporedSmenaZaKuvare');
            MockRacun = jasmine.createSpy('MockRacun');
            MockRestoran = jasmine.createSpy('MockRestoran');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Zaposleni': MockZaposleni,
                'KonfiguracijaStolova': MockKonfiguracijaStolova,
                'RasporedSmenaZaSankere': MockRasporedSmenaZaSankere,
                'RasporedSmenaZaKonobare': MockRasporedSmenaZaKonobare,
                'RasporedSmenaZaKuvare': MockRasporedSmenaZaKuvare,
                'Racun': MockRacun,
                'Restoran': MockRestoran
            };
            createController = function() {
                $injector.get('$controller')("ZaposleniDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:zaposleniUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
