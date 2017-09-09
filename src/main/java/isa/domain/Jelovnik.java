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
 * A Jelovnik.
 */
@Entity
@Table(name = "jelovnik")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jelovnik implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime_jelovnika")
    private String imeJelovnika;

    @OneToMany(mappedBy = "jelovnik")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Jelo> jelos = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImeJelovnika() {
        return imeJelovnika;
    }

    public Jelovnik imeJelovnika(String imeJelovnika) {
        this.imeJelovnika = imeJelovnika;
        return this;
    }

    public void setImeJelovnika(String imeJelovnika) {
        this.imeJelovnika = imeJelovnika;
    }

    public Set<Jelo> getJelos() {
        return jelos;
    }

    public Jelovnik jelos(Set<Jelo> jelos) {
        this.jelos = jelos;
        return this;
    }

    public Jelovnik addJelo(Jelo jelo) {
        this.jelos.add(jelo);
        jelo.setJelovnik(this);
        return this;
    }

    public Jelovnik removeJelo(Jelo jelo) {
        this.jelos.remove(jelo);
        jelo.setJelovnik(null);
        return this;
    }

    public void setJelos(Set<Jelo> jelos) {
        this.jelos = jelos;
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
        Jelovnik jelovnik = (Jelovnik) o;
        if (jelovnik.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jelovnik.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jelovnik{" +
            "id=" + getId() +
            ", imeJelovnika='" + getImeJelovnika() + "'" +
            "}";
    }
}
