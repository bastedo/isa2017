package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.PorudzbinaZANabavku;

import isa.repository.PorudzbinaZANabavkuRepository;
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
 * REST controller for managing PorudzbinaZANabavku.
 */
@RestController
@RequestMapping("/api")
public class PorudzbinaZANabavkuResource {

    private final Logger log = LoggerFactory.getLogger(PorudzbinaZANabavkuResource.class);

    private static final String ENTITY_NAME = "porudzbinaZANabavku";

    private final PorudzbinaZANabavkuRepository porudzbinaZANabavkuRepository;
    public PorudzbinaZANabavkuResource(PorudzbinaZANabavkuRepository porudzbinaZANabavkuRepository) {
        this.porudzbinaZANabavkuRepository = porudzbinaZANabavkuRepository;
    }

    /**
     * POST  /porudzbina-za-nabavkus : Create a new porudzbinaZANabavku.
     *
     * @param porudzbinaZANabavku the porudzbinaZANabavku to create
     * @return the ResponseEntity with status 201 (Created) and with body the new porudzbinaZANabavku, or with status 400 (Bad Request) if the porudzbinaZANabavku has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/porudzbina-za-nabavkus")
    @Timed
    public ResponseEntity<PorudzbinaZANabavku> createPorudzbinaZANabavku(@RequestBody PorudzbinaZANabavku porudzbinaZANabavku) throws URISyntaxException {
        log.debug("REST request to save PorudzbinaZANabavku : {}", porudzbinaZANabavku);
        if (porudzbinaZANabavku.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new porudzbinaZANabavku cannot already have an ID")).body(null);
        }
        PorudzbinaZANabavku result = porudzbinaZANabavkuRepository.save(porudzbinaZANabavku);
        return ResponseEntity.created(new URI("/api/porudzbina-za-nabavkus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /porudzbina-za-nabavkus : Updates an existing porudzbinaZANabavku.
     *
     * @param porudzbinaZANabavku the porudzbinaZANabavku to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated porudzbinaZANabavku,
     * or with status 400 (Bad Request) if the porudzbinaZANabavku is not valid,
     * or with status 500 (Internal Server Error) if the porudzbinaZANabavku couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/porudzbina-za-nabavkus")
    @Timed
    public ResponseEntity<PorudzbinaZANabavku> updatePorudzbinaZANabavku(@RequestBody PorudzbinaZANabavku porudzbinaZANabavku) throws URISyntaxException {
        log.debug("REST request to update PorudzbinaZANabavku : {}", porudzbinaZANabavku);
        if (porudzbinaZANabavku.getId() == null) {
            return createPorudzbinaZANabavku(porudzbinaZANabavku);
        }
        PorudzbinaZANabavku result = porudzbinaZANabavkuRepository.save(porudzbinaZANabavku);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, porudzbinaZANabavku.getId().toString()))
            .body(result);
    }

    /**
     * GET  /porudzbina-za-nabavkus : get all the porudzbinaZANabavkus.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of porudzbinaZANabavkus in body
     */
    @GetMapping("/porudzbina-za-nabavkus")
    @Timed
    public List<PorudzbinaZANabavku> getAllPorudzbinaZANabavkus() {
        log.debug("REST request to get all PorudzbinaZANabavkus");
        return porudzbinaZANabavkuRepository.findAll();
        }

    /**
     * GET  /porudzbina-za-nabavkus/:id : get the "id" porudzbinaZANabavku.
     *
     * @param id the id of the porudzbinaZANabavku to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the porudzbinaZANabavku, or with status 404 (Not Found)
     */
    @GetMapping("/porudzbina-za-nabavkus/{id}")
    @Timed
    public ResponseEntity<PorudzbinaZANabavku> getPorudzbinaZANabavku(@PathVariable Long id) {
        log.debug("REST request to get PorudzbinaZANabavku : {}", id);
        PorudzbinaZANabavku porudzbinaZANabavku = porudzbinaZANabavkuRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(porudzbinaZANabavku));
    }

    /**
     * DELETE  /porudzbina-za-nabavkus/:id : delete the "id" porudzbinaZANabavku.
     *
     * @param id the id of the porudzbinaZANabavku to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/porudzbina-za-nabavkus/{id}")
    @Timed
    public ResponseEntity<Void> deletePorudzbinaZANabavku(@PathVariable Long id) {
        log.debug("REST request to delete PorudzbinaZANabavku : {}", id);
        porudzbinaZANabavkuRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
