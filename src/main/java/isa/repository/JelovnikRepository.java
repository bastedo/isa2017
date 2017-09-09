package isa.repository;

import isa.domain.Jelovnik;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Jelovnik entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JelovnikRepository extends JpaRepository<Jelovnik, Long> {

}
