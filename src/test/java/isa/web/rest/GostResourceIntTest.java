package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Gost;
import isa.repository.GostRepository;
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
 * Test class for the GostResource REST controller.
 *
 * @see GostResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class GostResourceIntTest {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LOZINKA = "AAAAAAAAAA";
    private static final String UPDATED_LOZINKA = "BBBBBBBBBB";

    @Autowired
    private GostRepository gostRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGostMockMvc;

    private Gost gost;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GostResource gostResource = new GostResource(gostRepository);
        this.restGostMockMvc = MockMvcBuilders.standaloneSetup(gostResource)
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
    public static Gost createEntity(EntityManager em) {
        Gost gost = new Gost()
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .email(DEFAULT_EMAIL)
            .lozinka(DEFAULT_LOZINKA);
        return gost;
    }

    @Before
    public void initTest() {
        gost = createEntity(em);
    }

    @Test
    @Transactional
    public void createGost() throws Exception {
        int databaseSizeBeforeCreate = gostRepository.findAll().size();

        // Create the Gost
        restGostMockMvc.perform(post("/api/gosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gost)))
            .andExpect(status().isCreated());

        // Validate the Gost in the database
        List<Gost> gostList = gostRepository.findAll();
        assertThat(gostList).hasSize(databaseSizeBeforeCreate + 1);
        Gost testGost = gostList.get(gostList.size() - 1);
        assertThat(testGost.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testGost.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testGost.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGost.getLozinka()).isEqualTo(DEFAULT_LOZINKA);
    }

    @Test
    @Transactional
    public void createGostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gostRepository.findAll().size();

        // Create the Gost with an existing ID
        gost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGostMockMvc.perform(post("/api/gosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gost)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Gost> gostList = gostRepository.findAll();
        assertThat(gostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGosts() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);

        // Get all the gostList
        restGostMockMvc.perform(get("/api/gosts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gost.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].lozinka").value(hasItem(DEFAULT_LOZINKA.toString())));
    }

    @Test
    @Transactional
    public void getGost() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);

        // Get the gost
        restGostMockMvc.perform(get("/api/gosts/{id}", gost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gost.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.lozinka").value(DEFAULT_LOZINKA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGost() throws Exception {
        // Get the gost
        restGostMockMvc.perform(get("/api/gosts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGost() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);
        int databaseSizeBeforeUpdate = gostRepository.findAll().size();

        // Update the gost
        Gost updatedGost = gostRepository.findOne(gost.getId());
        updatedGost
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .email(UPDATED_EMAIL)
            .lozinka(UPDATED_LOZINKA);

        restGostMockMvc.perform(put("/api/gosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGost)))
            .andExpect(status().isOk());

        // Validate the Gost in the database
        List<Gost> gostList = gostRepository.findAll();
        assertThat(gostList).hasSize(databaseSizeBeforeUpdate);
        Gost testGost = gostList.get(gostList.size() - 1);
        assertThat(testGost.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testGost.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testGost.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGost.getLozinka()).isEqualTo(UPDATED_LOZINKA);
    }

    @Test
    @Transactional
    public void updateNonExistingGost() throws Exception {
        int databaseSizeBeforeUpdate = gostRepository.findAll().size();

        // Create the Gost

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGostMockMvc.perform(put("/api/gosts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gost)))
            .andExpect(status().isCreated());

        // Validate the Gost in the database
        List<Gost> gostList = gostRepository.findAll();
        assertThat(gostList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGost() throws Exception {
        // Initialize the database
        gostRepository.saveAndFlush(gost);
        int databaseSizeBeforeDelete = gostRepository.findAll().size();

        // Get the gost
        restGostMockMvc.perform(delete("/api/gosts/{id}", gost.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gost> gostList = gostRepository.findAll();
        assertThat(gostList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gost.class);
        Gost gost1 = new Gost();
        gost1.setId(1L);
        Gost gost2 = new Gost();
        gost2.setId(gost1.getId());
        assertThat(gost1).isEqualTo(gost2);
        gost2.setId(2L);
        assertThat(gost1).isNotEqualTo(gost2);
        gost1.setId(null);
        assertThat(gost1).isNotEqualTo(gost2);
    }
}
