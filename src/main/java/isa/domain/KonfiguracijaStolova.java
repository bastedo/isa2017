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
 * A KonfiguracijaStolova.
 */
@Entity
@Table(name = "konfiguracija_stolova")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KonfiguracijaStolova implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime")
    private String ime;

    @ManyToOne
    private MenadzerRestorana menadzerRestorana;

    @OneToMany(mappedBy = "konfiguracijaStolova")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stol> stols = new HashSet<>();

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

    public KonfiguracijaStolova ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public MenadzerRestorana getMenadzerRestorana() {
        return menadzerRestorana;
    }

    public KonfiguracijaStolova menadzerRestorana(MenadzerRestorana menadzerRestorana) {
        this.menadzerRestorana = menadzerRestorana;
        return this;
    }

    public void setMenadzerRestorana(MenadzerRestorana menadzerRestorana) {
        this.menadzerRestorana = menadzerRestorana;
    }

    public Set<Stol> getStols() {
        return stols;
    }

    public KonfiguracijaStolova stols(Set<Stol> stols) {
        this.stols = stols;
        return this;
    }

    public KonfiguracijaStolova addStol(Stol stol) {
        this.stols.add(stol);
        stol.setKonfiguracijaStolova(this);
        return this;
    }

    public KonfiguracijaStolova removeStol(Stol stol) {
        this.stols.remove(stol);
        stol.setKonfiguracijaStolova(null);
        return this;
    }

    public void setStols(Set<Stol> stols) {
        this.stols = stols;
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
        KonfiguracijaStolova konfiguracijaStolova = (KonfiguracijaStolova) o;
        if (konfiguracijaStolova.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), konfiguracijaStolova.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KonfiguracijaStolova{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            "}";
    }
}
