package isa.web.rest;

import isa.Isa2017App;

import isa.domain.MenadzerRestorana;
import isa.repository.MenadzerRestoranaRepository;
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
 * Test class for the MenadzerRestoranaResource REST controller.
 *
 * @see MenadzerRestoranaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class MenadzerRestoranaResourceIntTest {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LOZINKA = "AAAAAAAAAA";
    private static final String UPDATED_LOZINKA = "BBBBBBBBBB";

    @Autowired
    private MenadzerRestoranaRepository menadzerRestoranaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMenadzerRestoranaMockMvc;

    private MenadzerRestorana menadzerRestorana;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenadzerRestoranaResource menadzerRestoranaResource = new MenadzerRestoranaResource(menadzerRestoranaRepository);
        this.restMenadzerRestoranaMockMvc = MockMvcBuilders.standaloneSetup(menadzerRestoranaResource)
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
    public static MenadzerRestorana createEntity(EntityManager em) {
        MenadzerRestorana menadzerRestorana = new MenadzerRestorana()
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .email(DEFAULT_EMAIL)
            .lozinka(DEFAULT_LOZINKA);
        return menadzerRestorana;
    }

    @Before
    public void initTest() {
        menadzerRestorana = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenadzerRestorana() throws Exception {
        int databaseSizeBeforeCreate = menadzerRestoranaRepository.findAll().size();

        // Create the MenadzerRestorana
        restMenadzerRestoranaMockMvc.perform(post("/api/menadzer-restoranas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menadzerRestorana)))
            .andExpect(status().isCreated());

        // Validate the MenadzerRestorana in the database
        List<MenadzerRestorana> menadzerRestoranaList = menadzerRestoranaRepository.findAll();
        assertThat(menadzerRestoranaList).hasSize(databaseSizeBeforeCreate + 1);
        MenadzerRestorana testMenadzerRestorana = menadzerRestoranaList.get(menadzerRestoranaList.size() - 1);
        assertThat(testMenadzerRestorana.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testMenadzerRestorana.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testMenadzerRestorana.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMenadzerRestorana.getLozinka()).isEqualTo(DEFAULT_LOZINKA);
    }

    @Test
    @Transactional
    public void createMenadzerRestoranaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menadzerRestoranaRepository.findAll().size();

        // Create the MenadzerRestorana with an existing ID
        menadzerRestorana.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenadzerRestoranaMockMvc.perform(post("/api/menadzer-restoranas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menadzerRestorana)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MenadzerRestorana> menadzerRestoranaList = menadzerRestoranaRepository.findAll();
        assertThat(menadzerRestoranaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMenadzerRestoranas() throws Exception {
        // Initialize the database
        menadzerRestoranaRepository.saveAndFlush(menadzerRestorana);

        // Get all the menadzerRestoranaList
        restMenadzerRestoranaMockMvc.perform(get("/api/menadzer-restoranas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menadzerRestorana.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].lozinka").value(hasItem(DEFAULT_LOZINKA.toString())));
    }

    @Test
    @Transactional
    public void getMenadzerRestorana() throws Exception {
        // Initialize the database
        menadzerRestoranaRepository.saveAndFlush(menadzerRestorana);

        // Get the menadzerRestorana
        restMenadzerRestoranaMockMvc.perform(get("/api/menadzer-restoranas/{id}", menadzerRestorana.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menadzerRestorana.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.lozinka").value(DEFAULT_LOZINKA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMenadzerRestorana() throws Exception {
        // Get the menadzerRestorana
        restMenadzerRestoranaMockMvc.perform(get("/api/menadzer-restoranas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenadzerRestorana() throws Exception {
        // Initialize the database
        menadzerRestoranaRepository.saveAndFlush(menadzerRestorana);
        int databaseSizeBeforeUpdate = menadzerRestoranaRepository.findAll().size();

        // Update the menadzerRestorana
        MenadzerRestorana updatedMenadzerRestorana = menadzerRestoranaRepository.findOne(menadzerRestorana.getId());
        updatedMenadzerRestorana
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .email(UPDATED_EMAIL)
            .lozinka(UPDATED_LOZINKA);

        restMenadzerRestoranaMockMvc.perform(put("/api/menadzer-restoranas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMenadzerRestorana)))
            .andExpect(status().isOk());

        // Validate the MenadzerRestorana in the database
        List<MenadzerRestorana> menadzerRestoranaList = menadzerRestoranaRepository.findAll();
        assertThat(menadzerRestoranaList).hasSize(databaseSizeBeforeUpdate);
        MenadzerRestorana testMenadzerRestorana = menadzerRestoranaList.get(menadzerRestoranaList.size() - 1);
        assertThat(testMenadzerRestorana.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testMenadzerRestorana.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testMenadzerRestorana.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMenadzerRestorana.getLozinka()).isEqualTo(UPDATED_LOZINKA);
    }

    @Test
    @Transactional
    public void updateNonExistingMenadzerRestorana() throws Exception {
        int databaseSizeBeforeUpdate = menadzerRestoranaRepository.findAll().size();

        // Create the MenadzerRestorana

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMenadzerRestoranaMockMvc.perform(put("/api/menadzer-restoranas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menadzerRestorana)))
            .andExpect(status().isCreated());

        // Validate the MenadzerRestorana in the database
        List<MenadzerRestorana> menadzerRestoranaList = menadzerRestoranaRepository.findAll();
        assertThat(menadzerRestoranaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMenadzerRestorana() throws Exception {
        // Initialize the database
        menadzerRestoranaRepository.saveAndFlush(menadzerRestorana);
        int databaseSizeBeforeDelete = menadzerRestoranaRepository.findAll().size();

        // Get the menadzerRestorana
        restMenadzerRestoranaMockMvc.perform(delete("/api/menadzer-restoranas/{id}", menadzerRestorana.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MenadzerRestorana> menadzerRestoranaList = menadzerRestoranaRepository.findAll();
        assertThat(menadzerRestoranaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenadzerRestorana.class);
        MenadzerRestorana menadzerRestorana1 = new MenadzerRestorana();
        menadzerRestorana1.setId(1L);
        MenadzerRestorana menadzerRestorana2 = new MenadzerRestorana();
        menadzerRestorana2.setId(menadzerRestorana1.getId());
        assertThat(menadzerRestorana1).isEqualTo(menadzerRestorana2);
        menadzerRestorana2.setId(2L);
        assertThat(menadzerRestorana1).isNotEqualTo(menadzerRestorana2);
        menadzerRestorana1.setId(null);
        assertThat(menadzerRestorana1).isNotEqualTo(menadzerRestorana2);
    }
}
