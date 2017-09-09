package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.RasporedSmenaZaKonobare;

import isa.repository.RasporedSmenaZaKonobareRepository;
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
 * REST controller for managing RasporedSmenaZaKonobare.
 */
@RestController
@RequestMapping("/api")
public class RasporedSmenaZaKonobareResource {

    private final Logger log = LoggerFactory.getLogger(RasporedSmenaZaKonobareResource.class);

    private static final String ENTITY_NAME = "rasporedSmenaZaKonobare";

    private final RasporedSmenaZaKonobareRepository rasporedSmenaZaKonobareRepository;
    public RasporedSmenaZaKonobareResource(RasporedSmenaZaKonobareRepository rasporedSmenaZaKonobareRepository) {
        this.rasporedSmenaZaKonobareRepository = rasporedSmenaZaKonobareRepository;
    }

    /**
     * POST  /raspored-smena-za-konobares : Create a new rasporedSmenaZaKonobare.
     *
     * @param rasporedSmenaZaKonobare the rasporedSmenaZaKonobare to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rasporedSmenaZaKonobare, or with status 400 (Bad Request) if the rasporedSmenaZaKonobare has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/raspored-smena-za-konobares")
    @Timed
    public ResponseEntity<RasporedSmenaZaKonobare> createRasporedSmenaZaKonobare(@RequestBody RasporedSmenaZaKonobare rasporedSmenaZaKonobare) throws URISyntaxException {
        log.debug("REST request to save RasporedSmenaZaKonobare : {}", rasporedSmenaZaKonobare);
        if (rasporedSmenaZaKonobare.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rasporedSmenaZaKonobare cannot already have an ID")).body(null);
        }
        RasporedSmenaZaKonobare result = rasporedSmenaZaKonobareRepository.save(rasporedSmenaZaKonobare);
        return ResponseEntity.created(new URI("/api/raspored-smena-za-konobares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /raspored-smena-za-konobares : Updates an existing rasporedSmenaZaKonobare.
     *
     * @param rasporedSmenaZaKonobare the rasporedSmenaZaKonobare to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rasporedSmenaZaKonobare,
     * or with status 400 (Bad Request) if the rasporedSmenaZaKonobare is not valid,
     * or with status 500 (Internal Server Error) if the rasporedSmenaZaKonobare couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/raspored-smena-za-konobares")
    @Timed
    public ResponseEntity<RasporedSmenaZaKonobare> updateRasporedSmenaZaKonobare(@RequestBody RasporedSmenaZaKonobare rasporedSmenaZaKonobare) throws URISyntaxException {
        log.debug("REST request to update RasporedSmenaZaKonobare : {}", rasporedSmenaZaKonobare);
        if (rasporedSmenaZaKonobare.getId() == null) {
            return createRasporedSmenaZaKonobare(rasporedSmenaZaKonobare);
        }
        RasporedSmenaZaKonobare result = rasporedSmenaZaKonobareRepository.save(rasporedSmenaZaKonobare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rasporedSmenaZaKonobare.getId().toString()))
            .body(result);
    }

    /**
     * GET  /raspored-smena-za-konobares : get all the rasporedSmenaZaKonobares.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rasporedSmenaZaKonobares in body
     */
    @GetMapping("/raspored-smena-za-konobares")
    @Timed
    public List<RasporedSmenaZaKonobare> getAllRasporedSmenaZaKonobares() {
        log.debug("REST request to get all RasporedSmenaZaKonobares");
        return rasporedSmenaZaKonobareRepository.findAll();
        }

    /**
     * GET  /raspored-smena-za-konobares/:id : get the "id" rasporedSmenaZaKonobare.
     *
     * @param id the id of the rasporedSmenaZaKonobare to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rasporedSmenaZaKonobare, or with status 404 (Not Found)
     */
    @GetMapping("/raspored-smena-za-konobares/{id}")
    @Timed
    public ResponseEntity<RasporedSmenaZaKonobare> getRasporedSmenaZaKonobare(@PathVariable Long id) {
        log.debug("REST request to get RasporedSmenaZaKonobare : {}", id);
        RasporedSmenaZaKonobare rasporedSmenaZaKonobare = rasporedSmenaZaKonobareRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rasporedSmenaZaKonobare));
    }

    /**
     * DELETE  /raspored-smena-za-konobares/:id : delete the "id" rasporedSmenaZaKonobare.
     *
     * @param id the id of the rasporedSmenaZaKonobare to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/raspored-smena-za-konobares/{id}")
    @Timed
    public ResponseEntity<Void> deleteRasporedSmenaZaKonobare(@PathVariable Long id) {
        log.debug("REST request to delete RasporedSmenaZaKonobare : {}", id);
        rasporedSmenaZaKonobareRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
