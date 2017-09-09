package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Racun;
import isa.repository.RacunRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static isa.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RacunResource REST controller.
 *
 * @see RacunResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class RacunResourceIntTest {

    private static final Double DEFAULT_CENA = 1D;
    private static final Double UPDATED_CENA = 2D;

    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RacunRepository racunRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRacunMockMvc;

    private Racun racun;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RacunResource racunResource = new RacunResource(racunRepository);
        this.restRacunMockMvc = MockMvcBuilders.standaloneSetup(racunResource)
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
    public static Racun createEntity(EntityManager em) {
        Racun racun = new Racun()
            .cena(DEFAULT_CENA)
            .datum(DEFAULT_DATUM);
        return racun;
    }

    @Before
    public void initTest() {
        racun = createEntity(em);
    }

    @Test
    @Transactional
    public void createRacun() throws Exception {
        int databaseSizeBeforeCreate = racunRepository.findAll().size();

        // Create the Racun
        restRacunMockMvc.perform(post("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racun)))
            .andExpect(status().isCreated());

        // Validate the Racun in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeCreate + 1);
        Racun testRacun = racunList.get(racunList.size() - 1);
        assertThat(testRacun.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testRacun.getDatum()).isEqualTo(DEFAULT_DATUM);
    }

    @Test
    @Transactional
    public void createRacunWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = racunRepository.findAll().size();

        // Create the Racun with an existing ID
        racun.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRacunMockMvc.perform(post("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racun)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRacuns() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);

        // Get all the racunList
        restRacunMockMvc.perform(get("/api/racuns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(racun.getId().intValue())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.doubleValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(sameInstant(DEFAULT_DATUM))));
    }

    @Test
    @Transactional
    public void getRacun() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);

        // Get the racun
        restRacunMockMvc.perform(get("/api/racuns/{id}", racun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(racun.getId().intValue()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.doubleValue()))
            .andExpect(jsonPath("$.datum").value(sameInstant(DEFAULT_DATUM)));
    }

    @Test
    @Transactional
    public void getNonExistingRacun() throws Exception {
        // Get the racun
        restRacunMockMvc.perform(get("/api/racuns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRacun() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);
        int databaseSizeBeforeUpdate = racunRepository.findAll().size();

        // Update the racun
        Racun updatedRacun = racunRepository.findOne(racun.getId());
        updatedRacun
            .cena(UPDATED_CENA)
            .datum(UPDATED_DATUM);

        restRacunMockMvc.perform(put("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRacun)))
            .andExpect(status().isOk());

        // Validate the Racun in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeUpdate);
        Racun testRacun = racunList.get(racunList.size() - 1);
        assertThat(testRacun.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testRacun.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    public void updateNonExistingRacun() throws Exception {
        int databaseSizeBeforeUpdate = racunRepository.findAll().size();

        // Create the Racun

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRacunMockMvc.perform(put("/api/racuns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(racun)))
            .andExpect(status().isCreated());

        // Validate the Racun in the database
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRacun() throws Exception {
        // Initialize the database
        racunRepository.saveAndFlush(racun);
        int databaseSizeBeforeDelete = racunRepository.findAll().size();

        // Get the racun
        restRacunMockMvc.perform(delete("/api/racuns/{id}", racun.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Racun> racunList = racunRepository.findAll();
        assertThat(racunList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Racun.class);
        Racun racun1 = new Racun();
        racun1.setId(1L);
        Racun racun2 = new Racun();
        racun2.setId(racun1.getId());
        assertThat(racun1).isEqualTo(racun2);
        racun2.setId(2L);
        assertThat(racun1).isNotEqualTo(racun2);
        racun1.setId(null);
        assertThat(racun1).isNotEqualTo(racun2);
    }
}
