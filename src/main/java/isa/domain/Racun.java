package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Racun.
 */
@Entity
@Table(name = "racun")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cena")
    private Double cena;

    @Column(name = "datum")
    private ZonedDateTime datum;

    @ManyToOne
    private Zaposleni naplatio;

    @ManyToOne
    private Stol stol;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCena() {
        return cena;
    }

    public Racun cena(Double cena) {
        this.cena = cena;
        return this;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public ZonedDateTime getDatum() {
        return datum;
    }

    public Racun datum(ZonedDateTime datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(ZonedDateTime datum) {
        this.datum = datum;
    }

    public Zaposleni getNaplatio() {
        return naplatio;
    }

    public Racun naplatio(Zaposleni zaposleni) {
        this.naplatio = zaposleni;
        return this;
    }

    public void setNaplatio(Zaposleni zaposleni) {
        this.naplatio = zaposleni;
    }

    public Stol getStol() {
        return stol;
    }

    public Racun stol(Stol stol) {
        this.stol = stol;
        return this;
    }

    public void setStol(Stol stol) {
        this.stol = stol;
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
        Racun racun = (Racun) o;
        if (racun.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), racun.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Racun{" +
            "id=" + getId() +
            ", cena='" + getCena() + "'" +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
