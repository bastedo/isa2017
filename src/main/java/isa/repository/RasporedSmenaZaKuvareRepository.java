package isa.repository;

import isa.domain.RasporedSmenaZaKuvare;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RasporedSmenaZaKuvare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RasporedSmenaZaKuvareRepository extends JpaRepository<RasporedSmenaZaKuvare, Long> {

}
