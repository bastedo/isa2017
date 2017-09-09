package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Pice;

import isa.repository.PiceRepository;
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
 * REST controller for managing Pice.
 */
@RestController
@RequestMapping("/api")
public class PiceResource {

    private final Logger log = LoggerFactory.getLogger(PiceResource.class);

    private static final String ENTITY_NAME = "pice";

    private final PiceRepository piceRepository;
    public PiceResource(PiceRepository piceRepository) {
        this.piceRepository = piceRepository;
    }

    /**
     * POST  /pices : Create a new pice.
     *
     * @param pice the pice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pice, or with status 400 (Bad Request) if the pice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pices")
    @Timed
    public ResponseEntity<Pice> createPice(@RequestBody Pice pice) throws URISyntaxException {
        log.debug("REST request to save Pice : {}", pice);
        if (pice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pice cannot already have an ID")).body(null);
        }
        Pice result = piceRepository.save(pice);
        return ResponseEntity.created(new URI("/api/pices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pices : Updates an existing pice.
     *
     * @param pice the pice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pice,
     * or with status 400 (Bad Request) if the pice is not valid,
     * or with status 500 (Internal Server Error) if the pice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pices")
    @Timed
    public ResponseEntity<Pice> updatePice(@RequestBody Pice pice) throws URISyntaxException {
        log.debug("REST request to update Pice : {}", pice);
        if (pice.getId() == null) {
            return createPice(pice);
        }
        Pice result = piceRepository.save(pice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pices : get all the pices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pices in body
     */
    @GetMapping("/pices")
    @Timed
    public List<Pice> getAllPices() {
        log.debug("REST request to get all Pices");
        return piceRepository.findAll();
        }

    /**
     * GET  /pices/:id : get the "id" pice.
     *
     * @param id the id of the pice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pice, or with status 404 (Not Found)
     */
    @GetMapping("/pices/{id}")
    @Timed
    public ResponseEntity<Pice> getPice(@PathVariable Long id) {
        log.debug("REST request to get Pice : {}", id);
        Pice pice = piceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pice));
    }

    /**
     * DELETE  /pices/:id : delete the "id" pice.
     *
     * @param id the id of the pice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pices/{id}")
    @Timed
    public ResponseEntity<Void> deletePice(@PathVariable Long id) {
        log.debug("REST request to delete Pice : {}", id);
        piceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
