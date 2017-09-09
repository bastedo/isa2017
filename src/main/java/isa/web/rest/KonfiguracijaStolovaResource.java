package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.KonfiguracijaStolova;

import isa.repository.KonfiguracijaStolovaRepository;
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
 * REST controller for managing KonfiguracijaStolova.
 */
@RestController
@RequestMapping("/api")
public class KonfiguracijaStolovaResource {

    private final Logger log = LoggerFactory.getLogger(KonfiguracijaStolovaResource.class);

    private static final String ENTITY_NAME = "konfiguracijaStolova";

    private final KonfiguracijaStolovaRepository konfiguracijaStolovaRepository;
    public KonfiguracijaStolovaResource(KonfiguracijaStolovaRepository konfiguracijaStolovaRepository) {
        this.konfiguracijaStolovaRepository = konfiguracijaStolovaRepository;
    }

    /**
     * POST  /konfiguracija-stolova : Create a new konfiguracijaStolova.
     *
     * @param konfiguracijaStolova the konfiguracijaStolova to create
     * @return the ResponseEntity with status 201 (Created) and with body the new konfiguracijaStolova, or with status 400 (Bad Request) if the konfiguracijaStolova has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/konfiguracija-stolova")
    @Timed
    public ResponseEntity<KonfiguracijaStolova> createKonfiguracijaStolova(@RequestBody KonfiguracijaStolova konfiguracijaStolova) throws URISyntaxException {
        log.debug("REST request to save KonfiguracijaStolova : {}", konfiguracijaStolova);
        if (konfiguracijaStolova.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new konfiguracijaStolova cannot already have an ID")).body(null);
        }
        KonfiguracijaStolova result = konfiguracijaStolovaRepository.save(konfiguracijaStolova);
        return ResponseEntity.created(new URI("/api/konfiguracija-stolova/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /konfiguracija-stolova : Updates an existing konfiguracijaStolova.
     *
     * @param konfiguracijaStolova the konfiguracijaStolova to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated konfiguracijaStolova,
     * or with status 400 (Bad Request) if the konfiguracijaStolova is not valid,
     * or with status 500 (Internal Server Error) if the konfiguracijaStolova couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/konfiguracija-stolova")
    @Timed
    public ResponseEntity<KonfiguracijaStolova> updateKonfiguracijaStolova(@RequestBody KonfiguracijaStolova konfiguracijaStolova) throws URISyntaxException {
        log.debug("REST request to update KonfiguracijaStolova : {}", konfiguracijaStolova);
        if (konfiguracijaStolova.getId() == null) {
            return createKonfiguracijaStolova(konfiguracijaStolova);
        }
        KonfiguracijaStolova result = konfiguracijaStolovaRepository.save(konfiguracijaStolova);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, konfiguracijaStolova.getId().toString()))
            .body(result);
    }

    /**
     * GET  /konfiguracija-stolova : get all the konfiguracijaStolova.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of konfiguracijaStolova in body
     */
    @GetMapping("/konfiguracija-stolova")
    @Timed
    public List<KonfiguracijaStolova> getAllKonfiguracijaStolova() {
        log.debug("REST request to get all KonfiguracijaStolova");
        return konfiguracijaStolovaRepository.findAll();
        }

    /**
     * GET  /konfiguracija-stolova/:id : get the "id" konfiguracijaStolova.
     *
     * @param id the id of the konfiguracijaStolova to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the konfiguracijaStolova, or with status 404 (Not Found)
     */
    @GetMapping("/konfiguracija-stolova/{id}")
    @Timed
    public ResponseEntity<KonfiguracijaStolova> getKonfiguracijaStolova(@PathVariable Long id) {
        log.debug("REST request to get KonfiguracijaStolova : {}", id);
        KonfiguracijaStolova konfiguracijaStolova = konfiguracijaStolovaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(konfiguracijaStolova));
    }

    /**
     * DELETE  /konfiguracija-stolova/:id : delete the "id" konfiguracijaStolova.
     *
     * @param id the id of the konfiguracijaStolova to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/konfiguracija-stolova/{id}")
    @Timed
    public ResponseEntity<Void> deleteKonfiguracijaStolova(@PathVariable Long id) {
        log.debug("REST request to delete KonfiguracijaStolova : {}", id);
        konfiguracijaStolovaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
