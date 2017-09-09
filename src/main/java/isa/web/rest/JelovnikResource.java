package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Jelovnik;

import isa.repository.JelovnikRepository;
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
 * REST controller for managing Jelovnik.
 */
@RestController
@RequestMapping("/api")
public class JelovnikResource {

    private final Logger log = LoggerFactory.getLogger(JelovnikResource.class);

    private static final String ENTITY_NAME = "jelovnik";

    private final JelovnikRepository jelovnikRepository;
    public JelovnikResource(JelovnikRepository jelovnikRepository) {
        this.jelovnikRepository = jelovnikRepository;
    }

    /**
     * POST  /jelovniks : Create a new jelovnik.
     *
     * @param jelovnik the jelovnik to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jelovnik, or with status 400 (Bad Request) if the jelovnik has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jelovniks")
    @Timed
    public ResponseEntity<Jelovnik> createJelovnik(@RequestBody Jelovnik jelovnik) throws URISyntaxException {
        log.debug("REST request to save Jelovnik : {}", jelovnik);
        if (jelovnik.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jelovnik cannot already have an ID")).body(null);
        }
        Jelovnik result = jelovnikRepository.save(jelovnik);
        return ResponseEntity.created(new URI("/api/jelovniks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jelovniks : Updates an existing jelovnik.
     *
     * @param jelovnik the jelovnik to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jelovnik,
     * or with status 400 (Bad Request) if the jelovnik is not valid,
     * or with status 500 (Internal Server Error) if the jelovnik couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jelovniks")
    @Timed
    public ResponseEntity<Jelovnik> updateJelovnik(@RequestBody Jelovnik jelovnik) throws URISyntaxException {
        log.debug("REST request to update Jelovnik : {}", jelovnik);
        if (jelovnik.getId() == null) {
            return createJelovnik(jelovnik);
        }
        Jelovnik result = jelovnikRepository.save(jelovnik);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jelovnik.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jelovniks : get all the jelovniks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jelovniks in body
     */
    @GetMapping("/jelovniks")
    @Timed
    public List<Jelovnik> getAllJelovniks() {
        log.debug("REST request to get all Jelovniks");
        return jelovnikRepository.findAll();
        }

    /**
     * GET  /jelovniks/:id : get the "id" jelovnik.
     *
     * @param id the id of the jelovnik to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jelovnik, or with status 404 (Not Found)
     */
    @GetMapping("/jelovniks/{id}")
    @Timed
    public ResponseEntity<Jelovnik> getJelovnik(@PathVariable Long id) {
        log.debug("REST request to get Jelovnik : {}", id);
        Jelovnik jelovnik = jelovnikRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jelovnik));
    }

    /**
     * DELETE  /jelovniks/:id : delete the "id" jelovnik.
     *
     * @param id the id of the jelovnik to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jelovniks/{id}")
    @Timed
    public ResponseEntity<Void> deleteJelovnik(@PathVariable Long id) {
        log.debug("REST request to delete Jelovnik : {}", id);
        jelovnikRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
