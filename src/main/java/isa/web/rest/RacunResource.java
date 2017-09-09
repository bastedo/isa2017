package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Racun;

import isa.repository.RacunRepository;
import isa.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Racun.
 */
@RestController
@RequestMapping("/api")
public class RacunResource {

    private final Logger log = LoggerFactory.getLogger(RacunResource.class);

    private static final String ENTITY_NAME = "racun";

    private final RacunRepository racunRepository;
    public RacunResource(RacunRepository racunRepository) {
        this.racunRepository = racunRepository;
    }

    /**
     * POST  /racuns : Create a new racun.
     *
     * @param racun the racun to create
     * @return the ResponseEntity with status 201 (Created) and with body the new racun, or with status 400 (Bad Request) if the racun has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/racuns")
    @Timed
    public ResponseEntity<Racun> createRacun(@RequestBody Racun racun) throws URISyntaxException {
        log.debug("REST request to save Racun : {}", racun);
        if (racun.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new racun cannot already have an ID")).body(null);
        }
        Racun result = racunRepository.save(racun);
        return ResponseEntity.created(new URI("/api/racuns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /racuns : Updates an existing racun.
     *
     * @param racun the racun to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated racun,
     * or with status 400 (Bad Request) if the racun is not valid,
     * or with status 500 (Internal Server Error) if the racun couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/racuns")
    @Timed
    public ResponseEntity<Racun> updateRacun(@RequestBody Racun racun) throws URISyntaxException {
        log.debug("REST request to update Racun : {}", racun);
        if (racun.getId() == null) {
            return createRacun(racun);
        }
        Racun result = racunRepository.save(racun);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, racun.getId().toString()))
            .body(result);
    }

    /**
     * GET  /racuns : get all the racuns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of racuns in body
     */
    @GetMapping("/racuns")
    @Timed
    public List<Racun> getAllRacuns() {
        log.debug("REST request to get all Racuns");
        return racunRepository.findAll();
        }

    /**
     * GET  /racuns/:id : get the "id" racun.
     *
     * @param id the id of the racun to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the racun, or with status 404 (Not Found)
     */
    @GetMapping("/racuns/{id}")
    @Timed
    public ResponseEntity<Racun> getRacun(@PathVariable Long id) {
        log.debug("REST request to get Racun : {}", id);
        Racun racun = racunRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(racun));
    }

    /**
     * DELETE  /racuns/:id : delete the "id" racun.
     *
     * @param id the id of the racun to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/racuns/{id}")
    @Timed
    public ResponseEntity<Void> deleteRacun(@PathVariable Long id) {
        log.debug("REST request to delete Racun : {}", id);
        racunRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
