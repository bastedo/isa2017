package isa.repository;

import isa.domain.Ponudjac;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ponudjac entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PonudjacRepository extends JpaRepository<Ponudjac, Long> {

}
