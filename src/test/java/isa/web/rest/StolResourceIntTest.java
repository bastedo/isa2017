package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Stol;
import isa.repository.StolRepository;
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

import isa.domain.enumeration.Segmenat;
/**
 * Test class for the StolResource REST controller.
 *
 * @see StolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class StolResourceIntTest {

    private static final Integer DEFAULT_XPOZICIJA_STOLA = 1;
    private static final Integer UPDATED_XPOZICIJA_STOLA = 2;

    private static final Integer DEFAULT_YPOZICIJA_STOLA = 1;
    private static final Integer UPDATED_YPOZICIJA_STOLA = 2;

    private static final Segmenat DEFAULT_PRIPADA_SEGMENTU = Segmenat.ZA_PUSACE;
    private static final Segmenat UPDATED_PRIPADA_SEGMENTU = Segmenat.OTVORENA_BASTA;

    @Autowired
    private StolRepository stolRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStolMockMvc;

    private Stol stol;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StolResource stolResource = new StolResource(stolRepository);
        this.restStolMockMvc = MockMvcBuilders.standaloneSetup(stolResource)
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
    public static Stol createEntity(EntityManager em) {
        Stol stol = new Stol()
            .xpozicijaStola(DEFAULT_XPOZICIJA_STOLA)
            .ypozicijaStola(DEFAULT_YPOZICIJA_STOLA)
            .pripadaSegmentu(DEFAULT_PRIPADA_SEGMENTU);
        return stol;
    }

    @Before
    public void initTest() {
        stol = createEntity(em);
    }

    @Test
    @Transactional
    public void createStol() throws Exception {
        int databaseSizeBeforeCreate = stolRepository.findAll().size();

        // Create the Stol
        restStolMockMvc.perform(post("/api/stols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stol)))
            .andExpect(status().isCreated());

        // Validate the Stol in the database
        List<Stol> stolList = stolRepository.findAll();
        assertThat(stolList).hasSize(databaseSizeBeforeCreate + 1);
        Stol testStol = stolList.get(stolList.size() - 1);
        assertThat(testStol.getXpozicijaStola()).isEqualTo(DEFAULT_XPOZICIJA_STOLA);
        assertThat(testStol.getYpozicijaStola()).isEqualTo(DEFAULT_YPOZICIJA_STOLA);
        assertThat(testStol.getPripadaSegmentu()).isEqualTo(DEFAULT_PRIPADA_SEGMENTU);
    }

    @Test
    @Transactional
    public void createStolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stolRepository.findAll().size();

        // Create the Stol with an existing ID
        stol.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStolMockMvc.perform(post("/api/stols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stol)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Stol> stolList = stolRepository.findAll();
        assertThat(stolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStols() throws Exception {
        // Initialize the database
        stolRepository.saveAndFlush(stol);

        // Get all the stolList
        restStolMockMvc.perform(get("/api/stols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stol.getId().intValue())))
            .andExpect(jsonPath("$.[*].xpozicijaStola").value(hasItem(DEFAULT_XPOZICIJA_STOLA)))
            .andExpect(jsonPath("$.[*].ypozicijaStola").value(hasItem(DEFAULT_YPOZICIJA_STOLA)))
            .andExpect(jsonPath("$.[*].pripadaSegmentu").value(hasItem(DEFAULT_PRIPADA_SEGMENTU.toString())));
    }

    @Test
    @Transactional
    public void getStol() throws Exception {
        // Initialize the database
        stolRepository.saveAndFlush(stol);

        // Get the stol
        restStolMockMvc.perform(get("/api/stols/{id}", stol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stol.getId().intValue()))
            .andExpect(jsonPath("$.xpozicijaStola").value(DEFAULT_XPOZICIJA_STOLA))
            .andExpect(jsonPath("$.ypozicijaStola").value(DEFAULT_YPOZICIJA_STOLA))
            .andExpect(jsonPath("$.pripadaSegmentu").value(DEFAULT_PRIPADA_SEGMENTU.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStol() throws Exception {
        // Get the stol
        restStolMockMvc.perform(get("/api/stols/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStol() throws Exception {
        // Initialize the database
        stolRepository.saveAndFlush(stol);
        int databaseSizeBeforeUpdate = stolRepository.findAll().size();

        // Update the stol
        Stol updatedStol = stolRepository.findOne(stol.getId());
        updatedStol
            .xpozicijaStola(UPDATED_XPOZICIJA_STOLA)
            .ypozicijaStola(UPDATED_YPOZICIJA_STOLA)
            .pripadaSegmentu(UPDATED_PRIPADA_SEGMENTU);

        restStolMockMvc.perform(put("/api/stols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStol)))
            .andExpect(status().isOk());

        // Validate the Stol in the database
        List<Stol> stolList = stolRepository.findAll();
        assertThat(stolList).hasSize(databaseSizeBeforeUpdate);
        Stol testStol = stolList.get(stolList.size() - 1);
        assertThat(testStol.getXpozicijaStola()).isEqualTo(UPDATED_XPOZICIJA_STOLA);
        assertThat(testStol.getYpozicijaStola()).isEqualTo(UPDATED_YPOZICIJA_STOLA);
        assertThat(testStol.getPripadaSegmentu()).isEqualTo(UPDATED_PRIPADA_SEGMENTU);
    }

    @Test
    @Transactional
    public void updateNonExistingStol() throws Exception {
        int databaseSizeBeforeUpdate = stolRepository.findAll().size();

        // Create the Stol

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStolMockMvc.perform(put("/api/stols")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stol)))
            .andExpect(status().isCreated());

        // Validate the Stol in the database
        List<Stol> stolList = stolRepository.findAll();
        assertThat(stolList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStol() throws Exception {
        // Initialize the database
        stolRepository.saveAndFlush(stol);
        int databaseSizeBeforeDelete = stolRepository.findAll().size();

        // Get the stol
        restStolMockMvc.perform(delete("/api/stols/{id}", stol.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Stol> stolList = stolRepository.findAll();
        assertThat(stolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stol.class);
        Stol stol1 = new Stol();
        stol1.setId(1L);
        Stol stol2 = new Stol();
        stol2.setId(stol1.getId());
        assertThat(stol1).isEqualTo(stol2);
        stol2.setId(2L);
        assertThat(stol1).isNotEqualTo(stol2);
        stol1.setId(null);
        assertThat(stol1).isNotEqualTo(stol2);
    }
}
