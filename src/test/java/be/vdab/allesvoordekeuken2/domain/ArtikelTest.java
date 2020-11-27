package be.vdab.allesvoordekeuken2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class ArtikelTest {
    private final static BigDecimal WEDDE = BigDecimal.valueOf(200);
    private Artikel artikel;

    @BeforeEach
    void beforeEach() {
        artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.TEN);
    }

    @Test
    void verhoogVerkoopPrijs() {
        artikel.verhoogVerkoopPrijs(BigDecimal.ONE);
        assertThat(artikel.getVerkoopprijs()).isEqualByComparingTo("11");
    }

    @Test
    void verhoogVerkoopPrijsMetNullMislukt() {
        assertThatNullPointerException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(null));
    }

    @Test
    void verhoogVerkoopPrijsMetMet0Mislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(BigDecimal.ZERO));
    }

    @Test
    void verhoogVerkoopPrijsMetMetNegatieveWaardeMislukt() {
        assertThatIllegalArgumentException().isThrownBy(() -> artikel.verhoogVerkoopPrijs(BigDecimal.valueOf(-1)));
    }
}

