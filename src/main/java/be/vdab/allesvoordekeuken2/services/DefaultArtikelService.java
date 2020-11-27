package be.vdab.allesvoordekeuken2.services;

import be.vdab.allesvoordekeuken2.exceptions.ArtikelNietGevondenException;
import be.vdab.allesvoordekeuken2.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
class DefaultArtikelService implements ArtikelService {
    private final ArtikelRepository artikelRepository;

    DefaultArtikelService(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @Override
    public void verhoogVerkoopPrijs(long id, BigDecimal waarde) {
        artikelRepository.findById(id)
                .orElseThrow(() -> new ArtikelNietGevondenException())
                .verhoogVerkoopPrijs(waarde);
    }
}
