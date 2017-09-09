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
 * A KartaPica.
 */
@Entity
@Table(name = "karta_pica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KartaPica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime_karte_pica")
    private String imeKartePica;

    @OneToMany(mappedBy = "kartaPica")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pice> pices = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImeKartePica() {
        return imeKartePica;
    }

    public KartaPica imeKartePica(String imeKartePica) {
        this.imeKartePica = imeKartePica;
        return this;
    }

    public void setImeKartePica(String imeKartePica) {
        this.imeKartePica = imeKartePica;
    }

    public Set<Pice> getPices() {
        return pices;
    }

    public KartaPica pices(Set<Pice> pices) {
        this.pices = pices;
        return this;
    }

    public KartaPica addPice(Pice pice) {
        this.pices.add(pice);
        pice.setKartaPica(this);
        return this;
    }

    public KartaPica removePice(Pice pice) {
        this.pices.remove(pice);
        pice.setKartaPica(null);
        return this;
    }

    public void setPices(Set<Pice> pices) {
        this.pices = pices;
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
        KartaPica kartaPica = (KartaPica) o;
        if (kartaPica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kartaPica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KartaPica{" +
            "id=" + getId() +
            ", imeKartePica='" + getImeKartePica() + "'" +
            "}";
    }
}
