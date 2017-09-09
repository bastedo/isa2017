package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.PozivZaPrikupljanjeN;

import isa.repository.PozivZaPrikupljanjeNRepository;
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
 * REST controller for managing PozivZaPrikupljanjeN.
 */
@RestController
@RequestMapping("/api")
public class PozivZaPrikupljanjeNResource {

    private final Logger log = LoggerFactory.getLogger(PozivZaPrikupljanjeNResource.class);

    private static final String ENTITY_NAME = "pozivZaPrikupljanjeN";

    private final PozivZaPrikupljanjeNRepository pozivZaPrikupljanjeNRepository;
    public PozivZaPrikupljanjeNResource(PozivZaPrikupljanjeNRepository pozivZaPrikupljanjeNRepository) {
        this.pozivZaPrikupljanjeNRepository = pozivZaPrikupljanjeNRepository;
    }

    /**
     * POST  /poziv-za-prikupljanje-ns : Create a new pozivZaPrikupljanjeN.
     *
     * @param pozivZaPrikupljanjeN the pozivZaPrikupljanjeN to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pozivZaPrikupljanjeN, or with status 400 (Bad Request) if the pozivZaPrikupljanjeN has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/poziv-za-prikupljanje-ns")
    @Timed
    public ResponseEntity<PozivZaPrikupljanjeN> createPozivZaPrikupljanjeN(@RequestBody PozivZaPrikupljanjeN pozivZaPrikupljanjeN) throws URISyntaxException {
        log.debug("REST request to save PozivZaPrikupljanjeN : {}", pozivZaPrikupljanjeN);
        if (pozivZaPrikupljanjeN.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pozivZaPrikupljanjeN cannot already have an ID")).body(null);
        }
        PozivZaPrikupljanjeN result = pozivZaPrikupljanjeNRepository.save(pozivZaPrikupljanjeN);
        return ResponseEntity.created(new URI("/api/poziv-za-prikupljanje-ns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /poziv-za-prikupljanje-ns : Updates an existing pozivZaPrikupljanjeN.
     *
     * @param pozivZaPrikupljanjeN the pozivZaPrikupljanjeN to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pozivZaPrikupljanjeN,
     * or with status 400 (Bad Request) if the pozivZaPrikupljanjeN is not valid,
     * or with status 500 (Internal Server Error) if the pozivZaPrikupljanjeN couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/poziv-za-prikupljanje-ns")
    @Timed
    public ResponseEntity<PozivZaPrikupljanjeN> updatePozivZaPrikupljanjeN(@RequestBody PozivZaPrikupljanjeN pozivZaPrikupljanjeN) throws URISyntaxException {
        log.debug("REST request to update PozivZaPrikupljanjeN : {}", pozivZaPrikupljanjeN);
        if (pozivZaPrikupljanjeN.getId() == null) {
            return createPozivZaPrikupljanjeN(pozivZaPrikupljanjeN);
        }
        PozivZaPrikupljanjeN result = pozivZaPrikupljanjeNRepository.save(pozivZaPrikupljanjeN);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pozivZaPrikupljanjeN.getId().toString()))
            .body(result);
    }

    /**
     * GET  /poziv-za-prikupljanje-ns : get all the pozivZaPrikupljanjeNS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pozivZaPrikupljanjeNS in body
     */
    @GetMapping("/poziv-za-prikupljanje-ns")
    @Timed
    public List<PozivZaPrikupljanjeN> getAllPozivZaPrikupljanjeNS() {
        log.debug("REST request to get all PozivZaPrikupljanjeNS");
        return pozivZaPrikupljanjeNRepository.findAll();
        }

    /**
     * GET  /poziv-za-prikupljanje-ns/:id : get the "id" pozivZaPrikupljanjeN.
     *
     * @param id the id of the pozivZaPrikupljanjeN to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pozivZaPrikupljanjeN, or with status 404 (Not Found)
     */
    @GetMapping("/poziv-za-prikupljanje-ns/{id}")
    @Timed
    public ResponseEntity<PozivZaPrikupljanjeN> getPozivZaPrikupljanjeN(@PathVariable Long id) {
        log.debug("REST request to get PozivZaPrikupljanjeN : {}", id);
        PozivZaPrikupljanjeN pozivZaPrikupljanjeN = pozivZaPrikupljanjeNRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pozivZaPrikupljanjeN));
    }

    /**
     * DELETE  /poziv-za-prikupljanje-ns/:id : delete the "id" pozivZaPrikupljanjeN.
     *
     * @param id the id of the pozivZaPrikupljanjeN to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/poziv-za-prikupljanje-ns/{id}")
    @Timed
    public ResponseEntity<Void> deletePozivZaPrikupljanjeN(@PathVariable Long id) {
        log.debug("REST request to delete PozivZaPrikupljanjeN : {}", id);
        pozivZaPrikupljanjeNRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
