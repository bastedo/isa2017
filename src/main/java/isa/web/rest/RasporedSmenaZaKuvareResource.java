package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.RasporedSmenaZaKuvare;

import isa.repository.RasporedSmenaZaKuvareRepository;
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
 * REST controller for managing RasporedSmenaZaKuvare.
 */
@RestController
@RequestMapping("/api")
public class RasporedSmenaZaKuvareResource {

    private final Logger log = LoggerFactory.getLogger(RasporedSmenaZaKuvareResource.class);

    private static final String ENTITY_NAME = "rasporedSmenaZaKuvare";

    private final RasporedSmenaZaKuvareRepository rasporedSmenaZaKuvareRepository;
    public RasporedSmenaZaKuvareResource(RasporedSmenaZaKuvareRepository rasporedSmenaZaKuvareRepository) {
        this.rasporedSmenaZaKuvareRepository = rasporedSmenaZaKuvareRepository;
    }

    /**
     * POST  /raspored-smena-za-kuvares : Create a new rasporedSmenaZaKuvare.
     *
     * @param rasporedSmenaZaKuvare the rasporedSmenaZaKuvare to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rasporedSmenaZaKuvare, or with status 400 (Bad Request) if the rasporedSmenaZaKuvare has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raspored-smena-za-kuvares")
    @Timed
    public ResponseEntity<RasporedSmenaZaKuvare> createRasporedSmenaZaKuvare(@RequestBody RasporedSmenaZaKuvare rasporedSmenaZaKuvare) throws URISyntaxException {
        log.debug("REST request to save RasporedSmenaZaKuvare : {}", rasporedSmenaZaKuvare);
        if (rasporedSmenaZaKuvare.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rasporedSmenaZaKuvare cannot already have an ID")).body(null);
        }
        RasporedSmenaZaKuvare result = rasporedSmenaZaKuvareRepository.save(rasporedSmenaZaKuvare);
        return ResponseEntity.created(new URI("/api/raspored-smena-za-kuvares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raspored-smena-za-kuvares : Updates an existing rasporedSmenaZaKuvare.
     *
     * @param rasporedSmenaZaKuvare the rasporedSmenaZaKuvare to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rasporedSmenaZaKuvare,
     * or with status 400 (Bad Request) if the rasporedSmenaZaKuvare is not valid,
     * or with status 500 (Internal Server Error) if the rasporedSmenaZaKuvare couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raspored-smena-za-kuvares")
    @Timed
    public ResponseEntity<RasporedSmenaZaKuvare> updateRasporedSmenaZaKuvare(@RequestBody RasporedSmenaZaKuvare rasporedSmenaZaKuvare) throws URISyntaxException {
        log.debug("REST request to update RasporedSmenaZaKuvare : {}", rasporedSmenaZaKuvare);
        if (rasporedSmenaZaKuvare.getId() == null) {
            return createRasporedSmenaZaKuvare(rasporedSmenaZaKuvare);
        }
        RasporedSmenaZaKuvare result = rasporedSmenaZaKuvareRepository.save(rasporedSmenaZaKuvare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rasporedSmenaZaKuvare.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raspored-smena-za-kuvares : get all the rasporedSmenaZaKuvares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rasporedSmenaZaKuvares in body
     */
    @GetMapping("/raspored-smena-za-kuvares")
    @Timed
    public List<RasporedSmenaZaKuvare> getAllRasporedSmenaZaKuvares() {
        log.debug("REST request to get all RasporedSmenaZaKuvares");
        return rasporedSmenaZaKuvareRepository.findAll();
        }

    /**
     * GET  /raspored-smena-za-kuvares/:id : get the "id" rasporedSmenaZaKuvare.
     *
     * @param id the id of the rasporedSmenaZaKuvare to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rasporedSmenaZaKuvare, or with status 404 (Not Found)
     */
    @GetMapping("/raspored-smena-za-kuvares/{id}")
    @Timed
    public ResponseEntity<RasporedSmenaZaKuvare> getRasporedSmenaZaKuvare(@PathVariable Long id) {
        log.debug("REST request to get RasporedSmenaZaKuvare : {}", id);
        RasporedSmenaZaKuvare rasporedSmenaZaKuvare = rasporedSmenaZaKuvareRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rasporedSmenaZaKuvare));
    }

    /**
     * DELETE  /raspored-smena-za-kuvares/:id : delete the "id" rasporedSmenaZaKuvare.
     *
     * @param id the id of the rasporedSmenaZaKuvare to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raspored-smena-za-kuvares/{id}")
    @Timed
    public ResponseEntity<Void> deleteRasporedSmenaZaKuvare(@PathVariable Long id) {
        log.debug("REST request to delete RasporedSmenaZaKuvare : {}", id);
        rasporedSmenaZaKuvareRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
