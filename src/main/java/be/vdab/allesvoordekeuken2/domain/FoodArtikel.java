package be.vdab.allesvoordekeuken2.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {

    private int houdbaarheid;

    public FoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int houdbaarheid) {
        super(naam, aankoopprijs, verkoopprijs);
        this.houdbaarheid = houdbaarheid;
    }

    public FoodArtikel() {}

    public int getHoudbaarheid() {
        return houdbaarheid;
    }
}
