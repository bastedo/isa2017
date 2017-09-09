package isa.repository;

import isa.domain.Pice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PiceRepository extends JpaRepository<Pice, Long> {

}
