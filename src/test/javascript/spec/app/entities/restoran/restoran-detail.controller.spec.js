'use strict';

describe('Controller Tests', function() {

    describe('Restoran Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRestoran, MockKonfiguracijaStolova, MockJelovnik, MockKartaPica, MockOcena, MockPozivZaPrikupljanjeN, MockRezervacija, MockMenadzerRestorana, MockZaposleni, MockRasporedSmenaZaSankere, MockRasporedSmenaZaKonobare, MockRasporedSmenaZaKuvare;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRestoran = jasmine.createSpy('MockRestoran');
            MockKonfiguracijaStolova = jasmine.createSpy('MockKonfiguracijaStolova');
            MockJelovnik = jasmine.createSpy('MockJelovnik');
            MockKartaPica = jasmine.createSpy('MockKartaPica');
            MockOcena = jasmine.createSpy('MockOcena');
            MockPozivZaPrikupljanjeN = jasmine.createSpy('MockPozivZaPrikupljanjeN');
            MockRezervacija = jasmine.createSpy('MockRezervacija');
            MockMenadzerRestorana = jasmine.createSpy('MockMenadzerRestorana');
            MockZaposleni = jasmine.createSpy('MockZaposleni');
            MockRasporedSmenaZaSankere = jasmine.createSpy('MockRasporedSmenaZaSankere');
            MockRasporedSmenaZaKonobare = jasmine.createSpy('MockRasporedSmenaZaKonobare');
            MockRasporedSmenaZaKuvare = jasmine.createSpy('MockRasporedSmenaZaKuvare');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Restoran': MockRestoran,
                'KonfiguracijaStolova': MockKonfiguracijaStolova,
                'Jelovnik': MockJelovnik,
                'KartaPica': MockKartaPica,
                'Ocena': MockOcena,
                'PozivZaPrikupljanjeN': MockPozivZaPrikupljanjeN,
                'Rezervacija': MockRezervacija,
                'MenadzerRestorana': MockMenadzerRestorana,
                'Zaposleni': MockZaposleni,
                'RasporedSmenaZaSankere': MockRasporedSmenaZaSankere,
                'RasporedSmenaZaKonobare': MockRasporedSmenaZaKonobare,
                'RasporedSmenaZaKuvare': MockRasporedSmenaZaKuvare
            };
            createController = function() {
                $injector.get('$controller')("RestoranDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'isa2017App:restoranUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
