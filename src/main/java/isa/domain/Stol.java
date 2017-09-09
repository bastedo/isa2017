package isa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import isa.domain.enumeration.Segmenat;

/**
 * A Stol.
 */
@Entity
@Table(name = "stol")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "xpozicija_stola")
    private Integer xpozicijaStola;

    @Column(name = "ypozicija_stola")
    private Integer ypozicijaStola;

    @Enumerated(EnumType.STRING)
    @Column(name = "pripada_segmentu")
    private Segmenat pripadaSegmentu;

    @ManyToOne
    private KonfiguracijaStolova konfiguracijaStolova;

    @OneToMany(mappedBy = "stol")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Racun> zaStoloves = new HashSet<>();

    @OneToMany(mappedBy = "stol")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rezervacija> stols = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getXpozicijaStola() {
        return xpozicijaStola;
    }

    public Stol xpozicijaStola(Integer xpozicijaStola) {
        this.xpozicijaStola = xpozicijaStola;
        return this;
    }

    public void setXpozicijaStola(Integer xpozicijaStola) {
        this.xpozicijaStola = xpozicijaStola;
    }

    public Integer getYpozicijaStola() {
        return ypozicijaStola;
    }

    public Stol ypozicijaStola(Integer ypozicijaStola) {
        this.ypozicijaStola = ypozicijaStola;
        return this;
    }

    public void setYpozicijaStola(Integer ypozicijaStola) {
        this.ypozicijaStola = ypozicijaStola;
    }

    public Segmenat getPripadaSegmentu() {
        return pripadaSegmentu;
    }

    public Stol pripadaSegmentu(Segmenat pripadaSegmentu) {
        this.pripadaSegmentu = pripadaSegmentu;
        return this;
    }

    public void setPripadaSegmentu(Segmenat pripadaSegmentu) {
        this.pripadaSegmentu = pripadaSegmentu;
    }

    public KonfiguracijaStolova getKonfiguracijaStolova() {
        return konfiguracijaStolova;
    }

    public Stol konfiguracijaStolova(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijaStolova = konfiguracijaStolova;
        return this;
    }

    public void setKonfiguracijaStolova(KonfiguracijaStolova konfiguracijaStolova) {
        this.konfiguracijaStolova = konfiguracijaStolova;
    }

    public Set<Racun> getZaStoloves() {
        return zaStoloves;
    }

    public Stol zaStoloves(Set<Racun> racuns) {
        this.zaStoloves = racuns;
        return this;
    }

    public Stol addZaStolove(Racun racun) {
        this.zaStoloves.add(racun);
        racun.setStol(this);
        return this;
    }

    public Stol removeZaStolove(Racun racun) {
        this.zaStoloves.remove(racun);
        racun.setStol(null);
        return this;
    }

    public void setZaStoloves(Set<Racun> racuns) {
        this.zaStoloves = racuns;
    }

    public Set<Rezervacija> getStols() {
        return stols;
    }

    public Stol stols(Set<Rezervacija> rezervacijas) {
        this.stols = rezervacijas;
        return this;
    }

    public Stol addStol(Rezervacija rezervacija) {
        this.stols.add(rezervacija);
        rezervacija.setStol(this);
        return this;
    }

    public Stol removeStol(Rezervacija rezervacija) {
        this.stols.remove(rezervacija);
        rezervacija.setStol(null);
        return this;
    }

    public void setStols(Set<Rezervacija> rezervacijas) {
        this.stols = rezervacijas;
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
        Stol stol = (Stol) o;
        if (stol.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stol.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stol{" +
            "id=" + getId() +
            ", xpozicijaStola='" + getXpozicijaStola() + "'" +
            ", ypozicijaStola='" + getYpozicijaStola() + "'" +
            ", pripadaSegmentu='" + getPripadaSegmentu() + "'" +
            "}";
    }
}
