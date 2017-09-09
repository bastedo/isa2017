package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Rezervacija;

import isa.repository.RezervacijaRepository;
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
 * REST controller for managing Rezervacija.
 */
@RestController
@RequestMapping("/api")
public class RezervacijaResource {

    private final Logger log = LoggerFactory.getLogger(RezervacijaResource.class);

    private static final String ENTITY_NAME = "rezervacija";

    private final RezervacijaRepository rezervacijaRepository;
    public RezervacijaResource(RezervacijaRepository rezervacijaRepository) {
        this.rezervacijaRepository = rezervacijaRepository;
    }

    /**
     * POST  /rezervacijas : Create a new rezervacija.
     *
     * @param rezervacija the rezervacija to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rezervacija, or with status 400 (Bad Request) if the rezervacija has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rezervacijas")
    @Timed
    public ResponseEntity<Rezervacija> createRezervacija(@RequestBody Rezervacija rezervacija) throws URISyntaxException {
        log.debug("REST request to save Rezervacija : {}", rezervacija);
        if (rezervacija.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rezervacija cannot already have an ID")).body(null);
        }
        Rezervacija result = rezervacijaRepository.save(rezervacija);
        return ResponseEntity.created(new URI("/api/rezervacijas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rezervacijas : Updates an existing rezervacija.
     *
     * @param rezervacija the rezervacija to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rezervacija,
     * or with status 400 (Bad Request) if the rezervacija is not valid,
     * or with status 500 (Internal Server Error) if the rezervacija couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rezervacijas")
    @Timed
    public ResponseEntity<Rezervacija> updateRezervacija(@RequestBody Rezervacija rezervacija) throws URISyntaxException {
        log.debug("REST request to update Rezervacija : {}", rezervacija);
        if (rezervacija.getId() == null) {
            return createRezervacija(rezervacija);
        }
        Rezervacija result = rezervacijaRepository.save(rezervacija);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rezervacija.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rezervacijas : get all the rezervacijas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rezervacijas in body
     */
    @GetMapping("/rezervacijas")
    @Timed
    public List<Rezervacija> getAllRezervacijas() {
        log.debug("REST request to get all Rezervacijas");
        return rezervacijaRepository.findAll();
        }

    /**
     * GET  /rezervacijas/:id : get the "id" rezervacija.
     *
     * @param id the id of the rezervacija to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rezervacija, or with status 404 (Not Found)
     */
    @GetMapping("/rezervacijas/{id}")
    @Timed
    public ResponseEntity<Rezervacija> getRezervacija(@PathVariable Long id) {
        log.debug("REST request to get Rezervacija : {}", id);
        Rezervacija rezervacija = rezervacijaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rezervacija));
    }

    /**
     * DELETE  /rezervacijas/:id : delete the "id" rezervacija.
     *
     * @param id the id of the rezervacija to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rezervacijas/{id}")
    @Timed
    public ResponseEntity<Void> deleteRezervacija(@PathVariable Long id) {
        log.debug("REST request to delete Rezervacija : {}", id);
        rezervacijaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
