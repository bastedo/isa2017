package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.KartaPica;

import isa.repository.KartaPicaRepository;
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
 * REST controller for managing KartaPica.
 */
@RestController
@RequestMapping("/api")
public class KartaPicaResource {

    private final Logger log = LoggerFactory.getLogger(KartaPicaResource.class);

    private static final String ENTITY_NAME = "kartaPica";

    private final KartaPicaRepository kartaPicaRepository;
    public KartaPicaResource(KartaPicaRepository kartaPicaRepository) {
        this.kartaPicaRepository = kartaPicaRepository;
    }

    /**
     * POST  /karta-picas : Create a new kartaPica.
     *
     * @param kartaPica the kartaPica to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kartaPica, or with status 400 (Bad Request) if the kartaPica has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/karta-picas")
    @Timed
    public ResponseEntity<KartaPica> createKartaPica(@RequestBody KartaPica kartaPica) throws URISyntaxException {
        log.debug("REST request to save KartaPica : {}", kartaPica);
        if (kartaPica.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new kartaPica cannot already have an ID")).body(null);
        }
        KartaPica result = kartaPicaRepository.save(kartaPica);
        return ResponseEntity.created(new URI("/api/karta-picas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /karta-picas : Updates an existing kartaPica.
     *
     * @param kartaPica the kartaPica to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kartaPica,
     * or with status 400 (Bad Request) if the kartaPica is not valid,
     * or with status 500 (Internal Server Error) if the kartaPica couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/karta-picas")
    @Timed
    public ResponseEntity<KartaPica> updateKartaPica(@RequestBody KartaPica kartaPica) throws URISyntaxException {
        log.debug("REST request to update KartaPica : {}", kartaPica);
        if (kartaPica.getId() == null) {
            return createKartaPica(kartaPica);
        }
        KartaPica result = kartaPicaRepository.save(kartaPica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, kartaPica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /karta-picas : get all the kartaPicas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kartaPicas in body
     */
    @GetMapping("/karta-picas")
    @Timed
    public List<KartaPica> getAllKartaPicas() {
        log.debug("REST request to get all KartaPicas");
        return kartaPicaRepository.findAll();
        }

    /**
     * GET  /karta-picas/:id : get the "id" kartaPica.
     *
     * @param id the id of the kartaPica to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kartaPica, or with status 404 (Not Found)
     */
    @GetMapping("/karta-picas/{id}")
    @Timed
    public ResponseEntity<KartaPica> getKartaPica(@PathVariable Long id) {
        log.debug("REST request to get KartaPica : {}", id);
        KartaPica kartaPica = kartaPicaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(kartaPica));
    }

    /**
     * DELETE  /karta-picas/:id : delete the "id" kartaPica.
     *
     * @param id the id of the kartaPica to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/karta-picas/{id}")
    @Timed
    public ResponseEntity<Void> deleteKartaPica(@PathVariable Long id) {
        log.debug("REST request to delete KartaPica : {}", id);
        kartaPicaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
