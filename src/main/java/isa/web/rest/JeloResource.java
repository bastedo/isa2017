package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Jelo;

import isa.repository.JeloRepository;
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
 * REST controller for managing Jelo.
 */
@RestController
@RequestMapping("/api")
public class JeloResource {

    private final Logger log = LoggerFactory.getLogger(JeloResource.class);

    private static final String ENTITY_NAME = "jelo";

    private final JeloRepository jeloRepository;
    public JeloResource(JeloRepository jeloRepository) {
        this.jeloRepository = jeloRepository;
    }

    /**
     * POST  /jelos : Create a new jelo.
     *
     * @param jelo the jelo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jelo, or with status 400 (Bad Request) if the jelo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jelos")
    @Timed
    public ResponseEntity<Jelo> createJelo(@RequestBody Jelo jelo) throws URISyntaxException {
        log.debug("REST request to save Jelo : {}", jelo);
        if (jelo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new jelo cannot already have an ID")).body(null);
        }
        Jelo result = jeloRepository.save(jelo);
        return ResponseEntity.created(new URI("/api/jelos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jelos : Updates an existing jelo.
     *
     * @param jelo the jelo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jelo,
     * or with status 400 (Bad Request) if the jelo is not valid,
     * or with status 500 (Internal Server Error) if the jelo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jelos")
    @Timed
    public ResponseEntity<Jelo> updateJelo(@RequestBody Jelo jelo) throws URISyntaxException {
        log.debug("REST request to update Jelo : {}", jelo);
        if (jelo.getId() == null) {
            return createJelo(jelo);
        }
        Jelo result = jeloRepository.save(jelo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jelo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jelos : get all the jelos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jelos in body
     */
    @GetMapping("/jelos")
    @Timed
    public List<Jelo> getAllJelos() {
        log.debug("REST request to get all Jelos");
        return jeloRepository.findAll();
        }

    /**
     * GET  /jelos/:id : get the "id" jelo.
     *
     * @param id the id of the jelo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jelo, or with status 404 (Not Found)
     */
    @GetMapping("/jelos/{id}")
    @Timed
    public ResponseEntity<Jelo> getJelo(@PathVariable Long id) {
        log.debug("REST request to get Jelo : {}", id);
        Jelo jelo = jeloRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jelo));
    }

    /**
     * DELETE  /jelos/:id : delete the "id" jelo.
     *
     * @param id the id of the jelo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jelos/{id}")
    @Timed
    public ResponseEntity<Void> deleteJelo(@PathVariable Long id) {
        log.debug("REST request to delete Jelo : {}", id);
        jeloRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
