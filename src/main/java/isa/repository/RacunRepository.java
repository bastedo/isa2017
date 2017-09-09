package isa.repository;

import isa.domain.Racun;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Racun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacunRepository extends JpaRepository<Racun, Long> {

}
