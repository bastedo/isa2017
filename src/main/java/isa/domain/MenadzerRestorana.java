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
 * A MenadzerRestorana.
 */
@Entity
@Table(name = "menadzer_restorana")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MenadzerRestorana implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "email")
    private String email;

    @Column(name = "lozinka")
    private String lozinka;

    @OneToMany(mappedBy = "menadzerRestorana")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<KonfiguracijaStolova> konfiguracijas = new HashSet<>();

    @ManyToOne
    private Restoran menadzerZaRestoran;

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

    public MenadzerRestorana ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public MenadzerRestorana prezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public MenadzerRestorana email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public MenadzerRestorana lozinka(String lozinka) {
        this.lozinka = lozinka;
        return this;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Set<KonfiguracijaStolova> getKonfiguracijas() {
        return konfiguracijas;
    }

    public MenadzerRestorana konfiguracijas(Set<KonfiguracijaStolova> konfiguracijaStolova) {
        this.konfiguracijas = konfiguracijaStolova;
        return this;
    }

    public MenadzerRestorana addKonfiguracija(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijas.add(konfiguracijaStolova);
        konfiguracijaStolova.setMenadzerRestorana(this);
        return this;
    }

    public MenadzerRestorana removeKonfiguracija(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijas.remove(konfiguracijaStolova);
        konfiguracijaStolova.setMenadzerRestorana(null);
        return this;
    }

    public void setKonfiguracijas(Set<KonfiguracijaStolova> konfiguracijaStolova) {
        this.konfiguracijas = konfiguracijaStolova;
    }

    public Restoran getMenadzerZaRestoran() {
        return menadzerZaRestoran;
    }

    public MenadzerRestorana menadzerZaRestoran(Restoran restoran) {
        this.menadzerZaRestoran = restoran;
        return this;
    }

    public void setMenadzerZaRestoran(Restoran restoran) {
        this.menadzerZaRestoran = restoran;
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
        MenadzerRestorana menadzerRestorana = (MenadzerRestorana) o;
        if (menadzerRestorana.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menadzerRestorana.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenadzerRestorana{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", email='" + getEmail() + "'" +
            ", lozinka='" + getLozinka() + "'" +
            "}";
    }
}
