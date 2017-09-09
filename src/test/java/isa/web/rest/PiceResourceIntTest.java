package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Pice;
import isa.repository.PiceRepository;
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
 * Test class for the PiceResource REST controller.
 *
 * @see PiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class PiceResourceIntTest {

    private static final String DEFAULT_IME_JELA = "AAAAAAAAAA";
    private static final String UPDATED_IME_JELA = "BBBBBBBBBB";

    private static final String DEFAULT_KRATKI_TEKST = "AAAAAAAAAA";
    private static final String UPDATED_KRATKI_TEKST = "BBBBBBBBBB";

    private static final Double DEFAULT_CENA = 1D;
    private static final Double UPDATED_CENA = 2D;

    @Autowired
    private PiceRepository piceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPiceMockMvc;

    private Pice pice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PiceResource piceResource = new PiceResource(piceRepository);
        this.restPiceMockMvc = MockMvcBuilders.standaloneSetup(piceResource)
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
    public static Pice createEntity(EntityManager em) {
        Pice pice = new Pice()
            .imeJela(DEFAULT_IME_JELA)
            .kratkiTekst(DEFAULT_KRATKI_TEKST)
            .cena(DEFAULT_CENA);
        return pice;
    }

    @Before
    public void initTest() {
        pice = createEntity(em);
    }

    @Test
    @Transactional
    public void createPice() throws Exception {
        int databaseSizeBeforeCreate = piceRepository.findAll().size();

        // Create the Pice
        restPiceMockMvc.perform(post("/api/pices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pice)))
            .andExpect(status().isCreated());

        // Validate the Pice in the database
        List<Pice> piceList = piceRepository.findAll();
        assertThat(piceList).hasSize(databaseSizeBeforeCreate + 1);
        Pice testPice = piceList.get(piceList.size() - 1);
        assertThat(testPice.getImeJela()).isEqualTo(DEFAULT_IME_JELA);
        assertThat(testPice.getKratkiTekst()).isEqualTo(DEFAULT_KRATKI_TEKST);
        assertThat(testPice.getCena()).isEqualTo(DEFAULT_CENA);
    }

    @Test
    @Transactional
    public void createPiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = piceRepository.findAll().size();

        // Create the Pice with an existing ID
        pice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPiceMockMvc.perform(post("/api/pices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pice)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pice> piceList = piceRepository.findAll();
        assertThat(piceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPices() throws Exception {
        // Initialize the database
        piceRepository.saveAndFlush(pice);

        // Get all the piceList
        restPiceMockMvc.perform(get("/api/pices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pice.getId().intValue())))
            .andExpect(jsonPath("$.[*].imeJela").value(hasItem(DEFAULT_IME_JELA.toString())))
            .andExpect(jsonPath("$.[*].kratkiTekst").value(hasItem(DEFAULT_KRATKI_TEKST.toString())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.doubleValue())));
    }

    @Test
    @Transactional
    public void getPice() throws Exception {
        // Initialize the database
        piceRepository.saveAndFlush(pice);

        // Get the pice
        restPiceMockMvc.perform(get("/api/pices/{id}", pice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pice.getId().intValue()))
            .andExpect(jsonPath("$.imeJela").value(DEFAULT_IME_JELA.toString()))
            .andExpect(jsonPath("$.kratkiTekst").value(DEFAULT_KRATKI_TEKST.toString()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPice() throws Exception {
        // Get the pice
        restPiceMockMvc.perform(get("/api/pices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePice() throws Exception {
        // Initialize the database
        piceRepository.saveAndFlush(pice);
        int databaseSizeBeforeUpdate = piceRepository.findAll().size();

        // Update the pice
        Pice updatedPice = piceRepository.findOne(pice.getId());
        updatedPice
            .imeJela(UPDATED_IME_JELA)
            .kratkiTekst(UPDATED_KRATKI_TEKST)
            .cena(UPDATED_CENA);

        restPiceMockMvc.perform(put("/api/pices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPice)))
            .andExpect(status().isOk());

        // Validate the Pice in the database
        List<Pice> piceList = piceRepository.findAll();
        assertThat(piceList).hasSize(databaseSizeBeforeUpdate);
        Pice testPice = piceList.get(piceList.size() - 1);
        assertThat(testPice.getImeJela()).isEqualTo(UPDATED_IME_JELA);
        assertThat(testPice.getKratkiTekst()).isEqualTo(UPDATED_KRATKI_TEKST);
        assertThat(testPice.getCena()).isEqualTo(UPDATED_CENA);
    }

    @Test
    @Transactional
    public void updateNonExistingPice() throws Exception {
        int databaseSizeBeforeUpdate = piceRepository.findAll().size();

        // Create the Pice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPiceMockMvc.perform(put("/api/pices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pice)))
            .andExpect(status().isCreated());

        // Validate the Pice in the database
        List<Pice> piceList = piceRepository.findAll();
        assertThat(piceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePice() throws Exception {
        // Initialize the database
        piceRepository.saveAndFlush(pice);
        int databaseSizeBeforeDelete = piceRepository.findAll().size();

        // Get the pice
        restPiceMockMvc.perform(delete("/api/pices/{id}", pice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pice> piceList = piceRepository.findAll();
        assertThat(piceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pice.class);
        Pice pice1 = new Pice();
        pice1.setId(1L);
        Pice pice2 = new Pice();
        pice2.setId(pice1.getId());
        assertThat(pice1).isEqualTo(pice2);
        pice2.setId(2L);
        assertThat(pice1).isNotEqualTo(pice2);
        pice1.setId(null);
        assertThat(pice1).isNotEqualTo(pice2);
    }
}
