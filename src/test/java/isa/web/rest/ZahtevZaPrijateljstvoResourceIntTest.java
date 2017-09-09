package isa.web.rest;

import isa.Isa2017App;

import isa.domain.ZahtevZaPrijateljstvo;
import isa.repository.ZahtevZaPrijateljstvoRepository;
import isa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ZahtevZaPrijateljstvoResource REST controller.
 *
 * @see ZahtevZaPrijateljstvoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class ZahtevZaPrijateljstvoResourceIntTest {

    private static final Integer DEFAULT_ID_PODNOSIOCA_ZAHTEVA = 1;
    private static final Integer UPDATED_ID_PODNOSIOCA_ZAHTEVA = 2;

    private static final LocalDate DEFAULT_POSTALAN_ZAHTEV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTALAN_ZAHTEV = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PRIHVACEN_ZAHTEV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRIHVACEN_ZAHTEV = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PRIHVACEN = false;
    private static final Boolean UPDATED_PRIHVACEN = true;

    private static final Boolean DEFAULT_ODBIJEN = false;
    private static final Boolean UPDATED_ODBIJEN = true;

    @Autowired
    private ZahtevZaPrijateljstvoRepository zahtevZaPrijateljstvoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZahtevZaPrijateljstvoMockMvc;

    private ZahtevZaPrijateljstvo zahtevZaPrijateljstvo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZahtevZaPrijateljstvoResource zahtevZaPrijateljstvoResource = new ZahtevZaPrijateljstvoResource(zahtevZaPrijateljstvoRepository);
        this.restZahtevZaPrijateljstvoMockMvc = MockMvcBuilders.standaloneSetup(zahtevZaPrijateljstvoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZahtevZaPrijateljstvo createEntity(EntityManager em) {
        ZahtevZaPrijateljstvo zahtevZaPrijateljstvo = new ZahtevZaPrijateljstvo()
            .idPodnosiocaZahteva(DEFAULT_ID_PODNOSIOCA_ZAHTEVA)
            .postalanZahtev(DEFAULT_POSTALAN_ZAHTEV)
            .prihvacenZahtev(DEFAULT_PRIHVACEN_ZAHTEV)
            .prihvacen(DEFAULT_PRIHVACEN)
            .odbijen(DEFAULT_ODBIJEN);
        return zahtevZaPrijateljstvo;
    }

    @Before
    public void initTest() {
        zahtevZaPrijateljstvo = createEntity(em);
    }

    @Test
    @Transactional
    public void createZahtevZaPrijateljstvo() throws Exception {
        int databaseSizeBeforeCreate = zahtevZaPrijateljstvoRepository.findAll().size();

        // Create the ZahtevZaPrijateljstvo
        restZahtevZaPrijateljstvoMockMvc.perform(post("/api/zahtev-za-prijateljstvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zahtevZaPrijateljstvo)))
            .andExpect(status().isCreated());

        // Validate the ZahtevZaPrijateljstvo in the database
        List<ZahtevZaPrijateljstvo> zahtevZaPrijateljstvoList = zahtevZaPrijateljstvoRepository.findAll();
        assertThat(zahtevZaPrijateljstvoList).hasSize(databaseSizeBeforeCreate + 1);
        ZahtevZaPrijateljstvo testZahtevZaPrijateljstvo = zahtevZaPrijateljstvoList.get(zahtevZaPrijateljstvoList.size() - 1);
        assertThat(testZahtevZaPrijateljstvo.getIdPodnosiocaZahteva()).isEqualTo(DEFAULT_ID_PODNOSIOCA_ZAHTEVA);
        assertThat(testZahtevZaPrijateljstvo.getPostalanZahtev()).isEqualTo(DEFAULT_POSTALAN_ZAHTEV);
        assertThat(testZahtevZaPrijateljstvo.getPrihvacenZahtev()).isEqualTo(DEFAULT_PRIHVACEN_ZAHTEV);
        assertThat(testZahtevZaPrijateljstvo.isPrihvacen()).isEqualTo(DEFAULT_PRIHVACEN);
        assertThat(testZahtevZaPrijateljstvo.isOdbijen()).isEqualTo(DEFAULT_ODBIJEN);
    }

    @Test
    @Transactional
    public void createZahtevZaPrijateljstvoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zahtevZaPrijateljstvoRepository.findAll().size();

        // Create the ZahtevZaPrijateljstvo with an existing ID
        zahtevZaPrijateljstvo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZahtevZaPrijateljstvoMockMvc.perform(post("/api/zahtev-za-prijateljstvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zahtevZaPrijateljstvo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ZahtevZaPrijateljstvo> zahtevZaPrijateljstvoList = zahtevZaPrijateljstvoRepository.findAll();
        assertThat(zahtevZaPrijateljstvoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllZahtevZaPrijateljstvos() throws Exception {
        // Initialize the database
        zahtevZaPrijateljstvoRepository.saveAndFlush(zahtevZaPrijateljstvo);

        // Get all the zahtevZaPrijateljstvoList
        restZahtevZaPrijateljstvoMockMvc.perform(get("/api/zahtev-za-prijateljstvos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zahtevZaPrijateljstvo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPodnosiocaZahteva").value(hasItem(DEFAULT_ID_PODNOSIOCA_ZAHTEVA)))
            .andExpect(jsonPath("$.[*].postalanZahtev").value(hasItem(DEFAULT_POSTALAN_ZAHTEV.toString())))
            .andExpect(jsonPath("$.[*].prihvacenZahtev").value(hasItem(DEFAULT_PRIHVACEN_ZAHTEV.toString())))
            .andExpect(jsonPath("$.[*].prihvacen").value(hasItem(DEFAULT_PRIHVACEN.booleanValue())))
            .andExpect(jsonPath("$.[*].odbijen").value(hasItem(DEFAULT_ODBIJEN.booleanValue())));
    }

    @Test
    @Transactional
    public void getZahtevZaPrijateljstvo() throws Exception {
        // Initialize the database
        zahtevZaPrijateljstvoRepository.saveAndFlush(zahtevZaPrijateljstvo);

        // Get the zahtevZaPrijateljstvo
        restZahtevZaPrijateljstvoMockMvc.perform(get("/api/zahtev-za-prijateljstvos/{id}", zahtevZaPrijateljstvo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zahtevZaPrijateljstvo.getId().intValue()))
            .andExpect(jsonPath("$.idPodnosiocaZahteva").value(DEFAULT_ID_PODNOSIOCA_ZAHTEVA))
            .andExpect(jsonPath("$.postalanZahtev").value(DEFAULT_POSTALAN_ZAHTEV.toString()))
            .andExpect(jsonPath("$.prihvacenZahtev").value(DEFAULT_PRIHVACEN_ZAHTEV.toString()))
            .andExpect(jsonPath("$.prihvacen").value(DEFAULT_PRIHVACEN.booleanValue()))
            .andExpect(jsonPath("$.odbijen").value(DEFAULT_ODBIJEN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingZahtevZaPrijateljstvo() throws Exception {
        // Get the zahtevZaPrijateljstvo
        restZahtevZaPrijateljstvoMockMvc.perform(get("/api/zahtev-za-prijateljstvos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZahtevZaPrijateljstvo() throws Exception {
        // Initialize the database
        zahtevZaPrijateljstvoRepository.saveAndFlush(zahtevZaPrijateljstvo);
        int databaseSizeBeforeUpdate = zahtevZaPrijateljstvoRepository.findAll().size();

        // Update the zahtevZaPrijateljstvo
        ZahtevZaPrijateljstvo updatedZahtevZaPrijateljstvo = zahtevZaPrijateljstvoRepository.findOne(zahtevZaPrijateljstvo.getId());
        updatedZahtevZaPrijateljstvo
            .idPodnosiocaZahteva(UPDATED_ID_PODNOSIOCA_ZAHTEVA)
            .postalanZahtev(UPDATED_POSTALAN_ZAHTEV)
            .prihvacenZahtev(UPDATED_PRIHVACEN_ZAHTEV)
            .prihvacen(UPDATED_PRIHVACEN)
            .odbijen(UPDATED_ODBIJEN);

        restZahtevZaPrijateljstvoMockMvc.perform(put("/api/zahtev-za-prijateljstvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZahtevZaPrijateljstvo)))
            .andExpect(status().isOk());

        // Validate the ZahtevZaPrijateljstvo in the database
        List<ZahtevZaPrijateljstvo> zahtevZaPrijateljstvoList = zahtevZaPrijateljstvoRepository.findAll();
        assertThat(zahtevZaPrijateljstvoList).hasSize(databaseSizeBeforeUpdate);
        ZahtevZaPrijateljstvo testZahtevZaPrijateljstvo = zahtevZaPrijateljstvoList.get(zahtevZaPrijateljstvoList.size() - 1);
        assertThat(testZahtevZaPrijateljstvo.getIdPodnosiocaZahteva()).isEqualTo(UPDATED_ID_PODNOSIOCA_ZAHTEVA);
        assertThat(testZahtevZaPrijateljstvo.getPostalanZahtev()).isEqualTo(UPDATED_POSTALAN_ZAHTEV);
        assertThat(testZahtevZaPrijateljstvo.getPrihvacenZahtev()).isEqualTo(UPDATED_PRIHVACEN_ZAHTEV);
        assertThat(testZahtevZaPrijateljstvo.isPrihvacen()).isEqualTo(UPDATED_PRIHVACEN);
        assertThat(testZahtevZaPrijateljstvo.isOdbijen()).isEqualTo(UPDATED_ODBIJEN);
    }

    @Test
    @Transactional
    public void updateNonExistingZahtevZaPrijateljstvo() throws Exception {
        int databaseSizeBeforeUpdate = zahtevZaPrijateljstvoRepository.findAll().size();

        // Create the ZahtevZaPrijateljstvo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZahtevZaPrijateljstvoMockMvc.perform(put("/api/zahtev-za-prijateljstvos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zahtevZaPrijateljstvo)))
            .andExpect(status().isCreated());

        // Validate the ZahtevZaPrijateljstvo in the database
        List<ZahtevZaPrijateljstvo> zahtevZaPrijateljstvoList = zahtevZaPrijateljstvoRepository.findAll();
        assertThat(zahtevZaPrijateljstvoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteZahtevZaPrijateljstvo() throws Exception {
        // Initialize the database
        zahtevZaPrijateljstvoRepository.saveAndFlush(zahtevZaPrijateljstvo);
        int databaseSizeBeforeDelete = zahtevZaPrijateljstvoRepository.findAll().size();

        // Get the zahtevZaPrijateljstvo
        restZahtevZaPrijateljstvoMockMvc.perform(delete("/api/zahtev-za-prijateljstvos/{id}", zahtevZaPrijateljstvo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZahtevZaPrijateljstvo> zahtevZaPrijateljstvoList = zahtevZaPrijateljstvoRepository.findAll();
        assertThat(zahtevZaPrijateljstvoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZahtevZaPrijateljstvo.class);
        ZahtevZaPrijateljstvo zahtevZaPrijateljstvo1 = new ZahtevZaPrijateljstvo();
        zahtevZaPrijateljstvo1.setId(1L);
        ZahtevZaPrijateljstvo zahtevZaPrijateljstvo2 = new ZahtevZaPrijateljstvo();
        zahtevZaPrijateljstvo2.setId(zahtevZaPrijateljstvo1.getId());
        assertThat(zahtevZaPrijateljstvo1).isEqualTo(zahtevZaPrijateljstvo2);
        zahtevZaPrijateljstvo2.setId(2L);
        assertThat(zahtevZaPrijateljstvo1).isNotEqualTo(zahtevZaPrijateljstvo2);
        zahtevZaPrijateljstvo1.setId(null);
        assertThat(zahtevZaPrijateljstvo1).isNotEqualTo(zahtevZaPrijateljstvo2);
    }
}
