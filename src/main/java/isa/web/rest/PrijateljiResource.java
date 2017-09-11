package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Prijatelji;

import isa.repository.PrijateljiRepository;
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
 * REST controller for managing Prijatelji.
 */
@RestController
@RequestMapping("/api")
public class PrijateljiResource {

    private final Logger log = LoggerFactory.getLogger(PrijateljiResource.class);

    private static final String ENTITY_NAME = "prijatelji";

    private final PrijateljiRepository prijateljiRepository;
    public PrijateljiResource(PrijateljiRepository prijateljiRepository) {
        this.prijateljiRepository = prijateljiRepository;
    }

    /**
     * POST  /prijateljis : Create a new prijatelji.
     *
     * @param prijatelji the prijatelji to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prijatelji, or with status 400 (Bad Request) if the prijatelji has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prijateljis")
    @Timed
    public ResponseEntity<Prijatelji> createPrijatelji(@RequestBody Prijatelji prijatelji) throws URISyntaxException {
        log.debug("REST request to save Prijatelji : {}", prijatelji);
        if (prijatelji.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new prijatelji cannot already have an ID")).body(null);
        }
        Prijatelji result = prijateljiRepository.save(prijatelji);
        return ResponseEntity.created(new URI("/api/prijateljis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prijateljis : Updates an existing prijatelji.
     *
     * @param prijatelji the prijatelji to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prijatelji,
     * or with status 400 (Bad Request) if the prijatelji is not valid,
     * or with status 500 (Internal Server Error) if the prijatelji couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prijateljis")
    @Timed
    public ResponseEntity<Prijatelji> updatePrijatelji(@RequestBody Prijatelji prijatelji) throws URISyntaxException {
        log.debug("REST request to update Prijatelji : {}", prijatelji);
        if (prijatelji.getId() == null) {
            return createPrijatelji(prijatelji);
        }
        Prijatelji result = prijateljiRepository.save(prijatelji);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prijatelji.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prijateljis : get all the prijateljis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of prijateljis in body
     */
    @GetMapping("/prijateljis")
    @Timed
    public List<Prijatelji> getAllPrijateljis() {
        log.debug("REST request to get all Prijateljis");
        return prijateljiRepository.findAll();
        }

    /**
     * GET  /prijateljis/:id : get the "id" prijatelji.
     *
     * @param id the id of the prijatelji to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prijatelji, or with status 404 (Not Found)
     */
    @GetMapping("/prijateljis/{id}")
    @Timed
    public ResponseEntity<Prijatelji> getPrijatelji(@PathVariable Long id) {
        log.debug("REST request to get Prijatelji : {}", id);
        Prijatelji prijatelji = prijateljiRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(prijatelji));
    }

    /**
     * DELETE  /prijateljis/:id : delete the "id" prijatelji.
     *
     * @param id the id of the prijatelji to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prijateljis/{id}")
    @Timed
    public ResponseEntity<Void> deletePrijatelji(@PathVariable Long id) {
        log.debug("REST request to delete Prijatelji : {}", id);
        prijateljiRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
