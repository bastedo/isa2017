package isa.web.rest;

import isa.Isa2017App;

import isa.domain.RasporedSmenaZaKuvare;
import isa.repository.RasporedSmenaZaKuvareRepository;
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

import isa.domain.enumeration.VrstaSmene;
/**
 * Test class for the RasporedSmenaZaKuvareResource REST controller.
 *
 * @see RasporedSmenaZaKuvareResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class RasporedSmenaZaKuvareResourceIntTest {

    private static final VrstaSmene DEFAULT_VRSTA_SMENE = VrstaSmene.PRVA;
    private static final VrstaSmene UPDATED_VRSTA_SMENE = VrstaSmene.DRUGA;

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RasporedSmenaZaKuvareRepository rasporedSmenaZaKuvareRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRasporedSmenaZaKuvareMockMvc;

    private RasporedSmenaZaKuvare rasporedSmenaZaKuvare;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RasporedSmenaZaKuvareResource rasporedSmenaZaKuvareResource = new RasporedSmenaZaKuvareResource(rasporedSmenaZaKuvareRepository);
        this.restRasporedSmenaZaKuvareMockMvc = MockMvcBuilders.standaloneSetup(rasporedSmenaZaKuvareResource)
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
    public static RasporedSmenaZaKuvare createEntity(EntityManager em) {
        RasporedSmenaZaKuvare rasporedSmenaZaKuvare = new RasporedSmenaZaKuvare()
            .vrstaSmene(DEFAULT_VRSTA_SMENE)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return rasporedSmenaZaKuvare;
    }

    @Before
    public void initTest() {
        rasporedSmenaZaKuvare = createEntity(em);
    }

    @Test
    @Transactional
    public void createRasporedSmenaZaKuvare() throws Exception {
        int databaseSizeBeforeCreate = rasporedSmenaZaKuvareRepository.findAll().size();

        // Create the RasporedSmenaZaKuvare
        restRasporedSmenaZaKuvareMockMvc.perform(post("/api/raspored-smena-za-kuvares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaKuvare)))
            .andExpect(status().isCreated());

        // Validate the RasporedSmenaZaKuvare in the database
        List<RasporedSmenaZaKuvare> rasporedSmenaZaKuvareList = rasporedSmenaZaKuvareRepository.findAll();
        assertThat(rasporedSmenaZaKuvareList).hasSize(databaseSizeBeforeCreate + 1);
        RasporedSmenaZaKuvare testRasporedSmenaZaKuvare = rasporedSmenaZaKuvareList.get(rasporedSmenaZaKuvareList.size() - 1);
        assertThat(testRasporedSmenaZaKuvare.getVrstaSmene()).isEqualTo(DEFAULT_VRSTA_SMENE);
        assertThat(testRasporedSmenaZaKuvare.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testRasporedSmenaZaKuvare.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createRasporedSmenaZaKuvareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rasporedSmenaZaKuvareRepository.findAll().size();

        // Create the RasporedSmenaZaKuvare with an existing ID
        rasporedSmenaZaKuvare.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRasporedSmenaZaKuvareMockMvc.perform(post("/api/raspored-smena-za-kuvares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaKuvare)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RasporedSmenaZaKuvare> rasporedSmenaZaKuvareList = rasporedSmenaZaKuvareRepository.findAll();
        assertThat(rasporedSmenaZaKuvareList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRasporedSmenaZaKuvares() throws Exception {
        // Initialize the database
        rasporedSmenaZaKuvareRepository.saveAndFlush(rasporedSmenaZaKuvare);

        // Get all the rasporedSmenaZaKuvareList
        restRasporedSmenaZaKuvareMockMvc.perform(get("/api/raspored-smena-za-kuvares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rasporedSmenaZaKuvare.getId().intValue())))
            .andExpect(jsonPath("$.[*].vrstaSmene").value(hasItem(DEFAULT_VRSTA_SMENE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))));
    }

    @Test
    @Transactional
    public void getRasporedSmenaZaKuvare() throws Exception {
        // Initialize the database
        rasporedSmenaZaKuvareRepository.saveAndFlush(rasporedSmenaZaKuvare);

        // Get the rasporedSmenaZaKuvare
        restRasporedSmenaZaKuvareMockMvc.perform(get("/api/raspored-smena-za-kuvares/{id}", rasporedSmenaZaKuvare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rasporedSmenaZaKuvare.getId().intValue()))
            .andExpect(jsonPath("$.vrstaSmene").value(DEFAULT_VRSTA_SMENE.toString()))
            .andExpect(jsonPath("$.start").value(sameInstant(DEFAULT_START)))
            .andExpect(jsonPath("$.end").value(sameInstant(DEFAULT_END)));
    }

    @Test
    @Transactional
    public void getNonExistingRasporedSmenaZaKuvare() throws Exception {
        // Get the rasporedSmenaZaKuvare
        restRasporedSmenaZaKuvareMockMvc.perform(get("/api/raspored-smena-za-kuvares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRasporedSmenaZaKuvare() throws Exception {
        // Initialize the database
        rasporedSmenaZaKuvareRepository.saveAndFlush(rasporedSmenaZaKuvare);
        int databaseSizeBeforeUpdate = rasporedSmenaZaKuvareRepository.findAll().size();

        // Update the rasporedSmenaZaKuvare
        RasporedSmenaZaKuvare updatedRasporedSmenaZaKuvare = rasporedSmenaZaKuvareRepository.findOne(rasporedSmenaZaKuvare.getId());
        updatedRasporedSmenaZaKuvare
            .vrstaSmene(UPDATED_VRSTA_SMENE)
            .start(UPDATED_START)
            .end(UPDATED_END);

        restRasporedSmenaZaKuvareMockMvc.perform(put("/api/raspored-smena-za-kuvares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRasporedSmenaZaKuvare)))
            .andExpect(status().isOk());

        // Validate the RasporedSmenaZaKuvare in the database
        List<RasporedSmenaZaKuvare> rasporedSmenaZaKuvareList = rasporedSmenaZaKuvareRepository.findAll();
        assertThat(rasporedSmenaZaKuvareList).hasSize(databaseSizeBeforeUpdate);
        RasporedSmenaZaKuvare testRasporedSmenaZaKuvare = rasporedSmenaZaKuvareList.get(rasporedSmenaZaKuvareList.size() - 1);
        assertThat(testRasporedSmenaZaKuvare.getVrstaSmene()).isEqualTo(UPDATED_VRSTA_SMENE);
        assertThat(testRasporedSmenaZaKuvare.getStart()).isEqualTo(UPDATED_START);
        assertThat(testRasporedSmenaZaKuvare.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingRasporedSmenaZaKuvare() throws Exception {
        int databaseSizeBeforeUpdate = rasporedSmenaZaKuvareRepository.findAll().size();

        // Create the RasporedSmenaZaKuvare

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRasporedSmenaZaKuvareMockMvc.perform(put("/api/raspored-smena-za-kuvares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaKuvare)))
            .andExpect(status().isCreated());

        // Validate the RasporedSmenaZaKuvare in the database
        List<RasporedSmenaZaKuvare> rasporedSmenaZaKuvareList = rasporedSmenaZaKuvareRepository.findAll();
        assertThat(rasporedSmenaZaKuvareList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRasporedSmenaZaKuvare() throws Exception {
        // Initialize the database
        rasporedSmenaZaKuvareRepository.saveAndFlush(rasporedSmenaZaKuvare);
        int databaseSizeBeforeDelete = rasporedSmenaZaKuvareRepository.findAll().size();

        // Get the rasporedSmenaZaKuvare
        restRasporedSmenaZaKuvareMockMvc.perform(delete("/api/raspored-smena-za-kuvares/{id}", rasporedSmenaZaKuvare.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RasporedSmenaZaKuvare> rasporedSmenaZaKuvareList = rasporedSmenaZaKuvareRepository.findAll();
        assertThat(rasporedSmenaZaKuvareList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RasporedSmenaZaKuvare.class);
        RasporedSmenaZaKuvare rasporedSmenaZaKuvare1 = new RasporedSmenaZaKuvare();
        rasporedSmenaZaKuvare1.setId(1L);
        RasporedSmenaZaKuvare rasporedSmenaZaKuvare2 = new RasporedSmenaZaKuvare();
        rasporedSmenaZaKuvare2.setId(rasporedSmenaZaKuvare1.getId());
        assertThat(rasporedSmenaZaKuvare1).isEqualTo(rasporedSmenaZaKuvare2);
        rasporedSmenaZaKuvare2.setId(2L);
        assertThat(rasporedSmenaZaKuvare1).isNotEqualTo(rasporedSmenaZaKuvare2);
        rasporedSmenaZaKuvare1.setId(null);
        assertThat(rasporedSmenaZaKuvare1).isNotEqualTo(rasporedSmenaZaKuvare2);
    }
}
