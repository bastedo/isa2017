package isa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Porudzbina.
 */
@Entity
@Table(name = "porudzbina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Porudzbina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prihvaceno_pice")
    private Boolean prihvacenoPice;

    @Column(name = "spremno_pice")
    private Boolean spremnoPice;

    @Column(name = "prihvacena_hrana")
    private Boolean prihvacenaHrana;

    @Column(name = "spremna_hrana")
    private Boolean spremnaHrana;

    @ManyToOne
    private Jelo jelo;

    @ManyToOne
    private Pice pice;

    @OneToOne(mappedBy = "porudzbina")
    @JsonIgnore
    private Rezervacija zaRezervaciju;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPrihvacenoPice() {
        return prihvacenoPice;
    }

    public Porudzbina prihvacenoPice(Boolean prihvacenoPice) {
        this.prihvacenoPice = prihvacenoPice;
        return this;
    }

    public void setPrihvacenoPice(Boolean prihvacenoPice) {
        this.prihvacenoPice = prihvacenoPice;
    }

    public Boolean isSpremnoPice() {
        return spremnoPice;
    }

    public Porudzbina spremnoPice(Boolean spremnoPice) {
        this.spremnoPice = spremnoPice;
        return this;
    }

    public void setSpremnoPice(Boolean spremnoPice) {
        this.spremnoPice = spremnoPice;
    }

    public Boolean isPrihvacenaHrana() {
        return prihvacenaHrana;
    }

    public Porudzbina prihvacenaHrana(Boolean prihvacenaHrana) {
        this.prihvacenaHrana = prihvacenaHrana;
        return this;
    }

    public void setPrihvacenaHrana(Boolean prihvacenaHrana) {
        this.prihvacenaHrana = prihvacenaHrana;
    }

    public Boolean isSpremnaHrana() {
        return spremnaHrana;
    }

    public Porudzbina spremnaHrana(Boolean spremnaHrana) {
        this.spremnaHrana = spremnaHrana;
        return this;
    }

    public void setSpremnaHrana(Boolean spremnaHrana) {
        this.spremnaHrana = spremnaHrana;
    }

    public Jelo getJelo() {
        return jelo;
    }

    public Porudzbina jelo(Jelo jelo) {
        this.jelo = jelo;
        return this;
    }

    public void setJelo(Jelo jelo) {
        this.jelo = jelo;
    }

    public Pice getPice() {
        return pice;
    }

    public Porudzbina pice(Pice pice) {
        this.pice = pice;
        return this;
    }

    public void setPice(Pice pice) {
        this.pice = pice;
    }

    public Rezervacija getZaRezervaciju() {
        return zaRezervaciju;
    }

    public Porudzbina zaRezervaciju(Rezervacija rezervacija) {
        this.zaRezervaciju = rezervacija;
        return this;
    }

    public void setZaRezervaciju(Rezervacija rezervacija) {
        this.zaRezervaciju = rezervacija;
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
        Porudzbina porudzbina = (Porudzbina) o;
        if (porudzbina.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), porudzbina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Porudzbina{" +
            "id=" + getId() +
            ", prihvacenoPice='" + isPrihvacenoPice() + "'" +
            ", spremnoPice='" + isSpremnoPice() + "'" +
            ", prihvacenaHrana='" + isPrihvacenaHrana() + "'" +
            ", spremnaHrana='" + isSpremnaHrana() + "'" +
            "}";
    }
}
