package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.RasporedSmenaZaSankere;

import isa.repository.RasporedSmenaZaSankereRepository;
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
 * REST controller for managing RasporedSmenaZaSankere.
 */
@RestController
@RequestMapping("/api")
public class RasporedSmenaZaSankereResource {

    private final Logger log = LoggerFactory.getLogger(RasporedSmenaZaSankereResource.class);

    private static final String ENTITY_NAME = "rasporedSmenaZaSankere";

    private final RasporedSmenaZaSankereRepository rasporedSmenaZaSankereRepository;
    public RasporedSmenaZaSankereResource(RasporedSmenaZaSankereRepository rasporedSmenaZaSankereRepository) {
        this.rasporedSmenaZaSankereRepository = rasporedSmenaZaSankereRepository;
    }

    /**
     * POST  /raspored-smena-za-sankeres : Create a new rasporedSmenaZaSankere.
     *
     * @param rasporedSmenaZaSankere the rasporedSmenaZaSankere to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rasporedSmenaZaSankere, or with status 400 (Bad Request) if the rasporedSmenaZaSankere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raspored-smena-za-sankeres")
    @Timed
    public ResponseEntity<RasporedSmenaZaSankere> createRasporedSmenaZaSankere(@RequestBody RasporedSmenaZaSankere rasporedSmenaZaSankere) throws URISyntaxException {
        log.debug("REST request to save RasporedSmenaZaSankere : {}", rasporedSmenaZaSankere);
        if (rasporedSmenaZaSankere.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rasporedSmenaZaSankere cannot already have an ID")).body(null);
        }
        RasporedSmenaZaSankere result = rasporedSmenaZaSankereRepository.save(rasporedSmenaZaSankere);
        return ResponseEntity.created(new URI("/api/raspored-smena-za-sankeres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raspored-smena-za-sankeres : Updates an existing rasporedSmenaZaSankere.
     *
     * @param rasporedSmenaZaSankere the rasporedSmenaZaSankere to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rasporedSmenaZaSankere,
     * or with status 400 (Bad Request) if the rasporedSmenaZaSankere is not valid,
     * or with status 500 (Internal Server Error) if the rasporedSmenaZaSankere couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raspored-smena-za-sankeres")
    @Timed
    public ResponseEntity<RasporedSmenaZaSankere> updateRasporedSmenaZaSankere(@RequestBody RasporedSmenaZaSankere rasporedSmenaZaSankere) throws URISyntaxException {
        log.debug("REST request to update RasporedSmenaZaSankere : {}", rasporedSmenaZaSankere);
        if (rasporedSmenaZaSankere.getId() == null) {
            return createRasporedSmenaZaSankere(rasporedSmenaZaSankere);
        }
        RasporedSmenaZaSankere result = rasporedSmenaZaSankereRepository.save(rasporedSmenaZaSankere);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rasporedSmenaZaSankere.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raspored-smena-za-sankeres : get all the rasporedSmenaZaSankeres.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rasporedSmenaZaSankeres in body
     */
    @GetMapping("/raspored-smena-za-sankeres")
    @Timed
    public List<RasporedSmenaZaSankere> getAllRasporedSmenaZaSankeres() {
        log.debug("REST request to get all RasporedSmenaZaSankeres");
        return rasporedSmenaZaSankereRepository.findAll();
        }

    /**
     * GET  /raspored-smena-za-sankeres/:id : get the "id" rasporedSmenaZaSankere.
     *
     * @param id the id of the rasporedSmenaZaSankere to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rasporedSmenaZaSankere, or with status 404 (Not Found)
     */
    @GetMapping("/raspored-smena-za-sankeres/{id}")
    @Timed
    public ResponseEntity<RasporedSmenaZaSankere> getRasporedSmenaZaSankere(@PathVariable Long id) {
        log.debug("REST request to get RasporedSmenaZaSankere : {}", id);
        RasporedSmenaZaSankere rasporedSmenaZaSankere = rasporedSmenaZaSankereRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rasporedSmenaZaSankere));
    }

    /**
     * DELETE  /raspored-smena-za-sankeres/:id : delete the "id" rasporedSmenaZaSankere.
     *
     * @param id the id of the rasporedSmenaZaSankere to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raspored-smena-za-sankeres/{id}")
    @Timed
    public ResponseEntity<Void> deleteRasporedSmenaZaSankere(@PathVariable Long id) {
        log.debug("REST request to delete RasporedSmenaZaSankere : {}", id);
        rasporedSmenaZaSankereRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
