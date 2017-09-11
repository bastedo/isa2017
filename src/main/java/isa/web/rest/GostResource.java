package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Gost;

import isa.repository.GostRepository;
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
 * REST controller for managing Gost.
 */
@RestController
@RequestMapping("/api")
public class GostResource {

    private final Logger log = LoggerFactory.getLogger(GostResource.class);

    private static final String ENTITY_NAME = "gost";

    private final GostRepository gostRepository;
    public GostResource(GostRepository gostRepository) {
        this.gostRepository = gostRepository;
    }

    /**
     * POST  /gosts : Create a new gost.
     *
     * @param gost the gost to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gost, or with status 400 (Bad Request) if the gost has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gosts")
    @Timed
    public ResponseEntity<Gost> createGost(@RequestBody Gost gost) throws URISyntaxException {
        log.debug("REST request to save Gost : {}", gost);
        if (gost.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gost cannot already have an ID")).body(null);
        }
        Gost result = gostRepository.save(gost);
        return ResponseEntity.created(new URI("/api/gosts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gosts : Updates an existing gost.
     *
     * @param gost the gost to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gost,
     * or with status 400 (Bad Request) if the gost is not valid,
     * or with status 500 (Internal Server Error) if the gost couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gosts")
    @Timed
    public ResponseEntity<Gost> updateGost(@RequestBody Gost gost) throws URISyntaxException {
        log.debug("REST request to update Gost : {}", gost);
        if (gost.getId() == null) {
            return createGost(gost);
        }
        Gost result = gostRepository.save(gost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gost.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gosts : get all the gosts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gosts in body
     */
    @GetMapping("/gosts")
    @Timed
    public List<Gost> getAllGosts() {
        log.debug("REST request to get all Gosts");
        return gostRepository.findAllWithEagerRelationships();
        }

    /**
     * GET  /gosts/:id : get the "id" gost.
     *
     * @param id the id of the gost to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gost, or with status 404 (Not Found)
     */
    @GetMapping("/gosts/{id}")
    @Timed
    public ResponseEntity<Gost> getGost(@PathVariable Long id) {
        log.debug("REST request to get Gost : {}", id);
        Gost gost = gostRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gost));
    }

    /**
     * DELETE  /gosts/:id : delete the "id" gost.
     *
     * @param id the id of the gost to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gosts/{id}")
    @Timed
    public ResponseEntity<Void> deleteGost(@PathVariable Long id) {
        log.debug("REST request to delete Gost : {}", id);
        gostRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
