(function() {
    'use strict';

    angular
        .module('isa2017App')
        .controller('RestoranDialogController', RestoranDialogController);

    RestoranDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Restoran', 'KonfiguracijaStolova', 'Jelovnik', 'KartaPica', 'Ocena', 'PozivZaPrikupljanjeN', 'Rezervacija', 'MenadzerRestorana', 'Zaposleni', 'RasporedSmenaZaSankere', 'RasporedSmenaZaKonobare', 'RasporedSmenaZaKuvare'];

    function RestoranDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Restoran, KonfiguracijaStolova, Jelovnik, KartaPica, Ocena, PozivZaPrikupljanjeN, Rezervacija, MenadzerRestorana, Zaposleni, RasporedSmenaZaSankere, RasporedSmenaZaKonobare, RasporedSmenaZaKuvare) {
        var vm = this;

        vm.restoran = entity;
        vm.clear = clear;
        vm.save = save;
        vm.konfiguracijastolova = KonfiguracijaStolova.query({filter: 'restoran-is-null'});
        $q.all([vm.restoran.$promise, vm.konfiguracijastolova.$promise]).then(function() {
            if (!vm.restoran.konfiguracijaStolova || !vm.restoran.konfiguracijaStolova.id) {
                return $q.reject();
            }
            return KonfiguracijaStolova.get({id : vm.restoran.konfiguracijaStolova.id}).$promise;
        }).then(function(konfiguracijaStolova) {
            vm.konfiguracijastolova.push(konfiguracijaStolova);
        });
        vm.jeovniks = Jelovnik.query({filter: 'restoran-is-null'});
        $q.all([vm.restoran.$promise, vm.jeovniks.$promise]).then(function() {
            if (!vm.restoran.jeovnik || !vm.restoran.jeovnik.id) {
                return $q.reject();
            }
            return Jelovnik.get({id : vm.restoran.jeovnik.id}).$promise;
        }).then(function(jeovnik) {
            vm.jeovniks.push(jeovnik);
        });
        vm.kartapicas = KartaPica.query({filter: 'restoran-is-null'});
        $q.all([vm.restoran.$promise, vm.kartapicas.$promise]).then(function() {
            if (!vm.restoran.kartaPica || !vm.restoran.kartaPica.id) {
                return $q.reject();
            }
            return KartaPica.get({id : vm.restoran.kartaPica.id}).$promise;
        }).then(function(kartaPica) {
            vm.kartapicas.push(kartaPica);
        });
        vm.ocenas = Ocena.query();
        vm.pozivzaprikupljanjens = PozivZaPrikupljanjeN.query();
        vm.rezervacijas = Rezervacija.query();
        vm.menadzerrestoranas = MenadzerRestorana.query();
        vm.zaposlenis = Zaposleni.query();
        vm.rasporedsmenazasankeres = RasporedSmenaZaSankere.query();
        vm.rasporedsmenazakonobares = RasporedSmenaZaKonobare.query();
        vm.rasporedsmenazakuvares = RasporedSmenaZaKuvare.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.restoran.id !== null) {
                Restoran.update(vm.restoran, onSaveSuccess, onSaveError);
            } else {
                Restoran.save(vm.restoran, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('isa2017App:restoranUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
