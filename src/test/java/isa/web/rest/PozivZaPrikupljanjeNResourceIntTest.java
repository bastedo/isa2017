package isa.web.rest;

import isa.Isa2017App;

import isa.domain.PozivZaPrikupljanjeN;
import isa.repository.PozivZaPrikupljanjeNRepository;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the PozivZaPrikupljanjeNResource REST controller.
 *
 * @see PozivZaPrikupljanjeNResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class PozivZaPrikupljanjeNResourceIntTest {

    private static final String DEFAULT_SPISAK_POTREBA = "AAAAAAAAAA";
    private static final String UPDATED_SPISAK_POTREBA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PozivZaPrikupljanjeNRepository pozivZaPrikupljanjeNRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPozivZaPrikupljanjeNMockMvc;

    private PozivZaPrikupljanjeN pozivZaPrikupljanjeN;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PozivZaPrikupljanjeNResource pozivZaPrikupljanjeNResource = new PozivZaPrikupljanjeNResource(pozivZaPrikupljanjeNRepository);
        this.restPozivZaPrikupljanjeNMockMvc = MockMvcBuilders.standaloneSetup(pozivZaPrikupljanjeNResource)
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
    public static PozivZaPrikupljanjeN createEntity(EntityManager em) {
        PozivZaPrikupljanjeN pozivZaPrikupljanjeN = new PozivZaPrikupljanjeN()
            .spisakPotreba(DEFAULT_SPISAK_POTREBA)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return pozivZaPrikupljanjeN;
    }

    @Before
    public void initTest() {
        pozivZaPrikupljanjeN = createEntity(em);
    }

    @Test
    @Transactional
    public void createPozivZaPrikupljanjeN() throws Exception {
        int databaseSizeBeforeCreate = pozivZaPrikupljanjeNRepository.findAll().size();

        // Create the PozivZaPrikupljanjeN
        restPozivZaPrikupljanjeNMockMvc.perform(post("/api/poziv-za-prikupljanje-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pozivZaPrikupljanjeN)))
            .andExpect(status().isCreated());

        // Validate the PozivZaPrikupljanjeN in the database
        List<PozivZaPrikupljanjeN> pozivZaPrikupljanjeNList = pozivZaPrikupljanjeNRepository.findAll();
        assertThat(pozivZaPrikupljanjeNList).hasSize(databaseSizeBeforeCreate + 1);
        PozivZaPrikupljanjeN testPozivZaPrikupljanjeN = pozivZaPrikupljanjeNList.get(pozivZaPrikupljanjeNList.size() - 1);
        assertThat(testPozivZaPrikupljanjeN.getSpisakPotreba()).isEqualTo(DEFAULT_SPISAK_POTREBA);
        assertThat(testPozivZaPrikupljanjeN.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testPozivZaPrikupljanjeN.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createPozivZaPrikupljanjeNWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pozivZaPrikupljanjeNRepository.findAll().size();

        // Create the PozivZaPrikupljanjeN with an existing ID
        pozivZaPrikupljanjeN.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPozivZaPrikupljanjeNMockMvc.perform(post("/api/poziv-za-prikupljanje-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pozivZaPrikupljanjeN)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PozivZaPrikupljanjeN> pozivZaPrikupljanjeNList = pozivZaPrikupljanjeNRepository.findAll();
        assertThat(pozivZaPrikupljanjeNList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPozivZaPrikupljanjeNS() throws Exception {
        // Initialize the database
        pozivZaPrikupljanjeNRepository.saveAndFlush(pozivZaPrikupljanjeN);

        // Get all the pozivZaPrikupljanjeNList
        restPozivZaPrikupljanjeNMockMvc.perform(get("/api/poziv-za-prikupljanje-ns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pozivZaPrikupljanjeN.getId().intValue())))
            .andExpect(jsonPath("$.[*].spisakPotreba").value(hasItem(DEFAULT_SPISAK_POTREBA.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }

    @Test
    @Transactional
    public void getPozivZaPrikupljanjeN() throws Exception {
        // Initialize the database
        pozivZaPrikupljanjeNRepository.saveAndFlush(pozivZaPrikupljanjeN);

        // Get the pozivZaPrikupljanjeN
        restPozivZaPrikupljanjeNMockMvc.perform(get("/api/poziv-za-prikupljanje-ns/{id}", pozivZaPrikupljanjeN.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pozivZaPrikupljanjeN.getId().intValue()))
            .andExpect(jsonPath("$.spisakPotreba").value(DEFAULT_SPISAK_POTREBA.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingPozivZaPrikupljanjeN() throws Exception {
        // Get the pozivZaPrikupljanjeN
        restPozivZaPrikupljanjeNMockMvc.perform(get("/api/poziv-za-prikupljanje-ns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePozivZaPrikupljanjeN() throws Exception {
        // Initialize the database
        pozivZaPrikupljanjeNRepository.saveAndFlush(pozivZaPrikupljanjeN);
        int databaseSizeBeforeUpdate = pozivZaPrikupljanjeNRepository.findAll().size();

        // Update the pozivZaPrikupljanjeN
        PozivZaPrikupljanjeN updatedPozivZaPrikupljanjeN = pozivZaPrikupljanjeNRepository.findOne(pozivZaPrikupljanjeN.getId());
        updatedPozivZaPrikupljanjeN
            .spisakPotreba(UPDATED_SPISAK_POTREBA)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restPozivZaPrikupljanjeNMockMvc.perform(put("/api/poziv-za-prikupljanje-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPozivZaPrikupljanjeN)))
            .andExpect(status().isOk());

        // Validate the PozivZaPrikupljanjeN in the database
        List<PozivZaPrikupljanjeN> pozivZaPrikupljanjeNList = pozivZaPrikupljanjeNRepository.findAll();
        assertThat(pozivZaPrikupljanjeNList).hasSize(databaseSizeBeforeUpdate);
        PozivZaPrikupljanjeN testPozivZaPrikupljanjeN = pozivZaPrikupljanjeNList.get(pozivZaPrikupljanjeNList.size() - 1);
        assertThat(testPozivZaPrikupljanjeN.getSpisakPotreba()).isEqualTo(UPDATED_SPISAK_POTREBA);
        assertThat(testPozivZaPrikupljanjeN.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testPozivZaPrikupljanjeN.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPozivZaPrikupljanjeN() throws Exception {
        int databaseSizeBeforeUpdate = pozivZaPrikupljanjeNRepository.findAll().size();

        // Create the PozivZaPrikupljanjeN

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPozivZaPrikupljanjeNMockMvc.perform(put("/api/poziv-za-prikupljanje-ns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pozivZaPrikupljanjeN)))
            .andExpect(status().isCreated());

        // Validate the PozivZaPrikupljanjeN in the database
        List<PozivZaPrikupljanjeN> pozivZaPrikupljanjeNList = pozivZaPrikupljanjeNRepository.findAll();
        assertThat(pozivZaPrikupljanjeNList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePozivZaPrikupljanjeN() throws Exception {
        // Initialize the database
        pozivZaPrikupljanjeNRepository.saveAndFlush(pozivZaPrikupljanjeN);
        int databaseSizeBeforeDelete = pozivZaPrikupljanjeNRepository.findAll().size();

        // Get the pozivZaPrikupljanjeN
        restPozivZaPrikupljanjeNMockMvc.perform(delete("/api/poziv-za-prikupljanje-ns/{id}", pozivZaPrikupljanjeN.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PozivZaPrikupljanjeN> pozivZaPrikupljanjeNList = pozivZaPrikupljanjeNRepository.findAll();
        assertThat(pozivZaPrikupljanjeNList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PozivZaPrikupljanjeN.class);
        PozivZaPrikupljanjeN pozivZaPrikupljanjeN1 = new PozivZaPrikupljanjeN();
        pozivZaPrikupljanjeN1.setId(1L);
        PozivZaPrikupljanjeN pozivZaPrikupljanjeN2 = new PozivZaPrikupljanjeN();
        pozivZaPrikupljanjeN2.setId(pozivZaPrikupljanjeN1.getId());
        assertThat(pozivZaPrikupljanjeN1).isEqualTo(pozivZaPrikupljanjeN2);
        pozivZaPrikupljanjeN2.setId(2L);
        assertThat(pozivZaPrikupljanjeN1).isNotEqualTo(pozivZaPrikupljanjeN2);
        pozivZaPrikupljanjeN1.setId(null);
        assertThat(pozivZaPrikupljanjeN1).isNotEqualTo(pozivZaPrikupljanjeN2);
    }
}
