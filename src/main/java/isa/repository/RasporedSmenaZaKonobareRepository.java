package isa.repository;

import isa.domain.RasporedSmenaZaKonobare;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RasporedSmenaZaKonobare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RasporedSmenaZaKonobareRepository extends JpaRepository<RasporedSmenaZaKonobare, Long> {

}
