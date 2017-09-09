package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Jelo;
import isa.repository.JeloRepository;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JeloResource REST controller.
 *
 * @see JeloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class JeloResourceIntTest {

    private static final String DEFAULT_IME_JELA = "AAAAAAAAAA";
    private static final String UPDATED_IME_JELA = "BBBBBBBBBB";

    private static final String DEFAULT_KRATKI_TEKST = "AAAAAAAAAA";
    private static final String UPDATED_KRATKI_TEKST = "BBBBBBBBBB";

    private static final Double DEFAULT_CENA = 1D;
    private static final Double UPDATED_CENA = 2D;

    @Autowired
    private JeloRepository jeloRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJeloMockMvc;

    private Jelo jelo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JeloResource jeloResource = new JeloResource(jeloRepository);
        this.restJeloMockMvc = MockMvcBuilders.standaloneSetup(jeloResource)
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
    public static Jelo createEntity(EntityManager em) {
        Jelo jelo = new Jelo()
            .imeJela(DEFAULT_IME_JELA)
            .kratkiTekst(DEFAULT_KRATKI_TEKST)
            .cena(DEFAULT_CENA);
        return jelo;
    }

    @Before
    public void initTest() {
        jelo = createEntity(em);
    }

    @Test
    @Transactional
    public void createJelo() throws Exception {
        int databaseSizeBeforeCreate = jeloRepository.findAll().size();

        // Create the Jelo
        restJeloMockMvc.perform(post("/api/jelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jelo)))
            .andExpect(status().isCreated());

        // Validate the Jelo in the database
        List<Jelo> jeloList = jeloRepository.findAll();
        assertThat(jeloList).hasSize(databaseSizeBeforeCreate + 1);
        Jelo testJelo = jeloList.get(jeloList.size() - 1);
        assertThat(testJelo.getImeJela()).isEqualTo(DEFAULT_IME_JELA);
        assertThat(testJelo.getKratkiTekst()).isEqualTo(DEFAULT_KRATKI_TEKST);
        assertThat(testJelo.getCena()).isEqualTo(DEFAULT_CENA);
    }

    @Test
    @Transactional
    public void createJeloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jeloRepository.findAll().size();

        // Create the Jelo with an existing ID
        jelo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJeloMockMvc.perform(post("/api/jelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jelo)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Jelo> jeloList = jeloRepository.findAll();
        assertThat(jeloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJelos() throws Exception {
        // Initialize the database
        jeloRepository.saveAndFlush(jelo);

        // Get all the jeloList
        restJeloMockMvc.perform(get("/api/jelos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jelo.getId().intValue())))
            .andExpect(jsonPath("$.[*].imeJela").value(hasItem(DEFAULT_IME_JELA.toString())))
            .andExpect(jsonPath("$.[*].kratkiTekst").value(hasItem(DEFAULT_KRATKI_TEKST.toString())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.doubleValue())));
    }

    @Test
    @Transactional
    public void getJelo() throws Exception {
        // Initialize the database
        jeloRepository.saveAndFlush(jelo);

        // Get the jelo
        restJeloMockMvc.perform(get("/api/jelos/{id}", jelo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jelo.getId().intValue()))
            .andExpect(jsonPath("$.imeJela").value(DEFAULT_IME_JELA.toString()))
            .andExpect(jsonPath("$.kratkiTekst").value(DEFAULT_KRATKI_TEKST.toString()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJelo() throws Exception {
        // Get the jelo
        restJeloMockMvc.perform(get("/api/jelos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJelo() throws Exception {
        // Initialize the database
        jeloRepository.saveAndFlush(jelo);
        int databaseSizeBeforeUpdate = jeloRepository.findAll().size();

        // Update the jelo
        Jelo updatedJelo = jeloRepository.findOne(jelo.getId());
        updatedJelo
            .imeJela(UPDATED_IME_JELA)
            .kratkiTekst(UPDATED_KRATKI_TEKST)
            .cena(UPDATED_CENA);

        restJeloMockMvc.perform(put("/api/jelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJelo)))
            .andExpect(status().isOk());

        // Validate the Jelo in the database
        List<Jelo> jeloList = jeloRepository.findAll();
        assertThat(jeloList).hasSize(databaseSizeBeforeUpdate);
        Jelo testJelo = jeloList.get(jeloList.size() - 1);
        assertThat(testJelo.getImeJela()).isEqualTo(UPDATED_IME_JELA);
        assertThat(testJelo.getKratkiTekst()).isEqualTo(UPDATED_KRATKI_TEKST);
        assertThat(testJelo.getCena()).isEqualTo(UPDATED_CENA);
    }

    @Test
    @Transactional
    public void updateNonExistingJelo() throws Exception {
        int databaseSizeBeforeUpdate = jeloRepository.findAll().size();

        // Create the Jelo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJeloMockMvc.perform(put("/api/jelos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jelo)))
            .andExpect(status().isCreated());

        // Validate the Jelo in the database
        List<Jelo> jeloList = jeloRepository.findAll();
        assertThat(jeloList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJelo() throws Exception {
        // Initialize the database
        jeloRepository.saveAndFlush(jelo);
        int databaseSizeBeforeDelete = jeloRepository.findAll().size();

        // Get the jelo
        restJeloMockMvc.perform(delete("/api/jelos/{id}", jelo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jelo> jeloList = jeloRepository.findAll();
        assertThat(jeloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jelo.class);
        Jelo jelo1 = new Jelo();
        jelo1.setId(1L);
        Jelo jelo2 = new Jelo();
        jelo2.setId(jelo1.getId());
        assertThat(jelo1).isEqualTo(jelo2);
        jelo2.setId(2L);
        assertThat(jelo1).isNotEqualTo(jelo2);
        jelo1.setId(null);
        assertThat(jelo1).isNotEqualTo(jelo2);
    }
}
