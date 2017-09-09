package isa.web.rest;

import isa.Isa2017App;

import isa.domain.PorudzbinaZANabavku;
import isa.repository.PorudzbinaZANabavkuRepository;
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
 * Test class for the PorudzbinaZANabavkuResource REST controller.
 *
 * @see PorudzbinaZANabavkuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class PorudzbinaZANabavkuResourceIntTest {

    private static final String DEFAULT_PORUDZBINA = "AAAAAAAAAA";
    private static final String UPDATED_PORUDZBINA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIHVACENA = false;
    private static final Boolean UPDATED_PRIHVACENA = true;

    private static final Double DEFAULT_VREDNOST = 1D;
    private static final Double UPDATED_VREDNOST = 2D;

    private static final ZonedDateTime DEFAULT_DOSTAVA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DOSTAVA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PorudzbinaZANabavkuRepository porudzbinaZANabavkuRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPorudzbinaZANabavkuMockMvc;

    private PorudzbinaZANabavku porudzbinaZANabavku;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PorudzbinaZANabavkuResource porudzbinaZANabavkuResource = new PorudzbinaZANabavkuResource(porudzbinaZANabavkuRepository);
        this.restPorudzbinaZANabavkuMockMvc = MockMvcBuilders.standaloneSetup(porudzbinaZANabavkuResource)
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
    public static PorudzbinaZANabavku createEntity(EntityManager em) {
        PorudzbinaZANabavku porudzbinaZANabavku = new PorudzbinaZANabavku()
            .porudzbina(DEFAULT_PORUDZBINA)
            .prihvacena(DEFAULT_PRIHVACENA)
            .vrednost(DEFAULT_VREDNOST)
            .dostava(DEFAULT_DOSTAVA);
        return porudzbinaZANabavku;
    }

    @Before
    public void initTest() {
        porudzbinaZANabavku = createEntity(em);
    }

    @Test
    @Transactional
    public void createPorudzbinaZANabavku() throws Exception {
        int databaseSizeBeforeCreate = porudzbinaZANabavkuRepository.findAll().size();

        // Create the PorudzbinaZANabavku
        restPorudzbinaZANabavkuMockMvc.perform(post("/api/porudzbina-za-nabavkus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(porudzbinaZANabavku)))
            .andExpect(status().isCreated());

        // Validate the PorudzbinaZANabavku in the database
        List<PorudzbinaZANabavku> porudzbinaZANabavkuList = porudzbinaZANabavkuRepository.findAll();
        assertThat(porudzbinaZANabavkuList).hasSize(databaseSizeBeforeCreate + 1);
        PorudzbinaZANabavku testPorudzbinaZANabavku = porudzbinaZANabavkuList.get(porudzbinaZANabavkuList.size() - 1);
        assertThat(testPorudzbinaZANabavku.getPorudzbina()).isEqualTo(DEFAULT_PORUDZBINA);
        assertThat(testPorudzbinaZANabavku.isPrihvacena()).isEqualTo(DEFAULT_PRIHVACENA);
        assertThat(testPorudzbinaZANabavku.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
        assertThat(testPorudzbinaZANabavku.getDostava()).isEqualTo(DEFAULT_DOSTAVA);
    }

    @Test
    @Transactional
    public void createPorudzbinaZANabavkuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = porudzbinaZANabavkuRepository.findAll().size();

        // Create the PorudzbinaZANabavku with an existing ID
        porudzbinaZANabavku.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPorudzbinaZANabavkuMockMvc.perform(post("/api/porudzbina-za-nabavkus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(porudzbinaZANabavku)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PorudzbinaZANabavku> porudzbinaZANabavkuList = porudzbinaZANabavkuRepository.findAll();
        assertThat(porudzbinaZANabavkuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPorudzbinaZANabavkus() throws Exception {
        // Initialize the database
        porudzbinaZANabavkuRepository.saveAndFlush(porudzbinaZANabavku);

        // Get all the porudzbinaZANabavkuList
        restPorudzbinaZANabavkuMockMvc.perform(get("/api/porudzbina-za-nabavkus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(porudzbinaZANabavku.getId().intValue())))
            .andExpect(jsonPath("$.[*].porudzbina").value(hasItem(DEFAULT_PORUDZBINA.toString())))
            .andExpect(jsonPath("$.[*].prihvacena").value(hasItem(DEFAULT_PRIHVACENA.booleanValue())))
            .andExpect(jsonPath("$.[*].vrednost").value(hasItem(DEFAULT_VREDNOST.doubleValue())))
            .andExpect(jsonPath("$.[*].dostava").value(hasItem(sameInstant(DEFAULT_DOSTAVA))));
    }

    @Test
    @Transactional
    public void getPorudzbinaZANabavku() throws Exception {
        // Initialize the database
        porudzbinaZANabavkuRepository.saveAndFlush(porudzbinaZANabavku);

        // Get the porudzbinaZANabavku
        restPorudzbinaZANabavkuMockMvc.perform(get("/api/porudzbina-za-nabavkus/{id}", porudzbinaZANabavku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(porudzbinaZANabavku.getId().intValue()))
            .andExpect(jsonPath("$.porudzbina").value(DEFAULT_PORUDZBINA.toString()))
            .andExpect(jsonPath("$.prihvacena").value(DEFAULT_PRIHVACENA.booleanValue()))
            .andExpect(jsonPath("$.vrednost").value(DEFAULT_VREDNOST.doubleValue()))
            .andExpect(jsonPath("$.dostava").value(sameInstant(DEFAULT_DOSTAVA)));
    }

    @Test
    @Transactional
    public void getNonExistingPorudzbinaZANabavku() throws Exception {
        // Get the porudzbinaZANabavku
        restPorudzbinaZANabavkuMockMvc.perform(get("/api/porudzbina-za-nabavkus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePorudzbinaZANabavku() throws Exception {
        // Initialize the database
        porudzbinaZANabavkuRepository.saveAndFlush(porudzbinaZANabavku);
        int databaseSizeBeforeUpdate = porudzbinaZANabavkuRepository.findAll().size();

        // Update the porudzbinaZANabavku
        PorudzbinaZANabavku updatedPorudzbinaZANabavku = porudzbinaZANabavkuRepository.findOne(porudzbinaZANabavku.getId());
        updatedPorudzbinaZANabavku
            .porudzbina(UPDATED_PORUDZBINA)
            .prihvacena(UPDATED_PRIHVACENA)
            .vrednost(UPDATED_VREDNOST)
            .dostava(UPDATED_DOSTAVA);

        restPorudzbinaZANabavkuMockMvc.perform(put("/api/porudzbina-za-nabavkus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPorudzbinaZANabavku)))
            .andExpect(status().isOk());

        // Validate the PorudzbinaZANabavku in the database
        List<PorudzbinaZANabavku> porudzbinaZANabavkuList = porudzbinaZANabavkuRepository.findAll();
        assertThat(porudzbinaZANabavkuList).hasSize(databaseSizeBeforeUpdate);
        PorudzbinaZANabavku testPorudzbinaZANabavku = porudzbinaZANabavkuList.get(porudzbinaZANabavkuList.size() - 1);
        assertThat(testPorudzbinaZANabavku.getPorudzbina()).isEqualTo(UPDATED_PORUDZBINA);
        assertThat(testPorudzbinaZANabavku.isPrihvacena()).isEqualTo(UPDATED_PRIHVACENA);
        assertThat(testPorudzbinaZANabavku.getVrednost()).isEqualTo(UPDATED_VREDNOST);
        assertThat(testPorudzbinaZANabavku.getDostava()).isEqualTo(UPDATED_DOSTAVA);
    }

    @Test
    @Transactional
    public void updateNonExistingPorudzbinaZANabavku() throws Exception {
        int databaseSizeBeforeUpdate = porudzbinaZANabavkuRepository.findAll().size();

        // Create the PorudzbinaZANabavku

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPorudzbinaZANabavkuMockMvc.perform(put("/api/porudzbina-za-nabavkus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(porudzbinaZANabavku)))
            .andExpect(status().isCreated());

        // Validate the PorudzbinaZANabavku in the database
        List<PorudzbinaZANabavku> porudzbinaZANabavkuList = porudzbinaZANabavkuRepository.findAll();
        assertThat(porudzbinaZANabavkuList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePorudzbinaZANabavku() throws Exception {
        // Initialize the database
        porudzbinaZANabavkuRepository.saveAndFlush(porudzbinaZANabavku);
        int databaseSizeBeforeDelete = porudzbinaZANabavkuRepository.findAll().size();

        // Get the porudzbinaZANabavku
        restPorudzbinaZANabavkuMockMvc.perform(delete("/api/porudzbina-za-nabavkus/{id}", porudzbinaZANabavku.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PorudzbinaZANabavku> porudzbinaZANabavkuList = porudzbinaZANabavkuRepository.findAll();
        assertThat(porudzbinaZANabavkuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PorudzbinaZANabavku.class);
        PorudzbinaZANabavku porudzbinaZANabavku1 = new PorudzbinaZANabavku();
        porudzbinaZANabavku1.setId(1L);
        PorudzbinaZANabavku porudzbinaZANabavku2 = new PorudzbinaZANabavku();
        porudzbinaZANabavku2.setId(porudzbinaZANabavku1.getId());
        assertThat(porudzbinaZANabavku1).isEqualTo(porudzbinaZANabavku2);
        porudzbinaZANabavku2.setId(2L);
        assertThat(porudzbinaZANabavku1).isNotEqualTo(porudzbinaZANabavku2);
        porudzbinaZANabavku1.setId(null);
        assertThat(porudzbinaZANabavku1).isNotEqualTo(porudzbinaZANabavku2);
    }
}
