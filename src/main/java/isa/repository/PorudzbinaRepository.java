package isa.repository;

import isa.domain.Porudzbina;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Porudzbina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Long> {

}
