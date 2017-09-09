package isa.web.rest;

import isa.Isa2017App;

import isa.domain.RasporedSmenaZaKonobare;
import isa.repository.RasporedSmenaZaKonobareRepository;
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
 * Test class for the RasporedSmenaZaKonobareResource REST controller.
 *
 * @see RasporedSmenaZaKonobareResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class RasporedSmenaZaKonobareResourceIntTest {

    private static final VrstaSmene DEFAULT_VRSTA_SMENE = VrstaSmene.PRVA;
    private static final VrstaSmene UPDATED_VRSTA_SMENE = VrstaSmene.DRUGA;

    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RasporedSmenaZaKonobareRepository rasporedSmenaZaKonobareRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRasporedSmenaZaKonobareMockMvc;

    private RasporedSmenaZaKonobare rasporedSmenaZaKonobare;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RasporedSmenaZaKonobareResource rasporedSmenaZaKonobareResource = new RasporedSmenaZaKonobareResource(rasporedSmenaZaKonobareRepository);
        this.restRasporedSmenaZaKonobareMockMvc = MockMvcBuilders.standaloneSetup(rasporedSmenaZaKonobareResource)
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
    public static RasporedSmenaZaKonobare createEntity(EntityManager em) {
        RasporedSmenaZaKonobare rasporedSmenaZaKonobare = new RasporedSmenaZaKonobare()
            .vrstaSmene(DEFAULT_VRSTA_SMENE)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return rasporedSmenaZaKonobare;
    }

    @Before
    public void initTest() {
        rasporedSmenaZaKonobare = createEntity(em);
    }

    @Test
    @Transactional
    public void createRasporedSmenaZaKonobare() throws Exception {
        int databaseSizeBeforeCreate = rasporedSmenaZaKonobareRepository.findAll().size();

        // Create the RasporedSmenaZaKonobare
        restRasporedSmenaZaKonobareMockMvc.perform(post("/api/raspored-smena-za-konobares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaKonobare)))
            .andExpect(status().isCreated());

        // Validate the RasporedSmenaZaKonobare in the database
        List<RasporedSmenaZaKonobare> rasporedSmenaZaKonobareList = rasporedSmenaZaKonobareRepository.findAll();
        assertThat(rasporedSmenaZaKonobareList).hasSize(databaseSizeBeforeCreate + 1);
        RasporedSmenaZaKonobare testRasporedSmenaZaKonobare = rasporedSmenaZaKonobareList.get(rasporedSmenaZaKonobareList.size() - 1);
        assertThat(testRasporedSmenaZaKonobare.getVrstaSmene()).isEqualTo(DEFAULT_VRSTA_SMENE);
        assertThat(testRasporedSmenaZaKonobare.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testRasporedSmenaZaKonobare.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createRasporedSmenaZaKonobareWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rasporedSmenaZaKonobareRepository.findAll().size();

        // Create the RasporedSmenaZaKonobare with an existing ID
        rasporedSmenaZaKonobare.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRasporedSmenaZaKonobareMockMvc.perform(post("/api/raspored-smena-za-konobares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaKonobare)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RasporedSmenaZaKonobare> rasporedSmenaZaKonobareList = rasporedSmenaZaKonobareRepository.findAll();
        assertThat(rasporedSmenaZaKonobareList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRasporedSmenaZaKonobares() throws Exception {
        // Initialize the database
        rasporedSmenaZaKonobareRepository.saveAndFlush(rasporedSmenaZaKonobare);

        // Get all the rasporedSmenaZaKonobareList
        restRasporedSmenaZaKonobareMockMvc.perform(get("/api/raspored-smena-za-konobares?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rasporedSmenaZaKonobare.getId().intValue())))
            .andExpect(jsonPath("$.[*].vrstaSmene").value(hasItem(DEFAULT_VRSTA_SMENE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(sameInstant(DEFAULT_START))))
            .andExpect(jsonPath("$.[*].end").value(hasItem(sameInstant(DEFAULT_END))));
    }

    @Test
    @Transactional
    public void getRasporedSmenaZaKonobare() throws Exception {
        // Initialize the database
        rasporedSmenaZaKonobareRepository.saveAndFlush(rasporedSmenaZaKonobare);

        // Get the rasporedSmenaZaKonobare
        restRasporedSmenaZaKonobareMockMvc.perform(get("/api/raspored-smena-za-konobares/{id}", rasporedSmenaZaKonobare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rasporedSmenaZaKonobare.getId().intValue()))
            .andExpect(jsonPath("$.vrstaSmene").value(DEFAULT_VRSTA_SMENE.toString()))
            .andExpect(jsonPath("$.start").value(sameInstant(DEFAULT_START)))
            .andExpect(jsonPath("$.end").value(sameInstant(DEFAULT_END)));
    }

    @Test
    @Transactional
    public void getNonExistingRasporedSmenaZaKonobare() throws Exception {
        // Get the rasporedSmenaZaKonobare
        restRasporedSmenaZaKonobareMockMvc.perform(get("/api/raspored-smena-za-konobares/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRasporedSmenaZaKonobare() throws Exception {
        // Initialize the database
        rasporedSmenaZaKonobareRepository.saveAndFlush(rasporedSmenaZaKonobare);
        int databaseSizeBeforeUpdate = rasporedSmenaZaKonobareRepository.findAll().size();

        // Update the rasporedSmenaZaKonobare
        RasporedSmenaZaKonobare updatedRasporedSmenaZaKonobare = rasporedSmenaZaKonobareRepository.findOne(rasporedSmenaZaKonobare.getId());
        updatedRasporedSmenaZaKonobare
            .vrstaSmene(UPDATED_VRSTA_SMENE)
            .start(UPDATED_START)
            .end(UPDATED_END);

        restRasporedSmenaZaKonobareMockMvc.perform(put("/api/raspored-smena-za-konobares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRasporedSmenaZaKonobare)))
            .andExpect(status().isOk());

        // Validate the RasporedSmenaZaKonobare in the database
        List<RasporedSmenaZaKonobare> rasporedSmenaZaKonobareList = rasporedSmenaZaKonobareRepository.findAll();
        assertThat(rasporedSmenaZaKonobareList).hasSize(databaseSizeBeforeUpdate);
        RasporedSmenaZaKonobare testRasporedSmenaZaKonobare = rasporedSmenaZaKonobareList.get(rasporedSmenaZaKonobareList.size() - 1);
        assertThat(testRasporedSmenaZaKonobare.getVrstaSmene()).isEqualTo(UPDATED_VRSTA_SMENE);
        assertThat(testRasporedSmenaZaKonobare.getStart()).isEqualTo(UPDATED_START);
        assertThat(testRasporedSmenaZaKonobare.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingRasporedSmenaZaKonobare() throws Exception {
        int databaseSizeBeforeUpdate = rasporedSmenaZaKonobareRepository.findAll().size();

        // Create the RasporedSmenaZaKonobare

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRasporedSmenaZaKonobareMockMvc.perform(put("/api/raspored-smena-za-konobares")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rasporedSmenaZaKonobare)))
            .andExpect(status().isCreated());

        // Validate the RasporedSmenaZaKonobare in the database
        List<RasporedSmenaZaKonobare> rasporedSmenaZaKonobareList = rasporedSmenaZaKonobareRepository.findAll();
        assertThat(rasporedSmenaZaKonobareList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRasporedSmenaZaKonobare() throws Exception {
        // Initialize the database
        rasporedSmenaZaKonobareRepository.saveAndFlush(rasporedSmenaZaKonobare);
        int databaseSizeBeforeDelete = rasporedSmenaZaKonobareRepository.findAll().size();

        // Get the rasporedSmenaZaKonobare
        restRasporedSmenaZaKonobareMockMvc.perform(delete("/api/raspored-smena-za-konobares/{id}", rasporedSmenaZaKonobare.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RasporedSmenaZaKonobare> rasporedSmenaZaKonobareList = rasporedSmenaZaKonobareRepository.findAll();
        assertThat(rasporedSmenaZaKonobareList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RasporedSmenaZaKonobare.class);
        RasporedSmenaZaKonobare rasporedSmenaZaKonobare1 = new RasporedSmenaZaKonobare();
        rasporedSmenaZaKonobare1.setId(1L);
        RasporedSmenaZaKonobare rasporedSmenaZaKonobare2 = new RasporedSmenaZaKonobare();
        rasporedSmenaZaKonobare2.setId(rasporedSmenaZaKonobare1.getId());
        assertThat(rasporedSmenaZaKonobare1).isEqualTo(rasporedSmenaZaKonobare2);
        rasporedSmenaZaKonobare2.setId(2L);
        assertThat(rasporedSmenaZaKonobare1).isNotEqualTo(rasporedSmenaZaKonobare2);
        rasporedSmenaZaKonobare1.setId(null);
        assertThat(rasporedSmenaZaKonobare1).isNotEqualTo(rasporedSmenaZaKonobare2);
    }
}
