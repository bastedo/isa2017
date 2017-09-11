package isa.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String GOST = "ROLE_GOST";

    public static final String ZAPOSLENI = "ROLE_ZAPOSLENI";

    public static final String MENADZER_RESTORANA = "ROLE_MENADZER_RESTORANA";

    public static final String MENADZER_SISTEMA = "ROLE_MENADZER_SISTEMA";

    private AuthoritiesConstants() {
    }
}
