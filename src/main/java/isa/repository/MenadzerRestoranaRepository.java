package isa.repository;

import isa.domain.MenadzerRestorana;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MenadzerRestorana entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenadzerRestoranaRepository extends JpaRepository<MenadzerRestorana, Long> {

}
