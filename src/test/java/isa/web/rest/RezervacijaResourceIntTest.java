package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Rezervacija;
import isa.repository.RezervacijaRepository;
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
 * Test class for the RezervacijaResource REST controller.
 *
 * @see RezervacijaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class RezervacijaResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_OCENJENO = false;
    private static final Boolean UPDATED_OCENJENO = true;

    private static final Boolean DEFAULT_POTVRDJENO = false;
    private static final Boolean UPDATED_POTVRDJENO = true;

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRezervacijaMockMvc;

    private Rezervacija rezervacija;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RezervacijaResource rezervacijaResource = new RezervacijaResource(rezervacijaRepository);
        this.restRezervacijaMockMvc = MockMvcBuilders.standaloneSetup(rezervacijaResource)
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
    public static Rezervacija createEntity(EntityManager em) {
        Rezervacija rezervacija = new Rezervacija()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .ocenjeno(DEFAULT_OCENJENO)
            .potvrdjeno(DEFAULT_POTVRDJENO);
        return rezervacija;
    }

    @Before
    public void initTest() {
        rezervacija = createEntity(em);
    }

    @Test
    @Transactional
    public void createRezervacija() throws Exception {
        int databaseSizeBeforeCreate = rezervacijaRepository.findAll().size();

        // Create the Rezervacija
        restRezervacijaMockMvc.perform(post("/api/rezervacijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rezervacija)))
            .andExpect(status().isCreated());

        // Validate the Rezervacija in the database
        List<Rezervacija> rezervacijaList = rezervacijaRepository.findAll();
        assertThat(rezervacijaList).hasSize(databaseSizeBeforeCreate + 1);
        Rezervacija testRezervacija = rezervacijaList.get(rezervacijaList.size() - 1);
        assertThat(testRezervacija.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testRezervacija.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testRezervacija.isOcenjeno()).isEqualTo(DEFAULT_OCENJENO);
        assertThat(testRezervacija.isPotvrdjeno()).isEqualTo(DEFAULT_POTVRDJENO);
    }

    @Test
    @Transactional
    public void createRezervacijaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rezervacijaRepository.findAll().size();

        // Create the Rezervacija with an existing ID
        rezervacija.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRezervacijaMockMvc.perform(post("/api/rezervacijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rezervacija)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rezervacija> rezervacijaList = rezervacijaRepository.findAll();
        assertThat(rezervacijaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRezervacijas() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);

        // Get all the rezervacijaList
        restRezervacijaMockMvc.perform(get("/api/rezervacijas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rezervacija.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].ocenjeno").value(hasItem(DEFAULT_OCENJENO.booleanValue())))
            .andExpect(jsonPath("$.[*].potvrdjeno").value(hasItem(DEFAULT_POTVRDJENO.booleanValue())));
    }

    @Test
    @Transactional
    public void getRezervacija() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);

        // Get the rezervacija
        restRezervacijaMockMvc.perform(get("/api/rezervacijas/{id}", rezervacija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rezervacija.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.ocenjeno").value(DEFAULT_OCENJENO.booleanValue()))
            .andExpect(jsonPath("$.potvrdjeno").value(DEFAULT_POTVRDJENO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRezervacija() throws Exception {
        // Get the rezervacija
        restRezervacijaMockMvc.perform(get("/api/rezervacijas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRezervacija() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);
        int databaseSizeBeforeUpdate = rezervacijaRepository.findAll().size();

        // Update the rezervacija
        Rezervacija updatedRezervacija = rezervacijaRepository.findOne(rezervacija.getId());
        updatedRezervacija
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .ocenjeno(UPDATED_OCENJENO)
            .potvrdjeno(UPDATED_POTVRDJENO);

        restRezervacijaMockMvc.perform(put("/api/rezervacijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRezervacija)))
            .andExpect(status().isOk());

        // Validate the Rezervacija in the database
        List<Rezervacija> rezervacijaList = rezervacijaRepository.findAll();
        assertThat(rezervacijaList).hasSize(databaseSizeBeforeUpdate);
        Rezervacija testRezervacija = rezervacijaList.get(rezervacijaList.size() - 1);
        assertThat(testRezervacija.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testRezervacija.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testRezervacija.isOcenjeno()).isEqualTo(UPDATED_OCENJENO);
        assertThat(testRezervacija.isPotvrdjeno()).isEqualTo(UPDATED_POTVRDJENO);
    }

    @Test
    @Transactional
    public void updateNonExistingRezervacija() throws Exception {
        int databaseSizeBeforeUpdate = rezervacijaRepository.findAll().size();

        // Create the Rezervacija

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRezervacijaMockMvc.perform(put("/api/rezervacijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rezervacija)))
            .andExpect(status().isCreated());

        // Validate the Rezervacija in the database
        List<Rezervacija> rezervacijaList = rezervacijaRepository.findAll();
        assertThat(rezervacijaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRezervacija() throws Exception {
        // Initialize the database
        rezervacijaRepository.saveAndFlush(rezervacija);
        int databaseSizeBeforeDelete = rezervacijaRepository.findAll().size();

        // Get the rezervacija
        restRezervacijaMockMvc.perform(delete("/api/rezervacijas/{id}", rezervacija.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rezervacija> rezervacijaList = rezervacijaRepository.findAll();
        assertThat(rezervacijaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rezervacija.class);
        Rezervacija rezervacija1 = new Rezervacija();
        rezervacija1.setId(1L);
        Rezervacija rezervacija2 = new Rezervacija();
        rezervacija2.setId(rezervacija1.getId());
        assertThat(rezervacija1).isEqualTo(rezervacija2);
        rezervacija2.setId(2L);
        assertThat(rezervacija1).isNotEqualTo(rezervacija2);
        rezervacija1.setId(null);
        assertThat(rezervacija1).isNotEqualTo(rezervacija2);
    }
}
