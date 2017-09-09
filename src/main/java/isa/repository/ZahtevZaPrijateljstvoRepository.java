package isa.repository;

import isa.domain.ZahtevZaPrijateljstvo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ZahtevZaPrijateljstvo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZahtevZaPrijateljstvoRepository extends JpaRepository<ZahtevZaPrijateljstvo, Long> {

}
