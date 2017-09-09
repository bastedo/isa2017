package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Rezervacija.
 */
@Entity
@Table(name = "rezervacija")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rezervacija implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "ocenjeno")
    private Boolean ocenjeno;

    @Column(name = "potvrdjeno")
    private Boolean potvrdjeno;

    @ManyToOne
    private Stol stol;

    @OneToOne
    @JoinColumn(unique = true)
    private Porudzbina porudzbina;

    @ManyToOne
    private Restoran restoran;

    @ManyToOne
    private Gost rezervisao;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public Rezervacija startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public Rezervacija endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean isOcenjeno() {
        return ocenjeno;
    }

    public Rezervacija ocenjeno(Boolean ocenjeno) {
        this.ocenjeno = ocenjeno;
        return this;
    }

    public void setOcenjeno(Boolean ocenjeno) {
        this.ocenjeno = ocenjeno;
    }

    public Boolean isPotvrdjeno() {
        return potvrdjeno;
    }

    public Rezervacija potvrdjeno(Boolean potvrdjeno) {
        this.potvrdjeno = potvrdjeno;
        return this;
    }

    public void setPotvrdjeno(Boolean potvrdjeno) {
        this.potvrdjeno = potvrdjeno;
    }

    public Stol getStol() {
        return stol;
    }

    public Rezervacija stol(Stol stol) {
        this.stol = stol;
        return this;
    }

    public void setStol(Stol stol) {
        this.stol = stol;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public Rezervacija porudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
        return this;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public Rezervacija restoran(Restoran restoran) {
        this.restoran = restoran;
        return this;
    }

    public void setRestoran(Restoran restoran) {
        this.restoran = restoran;
    }

    public Gost getRezervisao() {
        return rezervisao;
    }

    public Rezervacija rezervisao(Gost gost) {
        this.rezervisao = gost;
        return this;
    }

    public void setRezervisao(Gost gost) {
        this.rezervisao = gost;
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
        Rezervacija rezervacija = (Rezervacija) o;
        if (rezervacija.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rezervacija.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rezervacija{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", ocenjeno='" + isOcenjeno() + "'" +
            ", potvrdjeno='" + isPotvrdjeno() + "'" +
            "}";
    }
}
