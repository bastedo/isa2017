package isa.repository;

import isa.domain.Jelo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Jelo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JeloRepository extends JpaRepository<Jelo, Long> {

}
