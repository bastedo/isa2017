package isa.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(isa.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(isa.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(isa.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Gost.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Gost.class.getName() + ".rezervacijes", jcacheConfiguration);
            cm.createCache(isa.domain.Gost.class.getName() + ".poslaoZahtevs", jcacheConfiguration);
            cm.createCache(isa.domain.ZahtevZaPrijateljstvo.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.MenadzerRestorana.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.MenadzerRestorana.class.getName() + ".konfiguracijas", jcacheConfiguration);
            cm.createCache(isa.domain.Zaposleni.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Zaposleni.class.getName() + ".rasporedSmenaZaSankeres", jcacheConfiguration);
            cm.createCache(isa.domain.Zaposleni.class.getName() + ".rasporedSmenaZaKonobares", jcacheConfiguration);
            cm.createCache(isa.domain.Zaposleni.class.getName() + ".rasporedSmenaZaKuvares", jcacheConfiguration);
            cm.createCache(isa.domain.Zaposleni.class.getName() + ".racunis", jcacheConfiguration);
            cm.createCache(isa.domain.RasporedSmenaZaSankere.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.RasporedSmenaZaKuvare.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.RasporedSmenaZaKonobare.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Ocena.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".ocenas", jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".pozivZaPrikupljanjes", jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".rezervacijas", jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".menadzeriRestoranas", jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".zaposleniRestoranas", jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".rasporedSmenaZaSankeres", jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".rasporedSmenaZaKonobares", jcacheConfiguration);
            cm.createCache(isa.domain.Restoran.class.getName() + ".rasporedSmenaZaKuvares", jcacheConfiguration);
            cm.createCache(isa.domain.Jelovnik.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Jelovnik.class.getName() + ".jelos", jcacheConfiguration);
            cm.createCache(isa.domain.Jelo.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Jelo.class.getName() + ".jelos", jcacheConfiguration);
            cm.createCache(isa.domain.KartaPica.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.KartaPica.class.getName() + ".pices", jcacheConfiguration);
            cm.createCache(isa.domain.Racun.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Pice.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Pice.class.getName() + ".pices", jcacheConfiguration);
            cm.createCache(isa.domain.KonfiguracijaStolova.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.KonfiguracijaStolova.class.getName() + ".stols", jcacheConfiguration);
            cm.createCache(isa.domain.Stol.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Stol.class.getName() + ".zaStoloves", jcacheConfiguration);
            cm.createCache(isa.domain.Stol.class.getName() + ".stols", jcacheConfiguration);
            cm.createCache(isa.domain.PozivZaPrikupljanjeN.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.PozivZaPrikupljanjeN.class.getName() + ".porudzbinaZaNabavkus", jcacheConfiguration);
            cm.createCache(isa.domain.PorudzbinaZANabavku.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Porudzbina.class.getName(), jcacheConfiguration);
            cm.createCache(isa.domain.Rezervacija.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
