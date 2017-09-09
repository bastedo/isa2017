package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Restoran;
import isa.repository.RestoranRepository;
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
 * Test class for the RestoranResource REST controller.
 *
 * @see RestoranResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class RestoranResourceIntTest {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final String DEFAULT_VRSTA = "AAAAAAAAAA";
    private static final String UPDATED_VRSTA = "BBBBBBBBBB";

    private static final Double DEFAULT_OCENA_RESTORANA = 1D;
    private static final Double UPDATED_OCENA_RESTORANA = 2D;

    @Autowired
    private RestoranRepository restoranRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRestoranMockMvc;

    private Restoran restoran;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestoranResource restoranResource = new RestoranResource(restoranRepository);
        this.restRestoranMockMvc = MockMvcBuilders.standaloneSetup(restoranResource)
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
    public static Restoran createEntity(EntityManager em) {
        Restoran restoran = new Restoran()
            .naziv(DEFAULT_NAZIV)
            .vrsta(DEFAULT_VRSTA)
            .ocenaRestorana(DEFAULT_OCENA_RESTORANA);
        return restoran;
    }

    @Before
    public void initTest() {
        restoran = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestoran() throws Exception {
        int databaseSizeBeforeCreate = restoranRepository.findAll().size();

        // Create the Restoran
        restRestoranMockMvc.perform(post("/api/restorans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restoran)))
            .andExpect(status().isCreated());

        // Validate the Restoran in the database
        List<Restoran> restoranList = restoranRepository.findAll();
        assertThat(restoranList).hasSize(databaseSizeBeforeCreate + 1);
        Restoran testRestoran = restoranList.get(restoranList.size() - 1);
        assertThat(testRestoran.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testRestoran.getVrsta()).isEqualTo(DEFAULT_VRSTA);
        assertThat(testRestoran.getOcenaRestorana()).isEqualTo(DEFAULT_OCENA_RESTORANA);
    }

    @Test
    @Transactional
    public void createRestoranWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restoranRepository.findAll().size();

        // Create the Restoran with an existing ID
        restoran.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestoranMockMvc.perform(post("/api/restorans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restoran)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Restoran> restoranList = restoranRepository.findAll();
        assertThat(restoranList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRestorans() throws Exception {
        // Initialize the database
        restoranRepository.saveAndFlush(restoran);

        // Get all the restoranList
        restRestoranMockMvc.perform(get("/api/restorans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restoran.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].vrsta").value(hasItem(DEFAULT_VRSTA.toString())))
            .andExpect(jsonPath("$.[*].ocenaRestorana").value(hasItem(DEFAULT_OCENA_RESTORANA.doubleValue())));
    }

    @Test
    @Transactional
    public void getRestoran() throws Exception {
        // Initialize the database
        restoranRepository.saveAndFlush(restoran);

        // Get the restoran
        restRestoranMockMvc.perform(get("/api/restorans/{id}", restoran.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restoran.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.vrsta").value(DEFAULT_VRSTA.toString()))
            .andExpect(jsonPath("$.ocenaRestorana").value(DEFAULT_OCENA_RESTORANA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRestoran() throws Exception {
        // Get the restoran
        restRestoranMockMvc.perform(get("/api/restorans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestoran() throws Exception {
        // Initialize the database
        restoranRepository.saveAndFlush(restoran);
        int databaseSizeBeforeUpdate = restoranRepository.findAll().size();

        // Update the restoran
        Restoran updatedRestoran = restoranRepository.findOne(restoran.getId());
        updatedRestoran
            .naziv(UPDATED_NAZIV)
            .vrsta(UPDATED_VRSTA)
            .ocenaRestorana(UPDATED_OCENA_RESTORANA);

        restRestoranMockMvc.perform(put("/api/restorans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRestoran)))
            .andExpect(status().isOk());

        // Validate the Restoran in the database
        List<Restoran> restoranList = restoranRepository.findAll();
        assertThat(restoranList).hasSize(databaseSizeBeforeUpdate);
        Restoran testRestoran = restoranList.get(restoranList.size() - 1);
        assertThat(testRestoran.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testRestoran.getVrsta()).isEqualTo(UPDATED_VRSTA);
        assertThat(testRestoran.getOcenaRestorana()).isEqualTo(UPDATED_OCENA_RESTORANA);
    }

    @Test
    @Transactional
    public void updateNonExistingRestoran() throws Exception {
        int databaseSizeBeforeUpdate = restoranRepository.findAll().size();

        // Create the Restoran

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRestoranMockMvc.perform(put("/api/restorans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restoran)))
            .andExpect(status().isCreated());

        // Validate the Restoran in the database
        List<Restoran> restoranList = restoranRepository.findAll();
        assertThat(restoranList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRestoran() throws Exception {
        // Initialize the database
        restoranRepository.saveAndFlush(restoran);
        int databaseSizeBeforeDelete = restoranRepository.findAll().size();

        // Get the restoran
        restRestoranMockMvc.perform(delete("/api/restorans/{id}", restoran.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Restoran> restoranList = restoranRepository.findAll();
        assertThat(restoranList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Restoran.class);
        Restoran restoran1 = new Restoran();
        restoran1.setId(1L);
        Restoran restoran2 = new Restoran();
        restoran2.setId(restoran1.getId());
        assertThat(restoran1).isEqualTo(restoran2);
        restoran2.setId(2L);
        assertThat(restoran1).isNotEqualTo(restoran2);
        restoran1.setId(null);
        assertThat(restoran1).isNotEqualTo(restoran2);
    }
}
