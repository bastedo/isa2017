package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Porudzbina;
import isa.repository.PorudzbinaRepository;
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
 * Test class for the PorudzbinaResource REST controller.
 *
 * @see PorudzbinaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class PorudzbinaResourceIntTest {

    private static final Boolean DEFAULT_PRIHVACENO_PICE = false;
    private static final Boolean UPDATED_PRIHVACENO_PICE = true;

    private static final Boolean DEFAULT_SPREMNO_PICE = false;
    private static final Boolean UPDATED_SPREMNO_PICE = true;

    private static final Boolean DEFAULT_PRIHVACENA_HRANA = false;
    private static final Boolean UPDATED_PRIHVACENA_HRANA = true;

    private static final Boolean DEFAULT_SPREMNA_HRANA = false;
    private static final Boolean UPDATED_SPREMNA_HRANA = true;

    @Autowired
    private PorudzbinaRepository porudzbinaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPorudzbinaMockMvc;

    private Porudzbina porudzbina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PorudzbinaResource porudzbinaResource = new PorudzbinaResource(porudzbinaRepository);
        this.restPorudzbinaMockMvc = MockMvcBuilders.standaloneSetup(porudzbinaResource)
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
    public static Porudzbina createEntity(EntityManager em) {
        Porudzbina porudzbina = new Porudzbina()
            .prihvacenoPice(DEFAULT_PRIHVACENO_PICE)
            .spremnoPice(DEFAULT_SPREMNO_PICE)
            .prihvacenaHrana(DEFAULT_PRIHVACENA_HRANA)
            .spremnaHrana(DEFAULT_SPREMNA_HRANA);
        return porudzbina;
    }

    @Before
    public void initTest() {
        porudzbina = createEntity(em);
    }

    @Test
    @Transactional
    public void createPorudzbina() throws Exception {
        int databaseSizeBeforeCreate = porudzbinaRepository.findAll().size();

        // Create the Porudzbina
        restPorudzbinaMockMvc.perform(post("/api/porudzbinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(porudzbina)))
            .andExpect(status().isCreated());

        // Validate the Porudzbina in the database
        List<Porudzbina> porudzbinaList = porudzbinaRepository.findAll();
        assertThat(porudzbinaList).hasSize(databaseSizeBeforeCreate + 1);
        Porudzbina testPorudzbina = porudzbinaList.get(porudzbinaList.size() - 1);
        assertThat(testPorudzbina.isPrihvacenoPice()).isEqualTo(DEFAULT_PRIHVACENO_PICE);
        assertThat(testPorudzbina.isSpremnoPice()).isEqualTo(DEFAULT_SPREMNO_PICE);
        assertThat(testPorudzbina.isPrihvacenaHrana()).isEqualTo(DEFAULT_PRIHVACENA_HRANA);
        assertThat(testPorudzbina.isSpremnaHrana()).isEqualTo(DEFAULT_SPREMNA_HRANA);
    }

    @Test
    @Transactional
    public void createPorudzbinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = porudzbinaRepository.findAll().size();

        // Create the Porudzbina with an existing ID
        porudzbina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPorudzbinaMockMvc.perform(post("/api/porudzbinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(porudzbina)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Porudzbina> porudzbinaList = porudzbinaRepository.findAll();
        assertThat(porudzbinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPorudzbinas() throws Exception {
        // Initialize the database
        porudzbinaRepository.saveAndFlush(porudzbina);

        // Get all the porudzbinaList
        restPorudzbinaMockMvc.perform(get("/api/porudzbinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(porudzbina.getId().intValue())))
            .andExpect(jsonPath("$.[*].prihvacenoPice").value(hasItem(DEFAULT_PRIHVACENO_PICE.booleanValue())))
            .andExpect(jsonPath("$.[*].spremnoPice").value(hasItem(DEFAULT_SPREMNO_PICE.booleanValue())))
            .andExpect(jsonPath("$.[*].prihvacenaHrana").value(hasItem(DEFAULT_PRIHVACENA_HRANA.booleanValue())))
            .andExpect(jsonPath("$.[*].spremnaHrana").value(hasItem(DEFAULT_SPREMNA_HRANA.booleanValue())));
    }

    @Test
    @Transactional
    public void getPorudzbina() throws Exception {
        // Initialize the database
        porudzbinaRepository.saveAndFlush(porudzbina);

        // Get the porudzbina
        restPorudzbinaMockMvc.perform(get("/api/porudzbinas/{id}", porudzbina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(porudzbina.getId().intValue()))
            .andExpect(jsonPath("$.prihvacenoPice").value(DEFAULT_PRIHVACENO_PICE.booleanValue()))
            .andExpect(jsonPath("$.spremnoPice").value(DEFAULT_SPREMNO_PICE.booleanValue()))
            .andExpect(jsonPath("$.prihvacenaHrana").value(DEFAULT_PRIHVACENA_HRANA.booleanValue()))
            .andExpect(jsonPath("$.spremnaHrana").value(DEFAULT_SPREMNA_HRANA.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPorudzbina() throws Exception {
        // Get the porudzbina
        restPorudzbinaMockMvc.perform(get("/api/porudzbinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePorudzbina() throws Exception {
        // Initialize the database
        porudzbinaRepository.saveAndFlush(porudzbina);
        int databaseSizeBeforeUpdate = porudzbinaRepository.findAll().size();

        // Update the porudzbina
        Porudzbina updatedPorudzbina = porudzbinaRepository.findOne(porudzbina.getId());
        updatedPorudzbina
            .prihvacenoPice(UPDATED_PRIHVACENO_PICE)
            .spremnoPice(UPDATED_SPREMNO_PICE)
            .prihvacenaHrana(UPDATED_PRIHVACENA_HRANA)
            .spremnaHrana(UPDATED_SPREMNA_HRANA);

        restPorudzbinaMockMvc.perform(put("/api/porudzbinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPorudzbina)))
            .andExpect(status().isOk());

        // Validate the Porudzbina in the database
        List<Porudzbina> porudzbinaList = porudzbinaRepository.findAll();
        assertThat(porudzbinaList).hasSize(databaseSizeBeforeUpdate);
        Porudzbina testPorudzbina = porudzbinaList.get(porudzbinaList.size() - 1);
        assertThat(testPorudzbina.isPrihvacenoPice()).isEqualTo(UPDATED_PRIHVACENO_PICE);
        assertThat(testPorudzbina.isSpremnoPice()).isEqualTo(UPDATED_SPREMNO_PICE);
        assertThat(testPorudzbina.isPrihvacenaHrana()).isEqualTo(UPDATED_PRIHVACENA_HRANA);
        assertThat(testPorudzbina.isSpremnaHrana()).isEqualTo(UPDATED_SPREMNA_HRANA);
    }

    @Test
    @Transactional
    public void updateNonExistingPorudzbina() throws Exception {
        int databaseSizeBeforeUpdate = porudzbinaRepository.findAll().size();

        // Create the Porudzbina

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPorudzbinaMockMvc.perform(put("/api/porudzbinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(porudzbina)))
            .andExpect(status().isCreated());

        // Validate the Porudzbina in the database
        List<Porudzbina> porudzbinaList = porudzbinaRepository.findAll();
        assertThat(porudzbinaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePorudzbina() throws Exception {
        // Initialize the database
        porudzbinaRepository.saveAndFlush(porudzbina);
        int databaseSizeBeforeDelete = porudzbinaRepository.findAll().size();

        // Get the porudzbina
        restPorudzbinaMockMvc.perform(delete("/api/porudzbinas/{id}", porudzbina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Porudzbina> porudzbinaList = porudzbinaRepository.findAll();
        assertThat(porudzbinaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Porudzbina.class);
        Porudzbina porudzbina1 = new Porudzbina();
        porudzbina1.setId(1L);
        Porudzbina porudzbina2 = new Porudzbina();
        porudzbina2.setId(porudzbina1.getId());
        assertThat(porudzbina1).isEqualTo(porudzbina2);
        porudzbina2.setId(2L);
        assertThat(porudzbina1).isNotEqualTo(porudzbina2);
        porudzbina1.setId(null);
        assertThat(porudzbina1).isNotEqualTo(porudzbina2);
    }
}
