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
 * A Pice.
 */
@Entity
@Table(name = "pice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ime_jela")
    private String imeJela;

    @Lob
    @Column(name = "kratki_tekst")
    private String kratkiTekst;

    @Column(name = "cena")
    private Double cena;

    @ManyToOne
    private KartaPica kartaPica;

    @OneToMany(mappedBy = "pice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Porudzbina> pices = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImeJela() {
        return imeJela;
    }

    public Pice imeJela(String imeJela) {
        this.imeJela = imeJela;
        return this;
    }

    public void setImeJela(String imeJela) {
        this.imeJela = imeJela;
    }

    public String getKratkiTekst() {
        return kratkiTekst;
    }

    public Pice kratkiTekst(String kratkiTekst) {
        this.kratkiTekst = kratkiTekst;
        return this;
    }

    public void setKratkiTekst(String kratkiTekst) {
        this.kratkiTekst = kratkiTekst;
    }

    public Double getCena() {
        return cena;
    }

    public Pice cena(Double cena) {
        this.cena = cena;
        return this;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public KartaPica getKartaPica() {
        return kartaPica;
    }

    public Pice kartaPica(KartaPica kartaPica) {
        this.kartaPica = kartaPica;
        return this;
    }

    public void setKartaPica(KartaPica kartaPica) {
        this.kartaPica = kartaPica;
    }

    public Set<Porudzbina> getPices() {
        return pices;
    }

    public Pice pices(Set<Porudzbina> porudzbinas) {
        this.pices = porudzbinas;
        return this;
    }

    public Pice addPice(Porudzbina porudzbina) {
        this.pices.add(porudzbina);
        porudzbina.setPice(this);
        return this;
    }

    public Pice removePice(Porudzbina porudzbina) {
        this.pices.remove(porudzbina);
        porudzbina.setPice(null);
        return this;
    }

    public void setPices(Set<Porudzbina> porudzbinas) {
        this.pices = porudzbinas;
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
        Pice pice = (Pice) o;
        if (pice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pice{" +
            "id=" + getId() +
            ", imeJela='" + getImeJela() + "'" +
            ", kratkiTekst='" + getKratkiTekst() + "'" +
            ", cena='" + getCena() + "'" +
            "}";
    }
}
