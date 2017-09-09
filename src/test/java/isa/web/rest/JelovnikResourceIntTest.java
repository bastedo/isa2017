package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Jelovnik;
import isa.repository.JelovnikRepository;
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
 * Test class for the JelovnikResource REST controller.
 *
 * @see JelovnikResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class JelovnikResourceIntTest {

    private static final String DEFAULT_IME_JELOVNIKA = "AAAAAAAAAA";
    private static final String UPDATED_IME_JELOVNIKA = "BBBBBBBBBB";

    @Autowired
    private JelovnikRepository jelovnikRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJelovnikMockMvc;

    private Jelovnik jelovnik;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JelovnikResource jelovnikResource = new JelovnikResource(jelovnikRepository);
        this.restJelovnikMockMvc = MockMvcBuilders.standaloneSetup(jelovnikResource)
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
    public static Jelovnik createEntity(EntityManager em) {
        Jelovnik jelovnik = new Jelovnik()
            .imeJelovnika(DEFAULT_IME_JELOVNIKA);
        return jelovnik;
    }

    @Before
    public void initTest() {
        jelovnik = createEntity(em);
    }

    @Test
    @Transactional
    public void createJelovnik() throws Exception {
        int databaseSizeBeforeCreate = jelovnikRepository.findAll().size();

        // Create the Jelovnik
        restJelovnikMockMvc.perform(post("/api/jelovniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jelovnik)))
            .andExpect(status().isCreated());

        // Validate the Jelovnik in the database
        List<Jelovnik> jelovnikList = jelovnikRepository.findAll();
        assertThat(jelovnikList).hasSize(databaseSizeBeforeCreate + 1);
        Jelovnik testJelovnik = jelovnikList.get(jelovnikList.size() - 1);
        assertThat(testJelovnik.getImeJelovnika()).isEqualTo(DEFAULT_IME_JELOVNIKA);
    }

    @Test
    @Transactional
    public void createJelovnikWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jelovnikRepository.findAll().size();

        // Create the Jelovnik with an existing ID
        jelovnik.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJelovnikMockMvc.perform(post("/api/jelovniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jelovnik)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Jelovnik> jelovnikList = jelovnikRepository.findAll();
        assertThat(jelovnikList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJelovniks() throws Exception {
        // Initialize the database
        jelovnikRepository.saveAndFlush(jelovnik);

        // Get all the jelovnikList
        restJelovnikMockMvc.perform(get("/api/jelovniks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jelovnik.getId().intValue())))
            .andExpect(jsonPath("$.[*].imeJelovnika").value(hasItem(DEFAULT_IME_JELOVNIKA.toString())));
    }

    @Test
    @Transactional
    public void getJelovnik() throws Exception {
        // Initialize the database
        jelovnikRepository.saveAndFlush(jelovnik);

        // Get the jelovnik
        restJelovnikMockMvc.perform(get("/api/jelovniks/{id}", jelovnik.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jelovnik.getId().intValue()))
            .andExpect(jsonPath("$.imeJelovnika").value(DEFAULT_IME_JELOVNIKA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJelovnik() throws Exception {
        // Get the jelovnik
        restJelovnikMockMvc.perform(get("/api/jelovniks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJelovnik() throws Exception {
        // Initialize the database
        jelovnikRepository.saveAndFlush(jelovnik);
        int databaseSizeBeforeUpdate = jelovnikRepository.findAll().size();

        // Update the jelovnik
        Jelovnik updatedJelovnik = jelovnikRepository.findOne(jelovnik.getId());
        updatedJelovnik
            .imeJelovnika(UPDATED_IME_JELOVNIKA);

        restJelovnikMockMvc.perform(put("/api/jelovniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJelovnik)))
            .andExpect(status().isOk());

        // Validate the Jelovnik in the database
        List<Jelovnik> jelovnikList = jelovnikRepository.findAll();
        assertThat(jelovnikList).hasSize(databaseSizeBeforeUpdate);
        Jelovnik testJelovnik = jelovnikList.get(jelovnikList.size() - 1);
        assertThat(testJelovnik.getImeJelovnika()).isEqualTo(UPDATED_IME_JELOVNIKA);
    }

    @Test
    @Transactional
    public void updateNonExistingJelovnik() throws Exception {
        int databaseSizeBeforeUpdate = jelovnikRepository.findAll().size();

        // Create the Jelovnik

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJelovnikMockMvc.perform(put("/api/jelovniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jelovnik)))
            .andExpect(status().isCreated());

        // Validate the Jelovnik in the database
        List<Jelovnik> jelovnikList = jelovnikRepository.findAll();
        assertThat(jelovnikList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJelovnik() throws Exception {
        // Initialize the database
        jelovnikRepository.saveAndFlush(jelovnik);
        int databaseSizeBeforeDelete = jelovnikRepository.findAll().size();

        // Get the jelovnik
        restJelovnikMockMvc.perform(delete("/api/jelovniks/{id}", jelovnik.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jelovnik> jelovnikList = jelovnikRepository.findAll();
        assertThat(jelovnikList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jelovnik.class);
        Jelovnik jelovnik1 = new Jelovnik();
        jelovnik1.setId(1L);
        Jelovnik jelovnik2 = new Jelovnik();
        jelovnik2.setId(jelovnik1.getId());
        assertThat(jelovnik1).isEqualTo(jelovnik2);
        jelovnik2.setId(2L);
        assertThat(jelovnik1).isNotEqualTo(jelovnik2);
        jelovnik1.setId(null);
        assertThat(jelovnik1).isNotEqualTo(jelovnik2);
    }
}
