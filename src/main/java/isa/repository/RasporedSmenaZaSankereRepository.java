package isa.repository;

import isa.domain.RasporedSmenaZaSankere;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RasporedSmenaZaSankere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RasporedSmenaZaSankereRepository extends JpaRepository<RasporedSmenaZaSankere, Long> {

}
