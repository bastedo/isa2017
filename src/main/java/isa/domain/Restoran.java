package isa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Restoran.
 */
@Entity
@Table(name = "restoran")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Restoran implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "vrsta")
    private String vrsta;

    @Column(name = "ocena_restorana")
    private Double ocenaRestorana;

    @OneToOne
    @JoinColumn(unique = true)
    private KonfiguracijaStolova konfiguracijaStolova;

    @OneToOne
    @JoinColumn(unique = true)
    private Jelovnik jeovnik;

    @OneToOne
    @JoinColumn(unique = true)
    private KartaPica kartaPica;

    @OneToMany(mappedBy = "restoran")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ocena> ocenas = new HashSet<>();

    @OneToMany(mappedBy = "restoran")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PozivZaPrikupljanjeN> pozivZaPrikupljanjes = new HashSet<>();

    @OneToMany(mappedBy = "restoran")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rezervacija> rezervacijas = new HashSet<>();

    @OneToMany(mappedBy = "menadzerZaRestoran")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MenadzerRestorana> menadzeriRestoranas = new HashSet<>();

    @OneToMany(mappedBy = "radiURestoranu")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Zaposleni> zaposleniRestoranas = new HashSet<>();

    @OneToMany(mappedBy = "restoran")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RasporedSmenaZaSankere> rasporedSmenaZaSankeres = new HashSet<>();

    @OneToMany(mappedBy = "restoran")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RasporedSmenaZaKonobare> rasporedSmenaZaKonobares = new HashSet<>();

    @OneToMany(mappedBy = "restoran")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RasporedSmenaZaKuvare> rasporedSmenaZaKuvares = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public Restoran naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getVrsta() {
        return vrsta;
    }

    public Restoran vrsta(String vrsta) {
        this.vrsta = vrsta;
        return this;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public Double getOcenaRestorana() {
        return ocenaRestorana;
    }

    public Restoran ocenaRestorana(Double ocenaRestorana) {
        this.ocenaRestorana = ocenaRestorana;
        return this;
    }

    public void setOcenaRestorana(Double ocenaRestorana) {
        this.ocenaRestorana = ocenaRestorana;
    }

    public KonfiguracijaStolova getKonfiguracijaStolova() {
        return konfiguracijaStolova;
    }

    public Restoran konfiguracijaStolova(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijaStolova = konfiguracijaStolova;
        return this;
    }

    public void setKonfiguracijaStolova(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijaStolova = konfiguracijaStolova;
    }

    public Jelovnik getJeovnik() {
        return jeovnik;
    }

    public Restoran jeovnik(Jelovnik jelovnik) {
        this.jeovnik = jelovnik;
        return this;
    }

    public void setJeovnik(Jelovnik jelovnik) {
        this.jeovnik = jelovnik;
    }

    public KartaPica getKartaPica() {
        return kartaPica;
    }

    public Restoran kartaPica(KartaPica kartaPica) {
        this.kartaPica = kartaPica;
        return this;
    }

    public void setKartaPica(KartaPica kartaPica) {
        this.kartaPica = kartaPica;
    }

    public Set<Ocena> getOcenas() {
        return ocenas;
    }

    public Restoran ocenas(Set<Ocena> ocenas) {
        this.ocenas = ocenas;
        return this;
    }

    public Restoran addOcena(Ocena ocena) {
        this.ocenas.add(ocena);
        ocena.setRestoran(this);
        return this;
    }

    public Restoran removeOcena(Ocena ocena) {
        this.ocenas.remove(ocena);
        ocena.setRestoran(null);
        return this;
    }

    public void setOcenas(Set<Ocena> ocenas) {
        this.ocenas = ocenas;
    }

    public Set<PozivZaPrikupljanjeN> getPozivZaPrikupljanjes() {
        return pozivZaPrikupljanjes;
    }

    public Restoran pozivZaPrikupljanjes(Set<PozivZaPrikupljanjeN> pozivZaPrikupljanjeNS) {
        this.pozivZaPrikupljanjes = pozivZaPrikupljanjeNS;
        return this;
    }

    public Restoran addPozivZaPrikupljanje(PozivZaPrikupljanjeN pozivZaPrikupljanjeN) {
        this.pozivZaPrikupljanjes.add(pozivZaPrikupljanjeN);
        pozivZaPrikupljanjeN.setRestoran(this);
        return this;
    }

    public Restoran removePozivZaPrikupljanje(PozivZaPrikupljanjeN pozivZaPrikupljanjeN) {
        this.pozivZaPrikupljanjes.remove(pozivZaPrikupljanjeN);
        pozivZaPrikupljanjeN.setRestoran(null);
        return this;
    }

    public void setPozivZaPrikupljanjes(Set<PozivZaPrikupljanjeN> pozivZaPrikupljanjeNS) {
        this.pozivZaPrikupljanjes = pozivZaPrikupljanjeNS;
    }

    public Set<Rezervacija> getRezervacijas() {
        return rezervacijas;
    }

    public Restoran rezervacijas(Set<Rezervacija> rezervacijas) {
        this.rezervacijas = rezervacijas;
        return this;
    }

    public Restoran addRezervacija(Rezervacija rezervacija) {
        this.rezervacijas.add(rezervacija);
        rezervacija.setRestoran(this);
        return this;
    }

    public Restoran removeRezervacija(Rezervacija rezervacija) {
        this.rezervacijas.remove(rezervacija);
        rezervacija.setRestoran(null);
        return this;
    }

    public void setRezervacijas(Set<Rezervacija> rezervacijas) {
        this.rezervacijas = rezervacijas;
    }

    public Set<MenadzerRestorana> getMenadzeriRestoranas() {
        return menadzeriRestoranas;
    }

    public Restoran menadzeriRestoranas(Set<MenadzerRestorana> menadzerRestoranas) {
        this.menadzeriRestoranas = menadzerRestoranas;
        return this;
    }

    public Restoran addMenadzeriRestorana(MenadzerRestorana menadzerRestorana) {
        this.menadzeriRestoranas.add(menadzerRestorana);
        menadzerRestorana.setMenadzerZaRestoran(this);
        return this;
    }

    public Restoran removeMenadzeriRestorana(MenadzerRestorana menadzerRestorana) {
        this.menadzeriRestoranas.remove(menadzerRestorana);
        menadzerRestorana.setMenadzerZaRestoran(null);
        return this;
    }

    public void setMenadzeriRestoranas(Set<MenadzerRestorana> menadzerRestoranas) {
        this.menadzeriRestoranas = menadzerRestoranas;
    }

    public Set<Zaposleni> getZaposleniRestoranas() {
        return zaposleniRestoranas;
    }

    public Restoran zaposleniRestoranas(Set<Zaposleni> zaposlenis) {
        this.zaposleniRestoranas = zaposlenis;
        return this;
    }

    public Restoran addZaposleniRestorana(Zaposleni zaposleni) {
        this.zaposleniRestoranas.add(zaposleni);
        zaposleni.setRadiURestoranu(this);
        return this;
    }

    public Restoran removeZaposleniRestorana(Zaposleni zaposleni) {
        this.zaposleniRestoranas.remove(zaposleni);
        zaposleni.setRadiURestoranu(null);
        return this;
    }

    public void setZaposleniRestoranas(Set<Zaposleni> zaposlenis) {
        this.zaposleniRestoranas = zaposlenis;
    }

    public Set<RasporedSmenaZaSankere> getRasporedSmenaZaSankeres() {
        return rasporedSmenaZaSankeres;
    }

    public Restoran rasporedSmenaZaSankeres(Set<RasporedSmenaZaSankere> rasporedSmenaZaSankeres) {
        this.rasporedSmenaZaSankeres = rasporedSmenaZaSankeres;
        return this;
    }

    public Restoran addRasporedSmenaZaSankere(RasporedSmenaZaSankere rasporedSmenaZaSankere) {
        this.rasporedSmenaZaSankeres.add(rasporedSmenaZaSankere);
        rasporedSmenaZaSankere.setRestoran(this);
        return this;
    }

    public Restoran removeRasporedSmenaZaSankere(RasporedSmenaZaSankere rasporedSmenaZaSankere) {
        this.rasporedSmenaZaSankeres.remove(rasporedSmenaZaSankere);
        rasporedSmenaZaSankere.setRestoran(null);
        return this;
    }

    public void setRasporedSmenaZaSankeres(Set<RasporedSmenaZaSankere> rasporedSmenaZaSankeres) {
        this.rasporedSmenaZaSankeres = rasporedSmenaZaSankeres;
    }

    public Set<RasporedSmenaZaKonobare> getRasporedSmenaZaKonobares() {
        return rasporedSmenaZaKonobares;
    }

    public Restoran rasporedSmenaZaKonobares(Set<RasporedSmenaZaKonobare> rasporedSmenaZaKonobares) {
        this.rasporedSmenaZaKonobares = rasporedSmenaZaKonobares;
        return this;
    }

    public Restoran addRasporedSmenaZaKonobare(RasporedSmenaZaKonobare rasporedSmenaZaKonobare) {
        this.rasporedSmenaZaKonobares.add(rasporedSmenaZaKonobare);
        rasporedSmenaZaKonobare.setRestoran(this);
        return this;
    }

    public Restoran removeRasporedSmenaZaKonobare(RasporedSmenaZaKonobare rasporedSmenaZaKonobare) {
        this.rasporedSmenaZaKonobares.remove(rasporedSmenaZaKonobare);
        rasporedSmenaZaKonobare.setRestoran(null);
        return this;
    }

    public void setRasporedSmenaZaKonobares(Set<RasporedSmenaZaKonobare> rasporedSmenaZaKonobares) {
        this.rasporedSmenaZaKonobares = rasporedSmenaZaKonobares;
    }

    public Set<RasporedSmenaZaKuvare> getRasporedSmenaZaKuvares() {
        return rasporedSmenaZaKuvares;
    }

    public Restoran rasporedSmenaZaKuvares(Set<RasporedSmenaZaKuvare> rasporedSmenaZaKuvares) {
        this.rasporedSmenaZaKuvares = rasporedSmenaZaKuvares;
        return this;
    }

    public Restoran addRasporedSmenaZaKuvare(RasporedSmenaZaKuvare rasporedSmenaZaKuvare) {
        this.rasporedSmenaZaKuvares.add(rasporedSmenaZaKuvare);
        rasporedSmenaZaKuvare.setRestoran(this);
        return this;
    }

    public Restoran removeRasporedSmenaZaKuvare(RasporedSmenaZaKuvare rasporedSmenaZaKuvare) {
        this.rasporedSmenaZaKuvares.remove(rasporedSmenaZaKuvare);
        rasporedSmenaZaKuvare.setRestoran(null);
        return this;
    }

    public void setRasporedSmenaZaKuvares(Set<RasporedSmenaZaKuvare> rasporedSmenaZaKuvares) {
        this.rasporedSmenaZaKuvares = rasporedSmenaZaKuvares;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restoran restoran = (Restoran) o;
        if (restoran.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), restoran.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Restoran{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", vrsta='" + getVrsta() + "'" +
            ", ocenaRestorana='" + getOcenaRestorana() + "'" +
            "}";
    }
}
