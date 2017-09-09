package isa.repository;

import isa.domain.PozivZaPrikupljanjeN;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PozivZaPrikupljanjeN entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PozivZaPrikupljanjeNRepository extends JpaRepository<PozivZaPrikupljanjeN, Long> {

}
