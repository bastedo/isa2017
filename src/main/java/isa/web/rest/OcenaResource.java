package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Ocena;

import isa.repository.OcenaRepository;
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
 * REST controller for managing Ocena.
 */
@RestController
@RequestMapping("/api")
public class OcenaResource {

    private final Logger log = LoggerFactory.getLogger(OcenaResource.class);

    private static final String ENTITY_NAME = "ocena";

    private final OcenaRepository ocenaRepository;
    public OcenaResource(OcenaRepository ocenaRepository) {
        this.ocenaRepository = ocenaRepository;
    }

    /**
     * POST  /ocenas : Create a new ocena.
     *
     * @param ocena the ocena to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ocena, or with status 400 (Bad Request) if the ocena has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ocenas")
    @Timed
    public ResponseEntity<Ocena> createOcena(@RequestBody Ocena ocena) throws URISyntaxException {
        log.debug("REST request to save Ocena : {}", ocena);
        if (ocena.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ocena cannot already have an ID")).body(null);
        }
        Ocena result = ocenaRepository.save(ocena);
        return ResponseEntity.created(new URI("/api/ocenas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ocenas : Updates an existing ocena.
     *
     * @param ocena the ocena to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ocena,
     * or with status 400 (Bad Request) if the ocena is not valid,
     * or with status 500 (Internal Server Error) if the ocena couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ocenas")
    @Timed
    public ResponseEntity<Ocena> updateOcena(@RequestBody Ocena ocena) throws URISyntaxException {
        log.debug("REST request to update Ocena : {}", ocena);
        if (ocena.getId() == null) {
            return createOcena(ocena);
        }
        Ocena result = ocenaRepository.save(ocena);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ocena.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ocenas : get all the ocenas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ocenas in body
     */
    @GetMapping("/ocenas")
    @Timed
    public List<Ocena> getAllOcenas() {
        log.debug("REST request to get all Ocenas");
        return ocenaRepository.findAll();
        }

    /**
     * GET  /ocenas/:id : get the "id" ocena.
     *
     * @param id the id of the ocena to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ocena, or with status 404 (Not Found)
     */
    @GetMapping("/ocenas/{id}")
    @Timed
    public ResponseEntity<Ocena> getOcena(@PathVariable Long id) {
        log.debug("REST request to get Ocena : {}", id);
        Ocena ocena = ocenaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ocena));
    }

    /**
     * DELETE  /ocenas/:id : delete the "id" ocena.
     *
     * @param id the id of the ocena to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ocenas/{id}")
    @Timed
    public ResponseEntity<Void> deleteOcena(@PathVariable Long id) {
        log.debug("REST request to delete Ocena : {}", id);
        ocenaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
