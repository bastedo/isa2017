package isa.repository;

import isa.domain.PorudzbinaZANabavku;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PorudzbinaZANabavku entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PorudzbinaZANabavkuRepository extends JpaRepository<PorudzbinaZANabavku, Long> {

}
