package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Stol;

import isa.repository.StolRepository;
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
 * REST controller for managing Stol.
 */
@RestController
@RequestMapping("/api")
public class StolResource {

    private final Logger log = LoggerFactory.getLogger(StolResource.class);

    private static final String ENTITY_NAME = "stol";

    private final StolRepository stolRepository;
    public StolResource(StolRepository stolRepository) {
        this.stolRepository = stolRepository;
    }

    /**
     * POST  /stols : Create a new stol.
     *
     * @param stol the stol to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stol, or with status 400 (Bad Request) if the stol has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stols")
    @Timed
    public ResponseEntity<Stol> createStol(@RequestBody Stol stol) throws URISyntaxException {
        log.debug("REST request to save Stol : {}", stol);
        if (stol.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new stol cannot already have an ID")).body(null);
        }
        Stol result = stolRepository.save(stol);
        return ResponseEntity.created(new URI("/api/stols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stols : Updates an existing stol.
     *
     * @param stol the stol to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stol,
     * or with status 400 (Bad Request) if the stol is not valid,
     * or with status 500 (Internal Server Error) if the stol couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stols")
    @Timed
    public ResponseEntity<Stol> updateStol(@RequestBody Stol stol) throws URISyntaxException {
        log.debug("REST request to update Stol : {}", stol);
        if (stol.getId() == null) {
            return createStol(stol);
        }
        Stol result = stolRepository.save(stol);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stol.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stols : get all the stols.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stols in body
     */
    @GetMapping("/stols")
    @Timed
    public List<Stol> getAllStols() {
        log.debug("REST request to get all Stols");
        return stolRepository.findAll();
        }

    /**
     * GET  /stols/:id : get the "id" stol.
     *
     * @param id the id of the stol to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stol, or with status 404 (Not Found)
     */
    @GetMapping("/stols/{id}")
    @Timed
    public ResponseEntity<Stol> getStol(@PathVariable Long id) {
        log.debug("REST request to get Stol : {}", id);
        Stol stol = stolRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stol));
    }

    /**
     * DELETE  /stols/:id : delete the "id" stol.
     *
     * @param id the id of the stol to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stols/{id}")
    @Timed
    public ResponseEntity<Void> deleteStol(@PathVariable Long id) {
        log.debug("REST request to delete Stol : {}", id);
        stolRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
