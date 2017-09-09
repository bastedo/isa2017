package isa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PozivZaPrikupljanjeN.
 */
@Entity
@Table(name = "poziv_za_prikupljanje_n")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PozivZaPrikupljanjeN implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "spisak_potreba")
    private String spisakPotreba;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @ManyToOne
    private Restoran restoran;

    @OneToMany(mappedBy = "pozivZaPrikupljanjeN")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PorudzbinaZANabavku> porudzbinaZaNabavkus = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpisakPotreba() {
        return spisakPotreba;
    }

    public PozivZaPrikupljanjeN spisakPotreba(String spisakPotreba) {
        this.spisakPotreba = spisakPotreba;
        return this;
    }

    public void setSpisakPotreba(String spisakPotreba) {
        this.spisakPotreba = spisakPotreba;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public PozivZaPrikupljanjeN startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public PozivZaPrikupljanjeN endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public PozivZaPrikupljanjeN restoran(Restoran restoran) {
        this.restoran = restoran;
        return this;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public Set<PorudzbinaZANabavku> getPorudzbinaZaNabavkus() {
        return porudzbinaZaNabavkus;
    }

    public PozivZaPrikupljanjeN porudzbinaZaNabavkus(Set<PorudzbinaZANabavku> porudzbinaZANabavkus) {
        this.porudzbinaZaNabavkus = porudzbinaZANabavkus;
        return this;
    }

    public PozivZaPrikupljanjeN addPorudzbinaZaNabavku(PorudzbinaZANabavku porudzbinaZANabavku) {
        this.porudzbinaZaNabavkus.add(porudzbinaZANabavku);
        porudzbinaZANabavku.setPozivZaPrikupljanjeN(this);
        return this;
    }

    public PozivZaPrikupljanjeN removePorudzbinaZaNabavku(PorudzbinaZANabavku porudzbinaZANabavku) {
        this.porudzbinaZaNabavkus.remove(porudzbinaZANabavku);
        porudzbinaZANabavku.setPozivZaPrikupljanjeN(null);
        return this;
    }

    public void setPorudzbinaZaNabavkus(Set<PorudzbinaZANabavku> porudzbinaZANabavkus) {
        this.porudzbinaZaNabavkus = porudzbinaZANabavkus;
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
        PozivZaPrikupljanjeN pozivZaPrikupljanjeN = (PozivZaPrikupljanjeN) o;
        if (pozivZaPrikupljanjeN.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pozivZaPrikupljanjeN.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PozivZaPrikupljanjeN{" +
            "id=" + getId() +
            ", spisakPotreba='" + getSpisakPotreba() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
