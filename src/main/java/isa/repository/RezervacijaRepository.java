package isa.repository;

import isa.domain.Rezervacija;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rezervacija entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {

}
