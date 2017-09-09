package isa.web.rest;

import isa.Isa2017App;

import isa.domain.KonfiguracijaStolova;
import isa.repository.KonfiguracijaStolovaRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KonfiguracijaStolovaResource REST controller.
 *
 * @see KonfiguracijaStolovaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class KonfiguracijaStolovaResourceIntTest {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    @Autowired
    private KonfiguracijaStolovaRepository konfiguracijaStolovaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKonfiguracijaStolovaMockMvc;

    private KonfiguracijaStolova konfiguracijaStolova;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KonfiguracijaStolovaResource konfiguracijaStolovaResource = new KonfiguracijaStolovaResource(konfiguracijaStolovaRepository);
        this.restKonfiguracijaStolovaMockMvc = MockMvcBuilders.standaloneSetup(konfiguracijaStolovaResource)
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
    public static KonfiguracijaStolova createEntity(EntityManager em) {
        KonfiguracijaStolova konfiguracijaStolova = new KonfiguracijaStolova()
            .ime(DEFAULT_IME);
        return konfiguracijaStolova;
    }

    @Before
    public void initTest() {
        konfiguracijaStolova = createEntity(em);
    }

    @Test
    @Transactional
    public void createKonfiguracijaStolova() throws Exception {
        int databaseSizeBeforeCreate = konfiguracijaStolovaRepository.findAll().size();

        // Create the KonfiguracijaStolova
        restKonfiguracijaStolovaMockMvc.perform(post("/api/konfiguracija-stolova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(konfiguracijaStolova)))
            .andExpect(status().isCreated());

        // Validate the KonfiguracijaStolova in the database
        List<KonfiguracijaStolova> konfiguracijaStolovaList = konfiguracijaStolovaRepository.findAll();
        assertThat(konfiguracijaStolovaList).hasSize(databaseSizeBeforeCreate + 1);
        KonfiguracijaStolova testKonfiguracijaStolova = konfiguracijaStolovaList.get(konfiguracijaStolovaList.size() - 1);
        assertThat(testKonfiguracijaStolova.getIme()).isEqualTo(DEFAULT_IME);
    }

    @Test
    @Transactional
    public void createKonfiguracijaStolovaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = konfiguracijaStolovaRepository.findAll().size();

        // Create the KonfiguracijaStolova with an existing ID
        konfiguracijaStolova.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKonfiguracijaStolovaMockMvc.perform(post("/api/konfiguracija-stolova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(konfiguracijaStolova)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KonfiguracijaStolova> konfiguracijaStolovaList = konfiguracijaStolovaRepository.findAll();
        assertThat(konfiguracijaStolovaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKonfiguracijaStolova() throws Exception {
        // Initialize the database
        konfiguracijaStolovaRepository.saveAndFlush(konfiguracijaStolova);

        // Get all the konfiguracijaStolovaList
        restKonfiguracijaStolovaMockMvc.perform(get("/api/konfiguracija-stolova?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(konfiguracijaStolova.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())));
    }

    @Test
    @Transactional
    public void getKonfiguracijaStolova() throws Exception {
        // Initialize the database
        konfiguracijaStolovaRepository.saveAndFlush(konfiguracijaStolova);

        // Get the konfiguracijaStolova
        restKonfiguracijaStolovaMockMvc.perform(get("/api/konfiguracija-stolova/{id}", konfiguracijaStolova.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(konfiguracijaStolova.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKonfiguracijaStolova() throws Exception {
        // Get the konfiguracijaStolova
        restKonfiguracijaStolovaMockMvc.perform(get("/api/konfiguracija-stolova/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKonfiguracijaStolova() throws Exception {
        // Initialize the database
        konfiguracijaStolovaRepository.saveAndFlush(konfiguracijaStolova);
        int databaseSizeBeforeUpdate = konfiguracijaStolovaRepository.findAll().size();

        // Update the konfiguracijaStolova
        KonfiguracijaStolova updatedKonfiguracijaStolova = konfiguracijaStolovaRepository.findOne(konfiguracijaStolova.getId());
        updatedKonfiguracijaStolova
            .ime(UPDATED_IME);

        restKonfiguracijaStolovaMockMvc.perform(put("/api/konfiguracija-stolova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKonfiguracijaStolova)))
            .andExpect(status().isOk());

        // Validate the KonfiguracijaStolova in the database
        List<KonfiguracijaStolova> konfiguracijaStolovaList = konfiguracijaStolovaRepository.findAll();
        assertThat(konfiguracijaStolovaList).hasSize(databaseSizeBeforeUpdate);
        KonfiguracijaStolova testKonfiguracijaStolova = konfiguracijaStolovaList.get(konfiguracijaStolovaList.size() - 1);
        assertThat(testKonfiguracijaStolova.getIme()).isEqualTo(UPDATED_IME);
    }

    @Test
    @Transactional
    public void updateNonExistingKonfiguracijaStolova() throws Exception {
        int databaseSizeBeforeUpdate = konfiguracijaStolovaRepository.findAll().size();

        // Create the KonfiguracijaStolova

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKonfiguracijaStolovaMockMvc.perform(put("/api/konfiguracija-stolova")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(konfiguracijaStolova)))
            .andExpect(status().isCreated());

        // Validate the KonfiguracijaStolova in the database
        List<KonfiguracijaStolova> konfiguracijaStolovaList = konfiguracijaStolovaRepository.findAll();
        assertThat(konfiguracijaStolovaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKonfiguracijaStolova() throws Exception {
        // Initialize the database
        konfiguracijaStolovaRepository.saveAndFlush(konfiguracijaStolova);
        int databaseSizeBeforeDelete = konfiguracijaStolovaRepository.findAll().size();

        // Get the konfiguracijaStolova
        restKonfiguracijaStolovaMockMvc.perform(delete("/api/konfiguracija-stolova/{id}", konfiguracijaStolova.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KonfiguracijaStolova> konfiguracijaStolovaList = konfiguracijaStolovaRepository.findAll();
        assertThat(konfiguracijaStolovaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KonfiguracijaStolova.class);
        KonfiguracijaStolova konfiguracijaStolova1 = new KonfiguracijaStolova();
        konfiguracijaStolova1.setId(1L);
        KonfiguracijaStolova konfiguracijaStolova2 = new KonfiguracijaStolova();
        konfiguracijaStolova2.setId(konfiguracijaStolova1.getId());
        assertThat(konfiguracijaStolova1).isEqualTo(konfiguracijaStolova2);
        konfiguracijaStolova2.setId(2L);
        assertThat(konfiguracijaStolova1).isNotEqualTo(konfiguracijaStolova2);
        konfiguracijaStolova1.setId(null);
        assertThat(konfiguracijaStolova1).isNotEqualTo(konfiguracijaStolova2);
    }
}
