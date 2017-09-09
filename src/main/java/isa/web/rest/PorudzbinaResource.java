package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Porudzbina;

import isa.repository.PorudzbinaRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Porudzbina.
 */
@RestController
@RequestMapping("/api")
public class PorudzbinaResource {

    private final Logger log = LoggerFactory.getLogger(PorudzbinaResource.class);

    private static final String ENTITY_NAME = "porudzbina";

    private final PorudzbinaRepository porudzbinaRepository;
    public PorudzbinaResource(PorudzbinaRepository porudzbinaRepository) {
        this.porudzbinaRepository = porudzbinaRepository;
    }

    /**
     * POST  /porudzbinas : Create a new porudzbina.
     *
     * @param porudzbina the porudzbina to create
     * @return the ResponseEntity with status 201 (Created) and with body the new porudzbina, or with status 400 (Bad Request) if the porudzbina has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/porudzbinas")
    @Timed
    public ResponseEntity<Porudzbina> createPorudzbina(@RequestBody Porudzbina porudzbina) throws URISyntaxException {
        log.debug("REST request to save Porudzbina : {}", porudzbina);
        if (porudzbina.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new porudzbina cannot already have an ID")).body(null);
        }
        Porudzbina result = porudzbinaRepository.save(porudzbina);
        return ResponseEntity.created(new URI("/api/porudzbinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /porudzbinas : Updates an existing porudzbina.
     *
     * @param porudzbina the porudzbina to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated porudzbina,
     * or with status 400 (Bad Request) if the porudzbina is not valid,
     * or with status 500 (Internal Server Error) if the porudzbina couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/porudzbinas")
    @Timed
    public ResponseEntity<Porudzbina> updatePorudzbina(@RequestBody Porudzbina porudzbina) throws URISyntaxException {
        log.debug("REST request to update Porudzbina : {}", porudzbina);
        if (porudzbina.getId() == null) {
            return createPorudzbina(porudzbina);
        }
        Porudzbina result = porudzbinaRepository.save(porudzbina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, porudzbina.getId().toString()))
            .body(result);
    }

    /**
     * GET  /porudzbinas : get all the porudzbinas.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of porudzbinas in body
     */
    @GetMapping("/porudzbinas")
    @Timed
    public List<Porudzbina> getAllPorudzbinas(@RequestParam(required = false) String filter) {
        if ("zarezervaciju-is-null".equals(filter)) {
            log.debug("REST request to get all Porudzbinas where zaRezervaciju is null");
            return StreamSupport
                .stream(porudzbinaRepository.findAll().spliterator(), false)
                .filter(porudzbina -> porudzbina.getZaRezervaciju() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Porudzbinas");
        return porudzbinaRepository.findAll();
        }

    /**
     * GET  /porudzbinas/:id : get the "id" porudzbina.
     *
     * @param id the id of the porudzbina to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the porudzbina, or with status 404 (Not Found)
     */
    @GetMapping("/porudzbinas/{id}")
    @Timed
    public ResponseEntity<Porudzbina> getPorudzbina(@PathVariable Long id) {
        log.debug("REST request to get Porudzbina : {}", id);
        Porudzbina porudzbina = porudzbinaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(porudzbina));
    }

    /**
     * DELETE  /porudzbinas/:id : delete the "id" porudzbina.
     *
     * @param id the id of the porudzbina to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/porudzbinas/{id}")
    @Timed
    public ResponseEntity<Void> deletePorudzbina(@PathVariable Long id) {
        log.debug("REST request to delete Porudzbina : {}", id);
        porudzbinaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
