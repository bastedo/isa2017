package isa.web.rest;

import isa.Isa2017App;

import isa.domain.Zaposleni;
import isa.repository.ZaposleniRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import isa.domain.enumeration.VrstaZaposlenja;
import isa.domain.enumeration.Segmenat;
/**
 * Test class for the ZaposleniResource REST controller.
 *
 * @see ZaposleniResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Isa2017App.class)
public class ZaposleniResourceIntTest {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final VrstaZaposlenja DEFAULT_VRSTA_ZAPOSLENJA = VrstaZaposlenja.KONOBAR;
    private static final VrstaZaposlenja UPDATED_VRSTA_ZAPOSLENJA = VrstaZaposlenja.SANKER;

    private static final Segmenat DEFAULT_ZADUZENJE_ZA_SEGMENT = Segmenat.ZA_PUSACE;
    private static final Segmenat UPDATED_ZADUZENJE_ZA_SEGMENT = Segmenat.OTVORENA_BASTA;

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_LOZINKA = "AAAAAAAAAA";
    private static final String UPDATED_LOZINKA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM_RODJENJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_RODJENJA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_KONFEKCISKI_BROJ = 1;
    private static final Integer UPDATED_KONFEKCISKI_BROJ = 2;

    private static final Integer DEFAULT_VELICINA_OBUCE = 1;
    private static final Integer UPDATED_VELICINA_OBUCE = 2;

    private static final Boolean DEFAULT_FIRST_LOGIN = false;
    private static final Boolean UPDATED_FIRST_LOGIN = true;

    @Autowired
    private ZaposleniRepository zaposleniRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZaposleniMockMvc;

    private Zaposleni zaposleni;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZaposleniResource zaposleniResource = new ZaposleniResource(zaposleniRepository);
        this.restZaposleniMockMvc = MockMvcBuilders.standaloneSetup(zaposleniResource)
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
    public static Zaposleni createEntity(EntityManager em) {
        Zaposleni zaposleni = new Zaposleni()
            .ime(DEFAULT_IME)
            .vrstaZaposlenja(DEFAULT_VRSTA_ZAPOSLENJA)
            .zaduzenjeZaSegment(DEFAULT_ZADUZENJE_ZA_SEGMENT)
            .prezime(DEFAULT_PREZIME)
            .email(DEFAULT_EMAIL)
            .lozinka(DEFAULT_LOZINKA)
            .datumRodjenja(DEFAULT_DATUM_RODJENJA)
            .konfekciskiBroj(DEFAULT_KONFEKCISKI_BROJ)
            .velicinaObuce(DEFAULT_VELICINA_OBUCE)
            .firstLogin(DEFAULT_FIRST_LOGIN);
        return zaposleni;
    }

    @Before
    public void initTest() {
        zaposleni = createEntity(em);
    }

    @Test
    @Transactional
    public void createZaposleni() throws Exception {
        int databaseSizeBeforeCreate = zaposleniRepository.findAll().size();

        // Create the Zaposleni
        restZaposleniMockMvc.perform(post("/api/zaposlenis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zaposleni)))
            .andExpect(status().isCreated());

        // Validate the Zaposleni in the database
        List<Zaposleni> zaposleniList = zaposleniRepository.findAll();
        assertThat(zaposleniList).hasSize(databaseSizeBeforeCreate + 1);
        Zaposleni testZaposleni = zaposleniList.get(zaposleniList.size() - 1);
        assertThat(testZaposleni.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testZaposleni.getVrstaZaposlenja()).isEqualTo(DEFAULT_VRSTA_ZAPOSLENJA);
        assertThat(testZaposleni.getZaduzenjeZaSegment()).isEqualTo(DEFAULT_ZADUZENJE_ZA_SEGMENT);
        assertThat(testZaposleni.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testZaposleni.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testZaposleni.getLozinka()).isEqualTo(DEFAULT_LOZINKA);
        assertThat(testZaposleni.getDatumRodjenja()).isEqualTo(DEFAULT_DATUM_RODJENJA);
        assertThat(testZaposleni.getKonfekciskiBroj()).isEqualTo(DEFAULT_KONFEKCISKI_BROJ);
        assertThat(testZaposleni.getVelicinaObuce()).isEqualTo(DEFAULT_VELICINA_OBUCE);
        assertThat(testZaposleni.isFirstLogin()).isEqualTo(DEFAULT_FIRST_LOGIN);
    }

    @Test
    @Transactional
    public void createZaposleniWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zaposleniRepository.findAll().size();

        // Create the Zaposleni with an existing ID
        zaposleni.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZaposleniMockMvc.perform(post("/api/zaposlenis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zaposleni)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Zaposleni> zaposleniList = zaposleniRepository.findAll();
        assertThat(zaposleniList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllZaposlenis() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);

        // Get all the zaposleniList
        restZaposleniMockMvc.perform(get("/api/zaposlenis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zaposleni.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
            .andExpect(jsonPath("$.[*].vrstaZaposlenja").value(hasItem(DEFAULT_VRSTA_ZAPOSLENJA.toString())))
            .andExpect(jsonPath("$.[*].zaduzenjeZaSegment").value(hasItem(DEFAULT_ZADUZENJE_ZA_SEGMENT.toString())))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].lozinka").value(hasItem(DEFAULT_LOZINKA.toString())))
            .andExpect(jsonPath("$.[*].datumRodjenja").value(hasItem(DEFAULT_DATUM_RODJENJA.toString())))
            .andExpect(jsonPath("$.[*].konfekciskiBroj").value(hasItem(DEFAULT_KONFEKCISKI_BROJ)))
            .andExpect(jsonPath("$.[*].velicinaObuce").value(hasItem(DEFAULT_VELICINA_OBUCE)))
            .andExpect(jsonPath("$.[*].firstLogin").value(hasItem(DEFAULT_FIRST_LOGIN.booleanValue())));
    }

    @Test
    @Transactional
    public void getZaposleni() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);

        // Get the zaposleni
        restZaposleniMockMvc.perform(get("/api/zaposlenis/{id}", zaposleni.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zaposleni.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.vrstaZaposlenja").value(DEFAULT_VRSTA_ZAPOSLENJA.toString()))
            .andExpect(jsonPath("$.zaduzenjeZaSegment").value(DEFAULT_ZADUZENJE_ZA_SEGMENT.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.lozinka").value(DEFAULT_LOZINKA.toString()))
            .andExpect(jsonPath("$.datumRodjenja").value(DEFAULT_DATUM_RODJENJA.toString()))
            .andExpect(jsonPath("$.konfekciskiBroj").value(DEFAULT_KONFEKCISKI_BROJ))
            .andExpect(jsonPath("$.velicinaObuce").value(DEFAULT_VELICINA_OBUCE))
            .andExpect(jsonPath("$.firstLogin").value(DEFAULT_FIRST_LOGIN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingZaposleni() throws Exception {
        // Get the zaposleni
        restZaposleniMockMvc.perform(get("/api/zaposlenis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZaposleni() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);
        int databaseSizeBeforeUpdate = zaposleniRepository.findAll().size();

        // Update the zaposleni
        Zaposleni updatedZaposleni = zaposleniRepository.findOne(zaposleni.getId());
        updatedZaposleni
            .ime(UPDATED_IME)
            .vrstaZaposlenja(UPDATED_VRSTA_ZAPOSLENJA)
            .zaduzenjeZaSegment(UPDATED_ZADUZENJE_ZA_SEGMENT)
            .prezime(UPDATED_PREZIME)
            .email(UPDATED_EMAIL)
            .lozinka(UPDATED_LOZINKA)
            .datumRodjenja(UPDATED_DATUM_RODJENJA)
            .konfekciskiBroj(UPDATED_KONFEKCISKI_BROJ)
            .velicinaObuce(UPDATED_VELICINA_OBUCE)
            .firstLogin(UPDATED_FIRST_LOGIN);

        restZaposleniMockMvc.perform(put("/api/zaposlenis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZaposleni)))
            .andExpect(status().isOk());

        // Validate the Zaposleni in the database
        List<Zaposleni> zaposleniList = zaposleniRepository.findAll();
        assertThat(zaposleniList).hasSize(databaseSizeBeforeUpdate);
        Zaposleni testZaposleni = zaposleniList.get(zaposleniList.size() - 1);
        assertThat(testZaposleni.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testZaposleni.getVrstaZaposlenja()).isEqualTo(UPDATED_VRSTA_ZAPOSLENJA);
        assertThat(testZaposleni.getZaduzenjeZaSegment()).isEqualTo(UPDATED_ZADUZENJE_ZA_SEGMENT);
        assertThat(testZaposleni.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testZaposleni.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testZaposleni.getLozinka()).isEqualTo(UPDATED_LOZINKA);
        assertThat(testZaposleni.getDatumRodjenja()).isEqualTo(UPDATED_DATUM_RODJENJA);
        assertThat(testZaposleni.getKonfekciskiBroj()).isEqualTo(UPDATED_KONFEKCISKI_BROJ);
        assertThat(testZaposleni.getVelicinaObuce()).isEqualTo(UPDATED_VELICINA_OBUCE);
        assertThat(testZaposleni.isFirstLogin()).isEqualTo(UPDATED_FIRST_LOGIN);
    }

    @Test
    @Transactional
    public void updateNonExistingZaposleni() throws Exception {
        int databaseSizeBeforeUpdate = zaposleniRepository.findAll().size();

        // Create the Zaposleni

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZaposleniMockMvc.perform(put("/api/zaposlenis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zaposleni)))
            .andExpect(status().isCreated());

        // Validate the Zaposleni in the database
        List<Zaposleni> zaposleniList = zaposleniRepository.findAll();
        assertThat(zaposleniList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteZaposleni() throws Exception {
        // Initialize the database
        zaposleniRepository.saveAndFlush(zaposleni);
        int databaseSizeBeforeDelete = zaposleniRepository.findAll().size();

        // Get the zaposleni
        restZaposleniMockMvc.perform(delete("/api/zaposlenis/{id}", zaposleni.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Zaposleni> zaposleniList = zaposleniRepository.findAll();
        assertThat(zaposleniList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zaposleni.class);
        Zaposleni zaposleni1 = new Zaposleni();
        zaposleni1.setId(1L);
        Zaposleni zaposleni2 = new Zaposleni();
        zaposleni2.setId(zaposleni1.getId());
        assertThat(zaposleni1).isEqualTo(zaposleni2);
        zaposleni2.setId(2L);
        assertThat(zaposleni1).isNotEqualTo(zaposleni2);
        zaposleni1.setId(null);
        assertThat(zaposleni1).isNotEqualTo(zaposleni2);
    }
}
