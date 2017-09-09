package isa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import isa.domain.enumeration.VrstaZaposlenja;

import isa.domain.enumeration.Segmenat;

/**
 * A Zaposleni.
 */
@Entity
@Table(name = "zaposleni")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Zaposleni implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Enumerated(EnumType.STRING)
    @Column(name = "vrsta_zaposlenja")
    private VrstaZaposlenja vrstaZaposlenja;

    @Enumerated(EnumType.STRING)
    @Column(name = "zaduzenje_za_segment")
    private Segmenat zaduzenjeZaSegment;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "email")
    private String email;

    @Column(name = "lozinka")
    private String lozinka;

    @Column(name = "datum_rodjenja")
    private LocalDate datumRodjenja;

    @Column(name = "konfekciski_broj")
    private Integer konfekciskiBroj;

    @Column(name = "velicina_obuce")
    private Integer velicinaObuce;

    @Column(name = "first_login")
    private Boolean firstLogin;

    @OneToOne
    @JoinColumn(unique = true)
    private KonfiguracijaStolova konfiguracijaStolova;

    @OneToMany(mappedBy = "zaposleni")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RasporedSmenaZaSankere> rasporedSmenaZaSankeres = new HashSet<>();

    @OneToMany(mappedBy = "zaposleni")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RasporedSmenaZaKonobare> rasporedSmenaZaKonobares = new HashSet<>();

    @OneToMany(mappedBy = "zaposleni")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RasporedSmenaZaKuvare> rasporedSmenaZaKuvares = new HashSet<>();

    @OneToMany(mappedBy = "naplatio")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Racun> racunis = new HashSet<>();

    @ManyToOne
    private Restoran radiURestoranu;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public Zaposleni ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public VrstaZaposlenja getVrstaZaposlenja() {
        return vrstaZaposlenja;
    }

    public Zaposleni vrstaZaposlenja(VrstaZaposlenja vrstaZaposlenja) {
        this.vrstaZaposlenja = vrstaZaposlenja;
        return this;
    }

    public void setVrstaZaposlenja(VrstaZaposlenja vrstaZaposlenja) {
        this.vrstaZaposlenja = vrstaZaposlenja;
    }

    public Segmenat getZaduzenjeZaSegment() {
        return zaduzenjeZaSegment;
    }

    public Zaposleni zaduzenjeZaSegment(Segmenat zaduzenjeZaSegment) {
        this.zaduzenjeZaSegment = zaduzenjeZaSegment;
        return this;
    }

    public void setZaduzenjeZaSegment(Segmenat zaduzenjeZaSegment) {
        this.zaduzenjeZaSegment = zaduzenjeZaSegment;
    }

    public String getPrezime() {
        return prezime;
    }

    public Zaposleni prezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public Zaposleni email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public Zaposleni lozinka(String lozinka) {
        this.lozinka = lozinka;
        return this;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public Zaposleni datumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
        return this;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Integer getKonfekciskiBroj() {
        return konfekciskiBroj;
    }

    public Zaposleni konfekciskiBroj(Integer konfekciskiBroj) {
        this.konfekciskiBroj = konfekciskiBroj;
        return this;
    }

    public void setKonfekciskiBroj(Integer konfekciskiBroj) {
        this.konfekciskiBroj = konfekciskiBroj;
    }

    public Integer getVelicinaObuce() {
        return velicinaObuce;
    }

    public Zaposleni velicinaObuce(Integer velicinaObuce) {
        this.velicinaObuce = velicinaObuce;
        return this;
    }

    public void setVelicinaObuce(Integer velicinaObuce) {
        this.velicinaObuce = velicinaObuce;
    }

    public Boolean isFirstLogin() {
        return firstLogin;
    }

    public Zaposleni firstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
        return this;
    }

    public void setFirstLogin(Boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public KonfiguracijaStolova getKonfiguracijaStolova() {
        return konfiguracijaStolova;
    }

    public Zaposleni konfiguracijaStolova(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijaStolova = konfiguracijaStolova;
        return this;
    }

    public void setKonfiguracijaStolova(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijaStolova = konfiguracijaStolova;
    }

    public Set<RasporedSmenaZaSankere> getRasporedSmenaZaSankeres() {
        return rasporedSmenaZaSankeres;
    }

    public Zaposleni rasporedSmenaZaSankeres(Set<RasporedSmenaZaSankere> rasporedSmenaZaSankeres) {
        this.rasporedSmenaZaSankeres = rasporedSmenaZaSankeres;
        return this;
    }

    public Zaposleni addRasporedSmenaZaSankere(RasporedSmenaZaSankere rasporedSmenaZaSankere) {
        this.rasporedSmenaZaSankeres.add(rasporedSmenaZaSankere);
        rasporedSmenaZaSankere.setZaposleni(this);
        return this;
    }

    public Zaposleni removeRasporedSmenaZaSankere(RasporedSmenaZaSankere rasporedSmenaZaSankere) {
        this.rasporedSmenaZaSankeres.remove(rasporedSmenaZaSankere);
        rasporedSmenaZaSankere.setZaposleni(null);
        return this;
    }

    public void setRasporedSmenaZaSankeres(Set<RasporedSmenaZaSankere> rasporedSmenaZaSankeres) {
        this.rasporedSmenaZaSankeres = rasporedSmenaZaSankeres;
    }

    public Set<RasporedSmenaZaKonobare> getRasporedSmenaZaKonobares() {
        return rasporedSmenaZaKonobares;
    }

    public Zaposleni rasporedSmenaZaKonobares(Set<RasporedSmenaZaKonobare> rasporedSmenaZaKonobares) {
        this.rasporedSmenaZaKonobares = rasporedSmenaZaKonobares;
        return this;
    }

    public Zaposleni addRasporedSmenaZaKonobare(RasporedSmenaZaKonobare rasporedSmenaZaKonobare) {
        this.rasporedSmenaZaKonobares.add(rasporedSmenaZaKonobare);
        rasporedSmenaZaKonobare.setZaposleni(this);
        return this;
    }

    public Zaposleni removeRasporedSmenaZaKonobare(RasporedSmenaZaKonobare rasporedSmenaZaKonobare) {
        this.rasporedSmenaZaKonobares.remove(rasporedSmenaZaKonobare);
        rasporedSmenaZaKonobare.setZaposleni(null);
        return this;
    }

    public void setRasporedSmenaZaKonobares(Set<RasporedSmenaZaKonobare> rasporedSmenaZaKonobares) {
        this.rasporedSmenaZaKonobares = rasporedSmenaZaKonobares;
    }

    public Set<RasporedSmenaZaKuvare> getRasporedSmenaZaKuvares() {
        return rasporedSmenaZaKuvares;
    }

    public Zaposleni rasporedSmenaZaKuvares(Set<RasporedSmenaZaKuvare> rasporedSmenaZaKuvares) {
        this.rasporedSmenaZaKuvares = rasporedSmenaZaKuvares;
        return this;
    }

    public Zaposleni addRasporedSmenaZaKuvare(RasporedSmenaZaKuvare rasporedSmenaZaKuvare) {
        this.rasporedSmenaZaKuvares.add(rasporedSmenaZaKuvare);
        rasporedSmenaZaKuvare.setZaposleni(this);
        return this;
    }

    public Zaposleni removeRasporedSmenaZaKuvare(RasporedSmenaZaKuvare rasporedSmenaZaKuvare) {
        this.rasporedSmenaZaKuvares.remove(rasporedSmenaZaKuvare);
        rasporedSmenaZaKuvare.setZaposleni(null);
        return this;
    }

    public void setRasporedSmenaZaKuvares(Set<RasporedSmenaZaKuvare> rasporedSmenaZaKuvares) {
        this.rasporedSmenaZaKuvares = rasporedSmenaZaKuvares;
    }

    public Set<Racun> getRacunis() {
        return racunis;
    }

    public Zaposleni racunis(Set<Racun> racuns) {
        this.racunis = racuns;
        return this;
    }

    public Zaposleni addRacuni(Racun racun) {
        this.racunis.add(racun);
        racun.setNaplatio(this);
        return this;
    }

    public Zaposleni removeRacuni(Racun racun) {
        this.racunis.remove(racun);
        racun.setNaplatio(null);
        return this;
    }

    public void setRacunis(Set<Racun> racuns) {
        this.racunis = racuns;
    }

    public Restoran getRadiURestoranu() {
        return radiURestoranu;
    }

    public Zaposleni radiURestoranu(Restoran restoran) {
        this.radiURestoranu = restoran;
        return this;
    }

    public void setRadiURestoranu(Restoran restoran) {
        this.radiURestoranu = restoran;
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
        Zaposleni zaposleni = (Zaposleni) o;
        if (zaposleni.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zaposleni.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Zaposleni{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", vrstaZaposlenja='" + getVrstaZaposlenja() + "'" +
            ", zaduzenjeZaSegment='" + getZaduzenjeZaSegment() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", email='" + getEmail() + "'" +
            ", lozinka='" + getLozinka() + "'" +
            ", datumRodjenja='" + getDatumRodjenja() + "'" +
            ", konfekciskiBroj='" + getKonfekciskiBroj() + "'" +
            ", velicinaObuce='" + getVelicinaObuce() + "'" +
            ", firstLogin='" + isFirstLogin() + "'" +
            "}";
    }
}
