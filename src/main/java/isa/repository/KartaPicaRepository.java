package isa.repository;

import isa.domain.KartaPica;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KartaPica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KartaPicaRepository extends JpaRepository<KartaPica, Long> {

}
