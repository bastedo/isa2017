package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.MenadzerSistema;

import isa.repository.MenadzerSistemaRepository;
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
 * REST controller for managing MenadzerSistema.
 */
@RestController
@RequestMapping("/api")
public class MenadzerSistemaResource {

    private final Logger log = LoggerFactory.getLogger(MenadzerSistemaResource.class);

    private static final String ENTITY_NAME = "menadzerSistema";

    private final MenadzerSistemaRepository menadzerSistemaRepository;
    public MenadzerSistemaResource(MenadzerSistemaRepository menadzerSistemaRepository) {
        this.menadzerSistemaRepository = menadzerSistemaRepository;
    }

    /**
     * POST  /menadzer-sistemas : Create a new menadzerSistema.
     *
     * @param menadzerSistema the menadzerSistema to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menadzerSistema, or with status 400 (Bad Request) if the menadzerSistema has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menadzer-sistemas")
    @Timed
    public ResponseEntity<MenadzerSistema> createMenadzerSistema(@RequestBody MenadzerSistema menadzerSistema) throws URISyntaxException {
        log.debug("REST request to save MenadzerSistema : {}", menadzerSistema);
        if (menadzerSistema.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new menadzerSistema cannot already have an ID")).body(null);
        }
        MenadzerSistema result = menadzerSistemaRepository.save(menadzerSistema);
        return ResponseEntity.created(new URI("/api/menadzer-sistemas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menadzer-sistemas : Updates an existing menadzerSistema.
     *
     * @param menadzerSistema the menadzerSistema to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menadzerSistema,
     * or with status 400 (Bad Request) if the menadzerSistema is not valid,
     * or with status 500 (Internal Server Error) if the menadzerSistema couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menadzer-sistemas")
    @Timed
    public ResponseEntity<MenadzerSistema> updateMenadzerSistema(@RequestBody MenadzerSistema menadzerSistema) throws URISyntaxException {
        log.debug("REST request to update MenadzerSistema : {}", menadzerSistema);
        if (menadzerSistema.getId() == null) {
            return createMenadzerSistema(menadzerSistema);
        }
        MenadzerSistema result = menadzerSistemaRepository.save(menadzerSistema);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menadzerSistema.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menadzer-sistemas : get all the menadzerSistemas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of menadzerSistemas in body
     */
    @GetMapping("/menadzer-sistemas")
    @Timed
    public List<MenadzerSistema> getAllMenadzerSistemas() {
        log.debug("REST request to get all MenadzerSistemas");
        return menadzerSistemaRepository.findAll();
        }

    /**
     * GET  /menadzer-sistemas/:id : get the "id" menadzerSistema.
     *
     * @param id the id of the menadzerSistema to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menadzerSistema, or with status 404 (Not Found)
     */
    @GetMapping("/menadzer-sistemas/{id}")
    @Timed
    public ResponseEntity<MenadzerSistema> getMenadzerSistema(@PathVariable Long id) {
        log.debug("REST request to get MenadzerSistema : {}", id);
        MenadzerSistema menadzerSistema = menadzerSistemaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(menadzerSistema));
    }

    /**
     * DELETE  /menadzer-sistemas/:id : delete the "id" menadzerSistema.
     *
     * @param id the id of the menadzerSistema to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menadzer-sistemas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenadzerSistema(@PathVariable Long id) {
        log.debug("REST request to delete MenadzerSistema : {}", id);
        menadzerSistemaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
