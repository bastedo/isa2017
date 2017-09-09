package isa.repository;

import isa.domain.Ocena;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ocena entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcenaRepository extends JpaRepository<Ocena, Long> {

}
