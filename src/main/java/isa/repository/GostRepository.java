package isa.repository;

import isa.domain.Gost;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Gost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GostRepository extends JpaRepository<Gost, Long> {

}
