package isa.repository;

import isa.domain.Restoran;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Restoran entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestoranRepository extends JpaRepository<Restoran, Long> {

}
