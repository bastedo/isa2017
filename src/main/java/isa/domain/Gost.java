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
 * A Gost.
 */
@Entity
@Table(name = "gost")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gost implements Serializable {

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

    @OneToOne
    @JoinColumn(unique = true)
    private User userID;

    @OneToMany(mappedBy = "rezervisao")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rezervacija> rezervacijes = new HashSet<>();

    @OneToMany(mappedBy = "gost")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ZahtevZaPrijateljstvo> poslaoZahtevs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "gost_prijatelji",
               joinColumns = @JoinColumn(name="gosts_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="prijateljis_id", referencedColumnName="id"))
    private Set<Gost> prijateljis = new HashSet<>();

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

    public Gost ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Gost prezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public Gost email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public Gost lozinka(String lozinka) {
        this.lozinka = lozinka;
        return this;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public User getUserID() {
        return userID;
    }

    public Gost userID(User user) {
        this.userID = user;
        return this;
    }

    public void setUserID(User user) {
        this.userID = user;
    }

    public Set<Rezervacija> getRezervacijes() {
        return rezervacijes;
    }

    public Gost rezervacijes(Set<Rezervacija> rezervacijas) {
        this.rezervacijes = rezervacijas;
        return this;
    }

    public Gost addRezervacije(Rezervacija rezervacija) {
        this.rezervacijes.add(rezervacija);
        rezervacija.setRezervisao(this);
        return this;
    }

    public Gost removeRezervacije(Rezervacija rezervacija) {
        this.rezervacijes.remove(rezervacija);
        rezervacija.setRezervisao(null);
        return this;
    }

    public void setRezervacijes(Set<Rezervacija> rezervacijas) {
        this.rezervacijes = rezervacijas;
    }

    public Set<ZahtevZaPrijateljstvo> getPoslaoZahtevs() {
        return poslaoZahtevs;
    }

    public Gost poslaoZahtevs(Set<ZahtevZaPrijateljstvo> zahtevZaPrijateljstvos) {
        this.poslaoZahtevs = zahtevZaPrijateljstvos;
        return this;
    }

    public Gost addPoslaoZahtev(ZahtevZaPrijateljstvo zahtevZaPrijateljstvo) {
        this.poslaoZahtevs.add(zahtevZaPrijateljstvo);
        zahtevZaPrijateljstvo.setGost(this);
        return this;
    }

    public Gost removePoslaoZahtev(ZahtevZaPrijateljstvo zahtevZaPrijateljstvo) {
        this.poslaoZahtevs.remove(zahtevZaPrijateljstvo);
        zahtevZaPrijateljstvo.setGost(null);
        return this;
    }

    public void setPoslaoZahtevs(Set<ZahtevZaPrijateljstvo> zahtevZaPrijateljstvos) {
        this.poslaoZahtevs = zahtevZaPrijateljstvos;
    }

    public Set<Gost> getPrijateljis() {
        return prijateljis;
    }

    public Gost prijateljis(Set<Gost> gosts) {
        this.prijateljis = gosts;
        return this;
    }

    public Gost addPrijatelji(Gost gost) {
        this.prijateljis.add(gost);
        return this;
    }

    public Gost removePrijatelji(Gost gost) {
        this.prijateljis.remove(gost);
        return this;
    }

    public void setPrijateljis(Set<Gost> gosts) {
        this.prijateljis = gosts;
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
        Gost gost = (Gost) o;
        if (gost.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gost{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", email='" + getEmail() + "'" +
            ", lozinka='" + getLozinka() + "'" +
            "}";
    }
}
