package isa.repository;

import isa.domain.Stol;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Stol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StolRepository extends JpaRepository<Stol, Long> {

}
