package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Ocena;
import isa.repository.OcenaRepository;
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
 * Test class for the OcenaResource REST controller.
 *
 * @see OcenaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class OcenaResourceIntTest {

    private static final Double DEFAULT_OCENA = 1D;
    private static final Double UPDATED_OCENA = 2D;

    @Autowired
    private OcenaRepository ocenaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOcenaMockMvc;

    private Ocena ocena;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OcenaResource ocenaResource = new OcenaResource(ocenaRepository);
        this.restOcenaMockMvc = MockMvcBuilders.standaloneSetup(ocenaResource)
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
    public static Ocena createEntity(EntityManager em) {
        Ocena ocena = new Ocena()
            .ocena(DEFAULT_OCENA);
        return ocena;
    }

    @Before
    public void initTest() {
        ocena = createEntity(em);
    }

    @Test
    @Transactional
    public void createOcena() throws Exception {
        int databaseSizeBeforeCreate = ocenaRepository.findAll().size();

        // Create the Ocena
        restOcenaMockMvc.perform(post("/api/ocenas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocena)))
            .andExpect(status().isCreated());

        // Validate the Ocena in the database
        List<Ocena> ocenaList = ocenaRepository.findAll();
        assertThat(ocenaList).hasSize(databaseSizeBeforeCreate + 1);
        Ocena testOcena = ocenaList.get(ocenaList.size() - 1);
        assertThat(testOcena.getOcena()).isEqualTo(DEFAULT_OCENA);
    }

    @Test
    @Transactional
    public void createOcenaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ocenaRepository.findAll().size();

        // Create the Ocena with an existing ID
        ocena.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcenaMockMvc.perform(post("/api/ocenas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocena)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ocena> ocenaList = ocenaRepository.findAll();
        assertThat(ocenaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOcenas() throws Exception {
        // Initialize the database
        ocenaRepository.saveAndFlush(ocena);

        // Get all the ocenaList
        restOcenaMockMvc.perform(get("/api/ocenas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocena.getId().intValue())))
            .andExpect(jsonPath("$.[*].ocena").value(hasItem(DEFAULT_OCENA.doubleValue())));
    }

    @Test
    @Transactional
    public void getOcena() throws Exception {
        // Initialize the database
        ocenaRepository.saveAndFlush(ocena);

        // Get the ocena
        restOcenaMockMvc.perform(get("/api/ocenas/{id}", ocena.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ocena.getId().intValue()))
            .andExpect(jsonPath("$.ocena").value(DEFAULT_OCENA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOcena() throws Exception {
        // Get the ocena
        restOcenaMockMvc.perform(get("/api/ocenas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOcena() throws Exception {
        // Initialize the database
        ocenaRepository.saveAndFlush(ocena);
        int databaseSizeBeforeUpdate = ocenaRepository.findAll().size();

        // Update the ocena
        Ocena updatedOcena = ocenaRepository.findOne(ocena.getId());
        updatedOcena
            .ocena(UPDATED_OCENA);

        restOcenaMockMvc.perform(put("/api/ocenas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOcena)))
            .andExpect(status().isOk());

        // Validate the Ocena in the database
        List<Ocena> ocenaList = ocenaRepository.findAll();
        assertThat(ocenaList).hasSize(databaseSizeBeforeUpdate);
        Ocena testOcena = ocenaList.get(ocenaList.size() - 1);
        assertThat(testOcena.getOcena()).isEqualTo(UPDATED_OCENA);
    }

    @Test
    @Transactional
    public void updateNonExistingOcena() throws Exception {
        int databaseSizeBeforeUpdate = ocenaRepository.findAll().size();

        // Create the Ocena

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOcenaMockMvc.perform(put("/api/ocenas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ocena)))
            .andExpect(status().isCreated());

        // Validate the Ocena in the database
        List<Ocena> ocenaList = ocenaRepository.findAll();
        assertThat(ocenaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOcena() throws Exception {
        // Initialize the database
        ocenaRepository.saveAndFlush(ocena);
        int databaseSizeBeforeDelete = ocenaRepository.findAll().size();

        // Get the ocena
        restOcenaMockMvc.perform(delete("/api/ocenas/{id}", ocena.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ocena> ocenaList = ocenaRepository.findAll();
        assertThat(ocenaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ocena.class);
        Ocena ocena1 = new Ocena();
        ocena1.setId(1L);
        Ocena ocena2 = new Ocena();
        ocena2.setId(ocena1.getId());
        assertThat(ocena1).isEqualTo(ocena2);
        ocena2.setId(2L);
        assertThat(ocena1).isNotEqualTo(ocena2);
        ocena1.setId(null);
        assertThat(ocena1).isNotEqualTo(ocena2);
    }
}
