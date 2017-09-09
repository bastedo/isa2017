package isa.web.rest;

import isa.Isa2017App;

import isa.domain.RasporedSmenaZaSankere;
import isa.repository.RasporedSmenaZaSankereRepository;
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
 * Test class for the RasporedSmenaZaSankereResource REST controller.
 *
 * @see RasporedSmenaZaSankereResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class RasporedSmenaZaSankereResourceIntTest {

    private static final VrstaSmene DEFAULT_VRSTA_SMENE = VrstaSmene.PRVA;
    private static final VrstaSmene UPDATED_VRSTA_SMENE = VrstaSmene.DRUGA;

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RasporedSmenaZaSankereRepository rasporedSmenaZaSankereRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRasporedSmenaZaSankereMockMvc;

    private RasporedSmenaZaSankere rasporedSmenaZaSankere;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RasporedSmenaZaSankereResource rasporedSmenaZaSankereResource = new RasporedSmenaZaSankereResource(rasporedSmenaZaSankereRepository);
        this.restRasporedSmenaZaSankereMockMvc = MockMvcBuilders.standaloneSetup(rasporedSmenaZaSankereResource)
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
    public static RasporedSmenaZaSankere createEntity(EntityManager em) {
        RasporedSmenaZaSankere rasporedSmenaZaSankere = new RasporedSmenaZaSankere()
            .vrstaSmene(DEFAULT_VRSTA_SMENE)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return rasporedSmenaZaSankere;
    }

    @Before
    public void initTest() {
        rasporedSmenaZaSankere = createEntity(em);
    }

    @Test
    @Transactional
    public void createRasporedSmenaZaSankere() throws Exception {
        int databaseSizeBeforeCreate = rasporedSmenaZaSankereRepository.findAll().size();

        // Create the RasporedSmenaZaSankere
        restRasporedSmenaZaSankereMockMvc.perform(post("/api/raspored-smena-za-sankeres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaSankere)))
            .andExpect(status().isCreated());

        // Validate the RasporedSmenaZaSankere in the database
        List<RasporedSmenaZaSankere> rasporedSmenaZaSankereList = rasporedSmenaZaSankereRepository.findAll();
        assertThat(rasporedSmenaZaSankereList).hasSize(databaseSizeBeforeCreate + 1);
        RasporedSmenaZaSankere testRasporedSmenaZaSankere = rasporedSmenaZaSankereList.get(rasporedSmenaZaSankereList.size() - 1);
        assertThat(testRasporedSmenaZaSankere.getVrstaSmene()).isEqualTo(DEFAULT_VRSTA_SMENE);
        assertThat(testRasporedSmenaZaSankere.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testRasporedSmenaZaSankere.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createRasporedSmenaZaSankereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rasporedSmenaZaSankereRepository.findAll().size();

        // Create the RasporedSmenaZaSankere with an existing ID
        rasporedSmenaZaSankere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRasporedSmenaZaSankereMockMvc.perform(post("/api/raspored-smena-za-sankeres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaSankere)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RasporedSmenaZaSankere> rasporedSmenaZaSankereList = rasporedSmenaZaSankereRepository.findAll();
        assertThat(rasporedSmenaZaSankereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRasporedSmenaZaSankeres() throws Exception {
        // Initialize the database
        rasporedSmenaZaSankereRepository.saveAndFlush(rasporedSmenaZaSankere);

        // Get all the rasporedSmenaZaSankereList
        restRasporedSmenaZaSankereMockMvc.perform(get("/api/raspored-smena-za-sankeres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rasporedSmenaZaSankere.getId().intValue())))
            .andExpect(jsonPath("$.[*].vrstaSmene").value(hasItem(DEFAULT_VRSTA_SMENE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))));
    }

    @Test
    @Transactional
    public void getRasporedSmenaZaSankere() throws Exception {
        // Initialize the database
        rasporedSmenaZaSankereRepository.saveAndFlush(rasporedSmenaZaSankere);

        // Get the rasporedSmenaZaSankere
        restRasporedSmenaZaSankereMockMvc.perform(get("/api/raspored-smena-za-sankeres/{id}", rasporedSmenaZaSankere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rasporedSmenaZaSankere.getId().intValue()))
            .andExpect(jsonPath("$.vrstaSmene").value(DEFAULT_VRSTA_SMENE.toString()))
            .andExpect(jsonPath("$.start").value(sameInstant(DEFAULT_START)))
            .andExpect(jsonPath("$.end").value(sameInstant(DEFAULT_END)));
    }

    @Test
    @Transactional
    public void getNonExistingRasporedSmenaZaSankere() throws Exception {
        // Get the rasporedSmenaZaSankere
        restRasporedSmenaZaSankereMockMvc.perform(get("/api/raspored-smena-za-sankeres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRasporedSmenaZaSankere() throws Exception {
        // Initialize the database
        rasporedSmenaZaSankereRepository.saveAndFlush(rasporedSmenaZaSankere);
        int databaseSizeBeforeUpdate = rasporedSmenaZaSankereRepository.findAll().size();

        // Update the rasporedSmenaZaSankere
        RasporedSmenaZaSankere updatedRasporedSmenaZaSankere = rasporedSmenaZaSankereRepository.findOne(rasporedSmenaZaSankere.getId());
        updatedRasporedSmenaZaSankere
            .vrstaSmene(UPDATED_VRSTA_SMENE)
            .start(UPDATED_START)
            .end(UPDATED_END);

        restRasporedSmenaZaSankereMockMvc.perform(put("/api/raspored-smena-za-sankeres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRasporedSmenaZaSankere)))
            .andExpect(status().isOk());

        // Validate the RasporedSmenaZaSankere in the database
        List<RasporedSmenaZaSankere> rasporedSmenaZaSankereList = rasporedSmenaZaSankereRepository.findAll();
        assertThat(rasporedSmenaZaSankereList).hasSize(databaseSizeBeforeUpdate);
        RasporedSmenaZaSankere testRasporedSmenaZaSankere = rasporedSmenaZaSankereList.get(rasporedSmenaZaSankereList.size() - 1);
        assertThat(testRasporedSmenaZaSankere.getVrstaSmene()).isEqualTo(UPDATED_VRSTA_SMENE);
        assertThat(testRasporedSmenaZaSankere.getStart()).isEqualTo(UPDATED_START);
        assertThat(testRasporedSmenaZaSankere.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingRasporedSmenaZaSankere() throws Exception {
        int databaseSizeBeforeUpdate = rasporedSmenaZaSankereRepository.findAll().size();

        // Create the RasporedSmenaZaSankere

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRasporedSmenaZaSankereMockMvc.perform(put("/api/raspored-smena-za-sankeres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaSankere)))
            .andExpect(status().isCreated());

        // Validate the RasporedSmenaZaSankere in the database
        List<RasporedSmenaZaSankere> rasporedSmenaZaSankereList = rasporedSmenaZaSankereRepository.findAll();
        assertThat(rasporedSmenaZaSankereList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRasporedSmenaZaSankere() throws Exception {
        // Initialize the database
        rasporedSmenaZaSankereRepository.saveAndFlush(rasporedSmenaZaSankere);
        int databaseSizeBeforeDelete = rasporedSmenaZaSankereRepository.findAll().size();

        // Get the rasporedSmenaZaSankere
        restRasporedSmenaZaSankereMockMvc.perform(delete("/api/raspored-smena-za-sankeres/{id}", rasporedSmenaZaSankere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RasporedSmenaZaSankere> rasporedSmenaZaSankereList = rasporedSmenaZaSankereRepository.findAll();
        assertThat(rasporedSmenaZaSankereList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RasporedSmenaZaSankere.class);
        RasporedSmenaZaSankere rasporedSmenaZaSankere1 = new RasporedSmenaZaSankere();
        rasporedSmenaZaSankere1.setId(1L);
        RasporedSmenaZaSankere rasporedSmenaZaSankere2 = new RasporedSmenaZaSankere();
        rasporedSmenaZaSankere2.setId(rasporedSmenaZaSankere1.getId());
        assertThat(rasporedSmenaZaSankere1).isEqualTo(rasporedSmenaZaSankere2);
        rasporedSmenaZaSankere2.setId(2L);
        assertThat(rasporedSmenaZaSankere1).isNotEqualTo(rasporedSmenaZaSankere2);
        rasporedSmenaZaSankere1.setId(null);
        assertThat(rasporedSmenaZaSankere1).isNotEqualTo(rasporedSmenaZaSankere2);
    }
}
