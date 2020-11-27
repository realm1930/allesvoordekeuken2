package be.vdab.allesvoordekeuken2.services;

import java.math.BigDecimal;

public interface ArtikelService {
    void verhoogVerkoopPrijs(long id, BigDecimal waarde);
}
