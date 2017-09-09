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
 * A Jelo.
 */
@Entity
@Table(name = "jelo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Jelo implements Serializable {

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
    private Jelovnik jelovnik;

    @OneToMany(mappedBy = "jelo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Porudzbina> jelos = new HashSet<>();

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

    public Jelo imeJela(String imeJela) {
        this.imeJela = imeJela;
        return this;
    }

    public void setImeJela(String imeJela) {
        this.imeJela = imeJela;
    }

    public String getKratkiTekst() {
        return kratkiTekst;
    }

    public Jelo kratkiTekst(String kratkiTekst) {
        this.kratkiTekst = kratkiTekst;
        return this;
    }

    public void setKratkiTekst(String kratkiTekst) {
        this.kratkiTekst = kratkiTekst;
    }

    public Double getCena() {
        return cena;
    }

    public Jelo cena(Double cena) {
        this.cena = cena;
        return this;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Jelovnik getJelovnik() {
        return jelovnik;
    }

    public Jelo jelovnik(Jelovnik jelovnik) {
        this.jelovnik = jelovnik;
        return this;
    }

    public void setJelovnik(Jelovnik jelovnik) {
        this.jelovnik = jelovnik;
    }

    public Set<Porudzbina> getJelos() {
        return jelos;
    }

    public Jelo jelos(Set<Porudzbina> porudzbinas) {
        this.jelos = porudzbinas;
        return this;
    }

    public Jelo addJelo(Porudzbina porudzbina) {
        this.jelos.add(porudzbina);
        porudzbina.setJelo(this);
        return this;
    }

    public Jelo removeJelo(Porudzbina porudzbina) {
        this.jelos.remove(porudzbina);
        porudzbina.setJelo(null);
        return this;
    }

    public void setJelos(Set<Porudzbina> porudzbinas) {
        this.jelos = porudzbinas;
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
        Jelo jelo = (Jelo) o;
        if (jelo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jelo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Jelo{" +
            "id=" + getId() +
            ", imeJela='" + getImeJela() + "'" +
            ", kratkiTekst='" + getKratkiTekst() + "'" +
            ", cena='" + getCena() + "'" +
            "}";
    }
}
