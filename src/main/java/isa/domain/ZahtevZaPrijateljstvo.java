package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ZahtevZaPrijateljstvo.
 */
@Entity
@Table(name = "zahtev_za_prijateljstvo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ZahtevZaPrijateljstvo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_podnosioca_zahteva")
    private Integer idPodnosiocaZahteva;

    @Column(name = "postalan_zahtev")
    private LocalDate postalanZahtev;

    @Column(name = "prihvacen_zahtev")
    private LocalDate prihvacenZahtev;

    @Column(name = "prihvacen")
    private Boolean prihvacen;

    @Column(name = "odbijen")
    private Boolean odbijen;

    @ManyToOne
    private Gost gost;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPodnosiocaZahteva() {
        return idPodnosiocaZahteva;
    }

    public ZahtevZaPrijateljstvo idPodnosiocaZahteva(Integer idPodnosiocaZahteva) {
        this.idPodnosiocaZahteva = idPodnosiocaZahteva;
        return this;
    }

    public void setIdPodnosiocaZahteva(Integer idPodnosiocaZahteva) {
        this.idPodnosiocaZahteva = idPodnosiocaZahteva;
    }

    public LocalDate getPostalanZahtev() {
        return postalanZahtev;
    }

    public ZahtevZaPrijateljstvo postalanZahtev(LocalDate postalanZahtev) {
        this.postalanZahtev = postalanZahtev;
        return this;
    }

    public void setPostalanZahtev(LocalDate postalanZahtev) {
        this.postalanZahtev = postalanZahtev;
    }

    public LocalDate getPrihvacenZahtev() {
        return prihvacenZahtev;
    }

    public ZahtevZaPrijateljstvo prihvacenZahtev(LocalDate prihvacenZahtev) {
        this.prihvacenZahtev = prihvacenZahtev;
        return this;
    }

    public void setPrihvacenZahtev(LocalDate prihvacenZahtev) {
        this.prihvacenZahtev = prihvacenZahtev;
    }

    public Boolean isPrihvacen() {
        return prihvacen;
    }

    public ZahtevZaPrijateljstvo prihvacen(Boolean prihvacen) {
        this.prihvacen = prihvacen;
        return this;
    }

    public void setPrihvacen(Boolean prihvacen) {
        this.prihvacen = prihvacen;
    }

    public Boolean isOdbijen() {
        return odbijen;
    }

    public ZahtevZaPrijateljstvo odbijen(Boolean odbijen) {
        this.odbijen = odbijen;
        return this;
    }

    public void setOdbijen(Boolean odbijen) {
        this.odbijen = odbijen;
    }

    public Gost getGost() {
        return gost;
    }

    public ZahtevZaPrijateljstvo gost(Gost gost) {
        this.gost = gost;
        return this;
    }

    public void setGost(Gost gost) {
        this.gost = gost;
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
        ZahtevZaPrijateljstvo zahtevZaPrijateljstvo = (ZahtevZaPrijateljstvo) o;
        if (zahtevZaPrijateljstvo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), zahtevZaPrijateljstvo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ZahtevZaPrijateljstvo{" +
            "id=" + getId() +
            ", idPodnosiocaZahteva='" + getIdPodnosiocaZahteva() + "'" +
            ", postalanZahtev='" + getPostalanZahtev() + "'" +
            ", prihvacenZahtev='" + getPrihvacenZahtev() + "'" +
            ", prihvacen='" + isPrihvacen() + "'" +
            ", odbijen='" + isOdbijen() + "'" +
            "}";
    }
}
