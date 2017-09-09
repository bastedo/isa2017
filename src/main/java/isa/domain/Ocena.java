package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ocena.
 */
@Entity
@Table(name = "ocena")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ocena")
    private Double ocena;

    @OneToOne
    @JoinColumn(unique = true)
    private Gost gost;

    @ManyToOne
    private Restoran restoran;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOcena() {
        return ocena;
    }

    public Ocena ocena(Double ocena) {
        this.ocena = ocena;
        return this;
    }

    public void setOcena(Double ocena) {
        this.ocena = ocena;
    }

    public Gost getGost() {
        return gost;
    }

    public Ocena gost(Gost gost) {
        this.gost = gost;
        return this;
    }

    public void setGost(Gost gost) {
        this.gost = gost;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public Ocena restoran(Restoran restoran) {
        this.restoran = restoran;
        return this;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
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
        Ocena ocena = (Ocena) o;
        if (ocena.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ocena.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ocena{" +
            "id=" + getId() +
            ", ocena='" + getOcena() + "'" +
            "}";
    }
}
