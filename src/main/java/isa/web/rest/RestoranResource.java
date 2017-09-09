package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Restoran;

import isa.repository.RestoranRepository;
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
 * REST controller for managing Restoran.
 */
@RestController
@RequestMapping("/api")
public class RestoranResource {

    private final Logger log = LoggerFactory.getLogger(RestoranResource.class);

    private static final String ENTITY_NAME = "restoran";

    private final RestoranRepository restoranRepository;
    public RestoranResource(RestoranRepository restoranRepository) {
        this.restoranRepository = restoranRepository;
    }

    /**
     * POST  /restorans : Create a new restoran.
     *
     * @param restoran the restoran to create
     * @return the ResponseEntity with status 201 (Created) and with body the new restoran, or with status 400 (Bad Request) if the restoran has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/restorans")
    @Timed
    public ResponseEntity<Restoran> createRestoran(@RequestBody Restoran restoran) throws URISyntaxException {
        log.debug("REST request to save Restoran : {}", restoran);
        if (restoran.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new restoran cannot already have an ID")).body(null);
        }
        Restoran result = restoranRepository.save(restoran);
        return ResponseEntity.created(new URI("/api/restorans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /restorans : Updates an existing restoran.
     *
     * @param restoran the restoran to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated restoran,
     * or with status 400 (Bad Request) if the restoran is not valid,
     * or with status 500 (Internal Server Error) if the restoran couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/restorans")
    @Timed
    public ResponseEntity<Restoran> updateRestoran(@RequestBody Restoran restoran) throws URISyntaxException {
        log.debug("REST request to update Restoran : {}", restoran);
        if (restoran.getId() == null) {
            return createRestoran(restoran);
        }
        Restoran result = restoranRepository.save(restoran);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, restoran.getId().toString()))
            .body(result);
    }

    /**
     * GET  /restorans : get all the restorans.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of restorans in body
     */
    @GetMapping("/restorans")
    @Timed
    public List<Restoran> getAllRestorans() {
        log.debug("REST request to get all Restorans");
        return restoranRepository.findAll();
        }

    /**
     * GET  /restorans/:id : get the "id" restoran.
     *
     * @param id the id of the restoran to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the restoran, or with status 404 (Not Found)
     */
    @GetMapping("/restorans/{id}")
    @Timed
    public ResponseEntity<Restoran> getRestoran(@PathVariable Long id) {
        log.debug("REST request to get Restoran : {}", id);
        Restoran restoran = restoranRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(restoran));
    }

    /**
     * DELETE  /restorans/:id : delete the "id" restoran.
     *
     * @param id the id of the restoran to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/restorans/{id}")
    @Timed
    public ResponseEntity<Void> deleteRestoran(@PathVariable Long id) {
        log.debug("REST request to delete Restoran : {}", id);
        restoranRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
