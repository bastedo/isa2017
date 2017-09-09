package isa.repository;

import isa.domain.KonfiguracijaStolova;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the KonfiguracijaStolova entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KonfiguracijaStolovaRepository extends JpaRepository<KonfiguracijaStolova, Long> {

}
