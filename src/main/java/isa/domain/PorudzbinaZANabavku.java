package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A PorudzbinaZANabavku.
 */
@Entity
@Table(name = "porudzbina_za_nabavku")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PorudzbinaZANabavku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "porudzbina")
    private String porudzbina;

    @Column(name = "prihvacena")
    private Boolean prihvacena;

    @Column(name = "vrednost")
    private Double vrednost;

    @Column(name = "dostava")
    private ZonedDateTime dostava;

    @ManyToOne
    private PozivZaPrikupljanjeN pozivZaPrikupljanjeN;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPorudzbina() {
        return porudzbina;
    }

    public PorudzbinaZANabavku porudzbina(String porudzbina) {
        this.porudzbina = porudzbina;
        return this;
    }

    public void setPorudzbina(String porudzbina) {
        this.porudzbina = porudzbina;
    }

    public Boolean isPrihvacena() {
        return prihvacena;
    }

    public PorudzbinaZANabavku prihvacena(Boolean prihvacena) {
        this.prihvacena = prihvacena;
        return this;
    }

    public void setPrihvacena(Boolean prihvacena) {
        this.prihvacena = prihvacena;
    }

    public Double getVrednost() {
        return vrednost;
    }

    public PorudzbinaZANabavku vrednost(Double vrednost) {
        this.vrednost = vrednost;
        return this;
    }

    public void setVrednost(Double vrednost) {
        this.vrednost = vrednost;
    }

    public ZonedDateTime getDostava() {
        return dostava;
    }

    public PorudzbinaZANabavku dostava(ZonedDateTime dostava) {
        this.dostava = dostava;
        return this;
    }

    public void setDostava(ZonedDateTime dostava) {
        this.dostava = dostava;
    }

    public PozivZaPrikupljanjeN getPozivZaPrikupljanjeN() {
        return pozivZaPrikupljanjeN;
    }

    public PorudzbinaZANabavku pozivZaPrikupljanjeN(PozivZaPrikupljanjeN pozivZaPrikupljanjeN) {
        this.pozivZaPrikupljanjeN = pozivZaPrikupljanjeN;
        return this;
    }

    public void setPozivZaPrikupljanjeN(PozivZaPrikupljanjeN pozivZaPrikupljanjeN) {
        this.pozivZaPrikupljanjeN = pozivZaPrikupljanjeN;
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
        PorudzbinaZANabavku porudzbinaZANabavku = (PorudzbinaZANabavku) o;
        if (porudzbinaZANabavku.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), porudzbinaZANabavku.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PorudzbinaZANabavku{" +
            "id=" + getId() +
            ", porudzbina='" + getPorudzbina() + "'" +
            ", prihvacena='" + isPrihvacena() + "'" +
            ", vrednost='" + getVrednost() + "'" +
            ", dostava='" + getDostava() + "'" +
            "}";
    }
}
