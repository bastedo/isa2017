package isa.repository;

import isa.domain.Zaposleni;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Zaposleni entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZaposleniRepository extends JpaRepository<Zaposleni, Long> {

}
