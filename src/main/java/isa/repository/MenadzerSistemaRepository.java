package isa.repository;

import isa.domain.MenadzerSistema;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MenadzerSistema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenadzerSistemaRepository extends JpaRepository<MenadzerSistema, Long> {

}
