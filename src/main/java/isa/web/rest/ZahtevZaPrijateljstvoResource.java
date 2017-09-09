package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.ZahtevZaPrijateljstvo;

import isa.repository.ZahtevZaPrijateljstvoRepository;
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
 * REST controller for managing ZahtevZaPrijateljstvo.
 */
@RestController
@RequestMapping("/api")
public class ZahtevZaPrijateljstvoResource {

    private final Logger log = LoggerFactory.getLogger(ZahtevZaPrijateljstvoResource.class);

    private static final String ENTITY_NAME = "zahtevZaPrijateljstvo";

    private final ZahtevZaPrijateljstvoRepository zahtevZaPrijateljstvoRepository;
    public ZahtevZaPrijateljstvoResource(ZahtevZaPrijateljstvoRepository zahtevZaPrijateljstvoRepository) {
        this.zahtevZaPrijateljstvoRepository = zahtevZaPrijateljstvoRepository;
    }

    /**
     * POST  /zahtev-za-prijateljstvos : Create a new zahtevZaPrijateljstvo.
     *
     * @param zahtevZaPrijateljstvo the zahtevZaPrijateljstvo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zahtevZaPrijateljstvo, or with status 400 (Bad Request) if the zahtevZaPrijateljstvo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zahtev-za-prijateljstvos")
    @Timed
    public ResponseEntity<ZahtevZaPrijateljstvo> createZahtevZaPrijateljstvo(@RequestBody ZahtevZaPrijateljstvo zahtevZaPrijateljstvo) throws URISyntaxException {
        log.debug("REST request to save ZahtevZaPrijateljstvo : {}", zahtevZaPrijateljstvo);
        if (zahtevZaPrijateljstvo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zahtevZaPrijateljstvo cannot already have an ID")).body(null);
        }
        ZahtevZaPrijateljstvo result = zahtevZaPrijateljstvoRepository.save(zahtevZaPrijateljstvo);
        return ResponseEntity.created(new URI("/api/zahtev-za-prijateljstvos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zahtev-za-prijateljstvos : Updates an existing zahtevZaPrijateljstvo.
     *
     * @param zahtevZaPrijateljstvo the zahtevZaPrijateljstvo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zahtevZaPrijateljstvo,
     * or with status 400 (Bad Request) if the zahtevZaPrijateljstvo is not valid,
     * or with status 500 (Internal Server Error) if the zahtevZaPrijateljstvo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zahtev-za-prijateljstvos")
    @Timed
    public ResponseEntity<ZahtevZaPrijateljstvo> updateZahtevZaPrijateljstvo(@RequestBody ZahtevZaPrijateljstvo zahtevZaPrijateljstvo) throws URISyntaxException {
        log.debug("REST request to update ZahtevZaPrijateljstvo : {}", zahtevZaPrijateljstvo);
        if (zahtevZaPrijateljstvo.getId() == null) {
            return createZahtevZaPrijateljstvo(zahtevZaPrijateljstvo);
        }
        ZahtevZaPrijateljstvo result = zahtevZaPrijateljstvoRepository.save(zahtevZaPrijateljstvo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zahtevZaPrijateljstvo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zahtev-za-prijateljstvos : get all the zahtevZaPrijateljstvos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zahtevZaPrijateljstvos in body
     */
    @GetMapping("/zahtev-za-prijateljstvos")
    @Timed
    public List<ZahtevZaPrijateljstvo> getAllZahtevZaPrijateljstvos() {
        log.debug("REST request to get all ZahtevZaPrijateljstvos");
        return zahtevZaPrijateljstvoRepository.findAll();
        }

    /**
     * GET  /zahtev-za-prijateljstvos/:id : get the "id" zahtevZaPrijateljstvo.
     *
     * @param id the id of the zahtevZaPrijateljstvo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zahtevZaPrijateljstvo, or with status 404 (Not Found)
     */
    @GetMapping("/zahtev-za-prijateljstvos/{id}")
    @Timed
    public ResponseEntity<ZahtevZaPrijateljstvo> getZahtevZaPrijateljstvo(@PathVariable Long id) {
        log.debug("REST request to get ZahtevZaPrijateljstvo : {}", id);
        ZahtevZaPrijateljstvo zahtevZaPrijateljstvo = zahtevZaPrijateljstvoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zahtevZaPrijateljstvo));
    }

    /**
     * DELETE  /zahtev-za-prijateljstvos/:id : delete the "id" zahtevZaPrijateljstvo.
     *
     * @param id the id of the zahtevZaPrijateljstvo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zahtev-za-prijateljstvos/{id}")
    @Timed
    public ResponseEntity<Void> deleteZahtevZaPrijateljstvo(@PathVariable Long id) {
        log.debug("REST request to delete ZahtevZaPrijateljstvo : {}", id);
        zahtevZaPrijateljstvoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
