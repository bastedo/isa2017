package isa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import isa.domain.enumeration.VrstaSmene;

/**
 * A RasporedSmenaZaKonobare.
 */
@Entity
@Table(name = "raspored_smena_za_konobare")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RasporedSmenaZaKonobare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vrsta_smene")
    private VrstaSmene vrstaSmene;

    @Column(name = "jhi_start")
    private ZonedDateTime start;

    @Column(name = "jhi_end")
    private ZonedDateTime end;

    @ManyToOne
    private Zaposleni zaposleni;

    @ManyToOne
    private Restoran restoran;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VrstaSmene getVrstaSmene() {
        return vrstaSmene;
    }

    public RasporedSmenaZaKonobare vrstaSmene(VrstaSmene vrstaSmene) {
        this.vrstaSmene = vrstaSmene;
        return this;
    }

    public void setVrstaSmene(VrstaSmene vrstaSmene) {
        this.vrstaSmene = vrstaSmene;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public RasporedSmenaZaKonobare start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public RasporedSmenaZaKonobare end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public RasporedSmenaZaKonobare zaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
        return this;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    public Restoran getRestoran() {
        return restoran;
    }

    public RasporedSmenaZaKonobare restoran(Restoran restoran) {
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
        RasporedSmenaZaKonobare rasporedSmenaZaKonobare = (RasporedSmenaZaKonobare) o;
        if (rasporedSmenaZaKonobare.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rasporedSmenaZaKonobare.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RasporedSmenaZaKonobare{" +
            "id=" + getId() +
            ", vrstaSmene='" + getVrstaSmene() + "'" +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            "}";
    }
}
