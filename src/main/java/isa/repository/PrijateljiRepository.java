package isa.repository;

import isa.domain.Prijatelji;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Prijatelji entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrijateljiRepository extends JpaRepository<Prijatelji, Long> {

}
