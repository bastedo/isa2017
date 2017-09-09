package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Zaposleni;

import isa.repository.ZaposleniRepository;
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
 * REST controller for managing Zaposleni.
 */
@RestController
@RequestMapping("/api")
public class ZaposleniResource {

    private final Logger log = LoggerFactory.getLogger(ZaposleniResource.class);

    private static final String ENTITY_NAME = "zaposleni";

    private final ZaposleniRepository zaposleniRepository;
    public ZaposleniResource(ZaposleniRepository zaposleniRepository) {
        this.zaposleniRepository = zaposleniRepository;
    }

    /**
     * POST  /zaposlenis : Create a new zaposleni.
     *
     * @param zaposleni the zaposleni to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zaposleni, or with status 400 (Bad Request) if the zaposleni has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zaposlenis")
    @Timed
    public ResponseEntity<Zaposleni> createZaposleni(@RequestBody Zaposleni zaposleni) throws URISyntaxException {
        log.debug("REST request to save Zaposleni : {}", zaposleni);
        if (zaposleni.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zaposleni cannot already have an ID")).body(null);
        }
        Zaposleni result = zaposleniRepository.save(zaposleni);
        return ResponseEntity.created(new URI("/api/zaposlenis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zaposlenis : Updates an existing zaposleni.
     *
     * @param zaposleni the zaposleni to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zaposleni,
     * or with status 400 (Bad Request) if the zaposleni is not valid,
     * or with status 500 (Internal Server Error) if the zaposleni couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zaposlenis")
    @Timed
    public ResponseEntity<Zaposleni> updateZaposleni(@RequestBody Zaposleni zaposleni) throws URISyntaxException {
        log.debug("REST request to update Zaposleni : {}", zaposleni);
        if (zaposleni.getId() == null) {
            return createZaposleni(zaposleni);
        }
        Zaposleni result = zaposleniRepository.save(zaposleni);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zaposleni.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zaposlenis : get all the zaposlenis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zaposlenis in body
     */
    @GetMapping("/zaposlenis")
    @Timed
    public List<Zaposleni> getAllZaposlenis() {
        log.debug("REST request to get all Zaposlenis");
        return zaposleniRepository.findAll();
        }

    /**
     * GET  /zaposlenis/:id : get the "id" zaposleni.
     *
     * @param id the id of the zaposleni to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zaposleni, or with status 404 (Not Found)
     */
    @GetMapping("/zaposlenis/{id}")
    @Timed
    public ResponseEntity<Zaposleni> getZaposleni(@PathVariable Long id) {
        log.debug("REST request to get Zaposleni : {}", id);
        Zaposleni zaposleni = zaposleniRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zaposleni));
    }

    /**
     * DELETE  /zaposlenis/:id : delete the "id" zaposleni.
     *
     * @param id the id of the zaposleni to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zaposlenis/{id}")
    @Timed
    public ResponseEntity<Void> deleteZaposleni(@PathVariable Long id) {
        log.debug("REST request to delete Zaposleni : {}", id);
        zaposleniRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
