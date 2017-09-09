package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.MenadzerRestorana;

import isa.repository.MenadzerRestoranaRepository;
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
 * REST controller for managing MenadzerRestorana.
 */
@RestController
@RequestMapping("/api")
public class MenadzerRestoranaResource {

    private final Logger log = LoggerFactory.getLogger(MenadzerRestoranaResource.class);

    private static final String ENTITY_NAME = "menadzerRestorana";

    private final MenadzerRestoranaRepository menadzerRestoranaRepository;
    public MenadzerRestoranaResource(MenadzerRestoranaRepository menadzerRestoranaRepository) {
        this.menadzerRestoranaRepository = menadzerRestoranaRepository;
    }

    /**
     * POST  /menadzer-restoranas : Create a new menadzerRestorana.
     *
     * @param menadzerRestorana the menadzerRestorana to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menadzerRestorana, or with status 400 (Bad Request) if the menadzerRestorana has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menadzer-restoranas")
    @Timed
    public ResponseEntity<MenadzerRestorana> createMenadzerRestorana(@RequestBody MenadzerRestorana menadzerRestorana) throws URISyntaxException {
        log.debug("REST request to save MenadzerRestorana : {}", menadzerRestorana);
        if (menadzerRestorana.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new menadzerRestorana cannot already have an ID")).body(null);
        }
        MenadzerRestorana result = menadzerRestoranaRepository.save(menadzerRestorana);
        return ResponseEntity.created(new URI("/api/menadzer-restoranas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menadzer-restoranas : Updates an existing menadzerRestorana.
     *
     * @param menadzerRestorana the menadzerRestorana to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menadzerRestorana,
     * or with status 400 (Bad Request) if the menadzerRestorana is not valid,
     * or with status 500 (Internal Server Error) if the menadzerRestorana couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menadzer-restoranas")
    @Timed
    public ResponseEntity<MenadzerRestorana> updateMenadzerRestorana(@RequestBody MenadzerRestorana menadzerRestorana) throws URISyntaxException {
        log.debug("REST request to update MenadzerRestorana : {}", menadzerRestorana);
        if (menadzerRestorana.getId() == null) {
            return createMenadzerRestorana(menadzerRestorana);
        }
        MenadzerRestorana result = menadzerRestoranaRepository.save(menadzerRestorana);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menadzerRestorana.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menadzer-restoranas : get all the menadzerRestoranas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of menadzerRestoranas in body
     */
    @GetMapping("/menadzer-restoranas")
    @Timed
    public List<MenadzerRestorana> getAllMenadzerRestoranas() {
        log.debug("REST request to get all MenadzerRestoranas");
        return menadzerRestoranaRepository.findAll();
        }

    /**
     * GET  /menadzer-restoranas/:id : get the "id" menadzerRestorana.
     *
     * @param id the id of the menadzerRestorana to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menadzerRestorana, or with status 404 (Not Found)
     */
    @GetMapping("/menadzer-restoranas/{id}")
    @Timed
    public ResponseEntity<MenadzerRestorana> getMenadzerRestorana(@PathVariable Long id) {
        log.debug("REST request to get MenadzerRestorana : {}", id);
        MenadzerRestorana menadzerRestorana = menadzerRestoranaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(menadzerRestorana));
    }

    /**
     * DELETE  /menadzer-restoranas/:id : delete the "id" menadzerRestorana.
     *
     * @param id the id of the menadzerRestorana to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menadzer-restoranas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenadzerRestorana(@PathVariable Long id) {
        log.debug("REST request to delete MenadzerRestorana : {}", id);
        menadzerRestoranaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
