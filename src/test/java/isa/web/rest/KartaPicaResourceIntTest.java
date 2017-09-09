package isa.web.rest;

import isa.Isa2017App;

import isa.domain.KartaPica;
import isa.repository.KartaPicaRepository;
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
 * Test class for the KartaPicaResource REST controller.
 *
 * @see KartaPicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class KartaPicaResourceIntTest {

    private static final String DEFAULT_IME_KARTE_PICA = "AAAAAAAAAA";
    private static final String UPDATED_IME_KARTE_PICA = "BBBBBBBBBB";

    @Autowired
    private KartaPicaRepository kartaPicaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKartaPicaMockMvc;

    private KartaPica kartaPica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KartaPicaResource kartaPicaResource = new KartaPicaResource(kartaPicaRepository);
        this.restKartaPicaMockMvc = MockMvcBuilders.standaloneSetup(kartaPicaResource)
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
    public static KartaPica createEntity(EntityManager em) {
        KartaPica kartaPica = new KartaPica()
            .imeKartePica(DEFAULT_IME_KARTE_PICA);
        return kartaPica;
    }

    @Before
    public void initTest() {
        kartaPica = createEntity(em);
    }

    @Test
    @Transactional
    public void createKartaPica() throws Exception {
        int databaseSizeBeforeCreate = kartaPicaRepository.findAll().size();

        // Create the KartaPica
        restKartaPicaMockMvc.perform(post("/api/karta-picas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPica)))
            .andExpect(status().isCreated());

        // Validate the KartaPica in the database
        List<KartaPica> kartaPicaList = kartaPicaRepository.findAll();
        assertThat(kartaPicaList).hasSize(databaseSizeBeforeCreate + 1);
        KartaPica testKartaPica = kartaPicaList.get(kartaPicaList.size() - 1);
        assertThat(testKartaPica.getImeKartePica()).isEqualTo(DEFAULT_IME_KARTE_PICA);
    }

    @Test
    @Transactional
    public void createKartaPicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kartaPicaRepository.findAll().size();

        // Create the KartaPica with an existing ID
        kartaPica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKartaPicaMockMvc.perform(post("/api/karta-picas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPica)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<KartaPica> kartaPicaList = kartaPicaRepository.findAll();
        assertThat(kartaPicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKartaPicas() throws Exception {
        // Initialize the database
        kartaPicaRepository.saveAndFlush(kartaPica);

        // Get all the kartaPicaList
        restKartaPicaMockMvc.perform(get("/api/karta-picas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kartaPica.getId().intValue())))
            .andExpect(jsonPath("$.[*].imeKartePica").value(hasItem(DEFAULT_IME_KARTE_PICA.toString())));
    }

    @Test
    @Transactional
    public void getKartaPica() throws Exception {
        // Initialize the database
        kartaPicaRepository.saveAndFlush(kartaPica);

        // Get the kartaPica
        restKartaPicaMockMvc.perform(get("/api/karta-picas/{id}", kartaPica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kartaPica.getId().intValue()))
            .andExpect(jsonPath("$.imeKartePica").value(DEFAULT_IME_KARTE_PICA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKartaPica() throws Exception {
        // Get the kartaPica
        restKartaPicaMockMvc.perform(get("/api/karta-picas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKartaPica() throws Exception {
        // Initialize the database
        kartaPicaRepository.saveAndFlush(kartaPica);
        int databaseSizeBeforeUpdate = kartaPicaRepository.findAll().size();

        // Update the kartaPica
        KartaPica updatedKartaPica = kartaPicaRepository.findOne(kartaPica.getId());
        updatedKartaPica
            .imeKartePica(UPDATED_IME_KARTE_PICA);

        restKartaPicaMockMvc.perform(put("/api/karta-picas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKartaPica)))
            .andExpect(status().isOk());

        // Validate the KartaPica in the database
        List<KartaPica> kartaPicaList = kartaPicaRepository.findAll();
        assertThat(kartaPicaList).hasSize(databaseSizeBeforeUpdate);
        KartaPica testKartaPica = kartaPicaList.get(kartaPicaList.size() - 1);
        assertThat(testKartaPica.getImeKartePica()).isEqualTo(UPDATED_IME_KARTE_PICA);
    }

    @Test
    @Transactional
    public void updateNonExistingKartaPica() throws Exception {
        int databaseSizeBeforeUpdate = kartaPicaRepository.findAll().size();

        // Create the KartaPica

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKartaPicaMockMvc.perform(put("/api/karta-picas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kartaPica)))
            .andExpect(status().isCreated());

        // Validate the KartaPica in the database
        List<KartaPica> kartaPicaList = kartaPicaRepository.findAll();
        assertThat(kartaPicaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKartaPica() throws Exception {
        // Initialize the database
        kartaPicaRepository.saveAndFlush(kartaPica);
        int databaseSizeBeforeDelete = kartaPicaRepository.findAll().size();

        // Get the kartaPica
        restKartaPicaMockMvc.perform(delete("/api/karta-picas/{id}", kartaPica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<KartaPica> kartaPicaList = kartaPicaRepository.findAll();
        assertThat(kartaPicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KartaPica.class);
        KartaPica kartaPica1 = new KartaPica();
        kartaPica1.setId(1L);
        KartaPica kartaPica2 = new KartaPica();
        kartaPica2.setId(kartaPica1.getId());
        assertThat(kartaPica1).isEqualTo(kartaPica2);
        kartaPica2.setId(2L);
        assertThat(kartaPica1).isNotEqualTo(kartaPica2);
        kartaPica1.setId(null);
        assertThat(kartaPica1).isNotEqualTo(kartaPica2);
    }
}
