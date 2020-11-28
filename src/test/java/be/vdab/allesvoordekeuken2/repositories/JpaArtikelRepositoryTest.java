package be.vdab.allesvoordekeuken2.repositories;

import be.vdab.allesvoordekeuken2.domain.FoodArtikel;
import be.vdab.allesvoordekeuken2.domain.Korting;
import be.vdab.allesvoordekeuken2.domain.NonFoodArtikel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaArtikelRepository.class)
@Sql("/insertArtikel.sql")
public class JpaArtikelRepositoryTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    private static final String ARTIKELS = "artikels";
    private final EntityManager manager;
    private final JpaArtikelRepository repository;

    public JpaArtikelRepositoryTest(EntityManager manager, JpaArtikelRepository repository) {
        this.manager = manager;
        this.repository = repository;
    }

    private long idVanTestFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testfood'", Long.class);
    }

    private long idVanTestNonFoodArtikel() {
        return super.jdbcTemplate.queryForObject(
                "select id from artikels where naam='testnonfood'", Long.class);
    }

    @Test
    void findFoodArtikelById() {
        var artikel = repository.findById(idVanTestFoodArtikel()).get();
        assertThat(artikel).isInstanceOf(FoodArtikel.class);
        assertThat(artikel.getNaam()).isEqualTo("testfood");
    }

    @Test
    void findNonFoodArtikelById() {
        var artikel = repository.findById(idVanTestNonFoodArtikel()).get();
        assertThat(artikel).isInstanceOf(NonFoodArtikel.class);
        assertThat(artikel.getNaam()).isEqualTo("testnonfood");
    }

    @Test
    void findOnbestaandeId() {
        assertThat(repository.findById(-1)).isNotPresent();
    }

    @Test
    void createFoodArtikel() {
        var artikel = new FoodArtikel("testfood2", BigDecimal.ONE, BigDecimal.TEN, 7);
        repository.create(artikel);
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + artikel.getId())).isOne();
    }

    @Test
    void createNonFoodArtikel() {
        var artikel =
                new NonFoodArtikel("testnonfood2", BigDecimal.ONE, BigDecimal.TEN, 30);
        repository.create(artikel);
        assertThat(super.countRowsInTableWhere(ARTIKELS,
                "id=" + artikel.getId())).isOne();
    }

    @Test
    void findBijNaamContains() {
        assertThat(repository.findByNaamContains("es")).hasSize(super.jdbcTemplate.queryForObject(
                "select count(*) from artikels where naam like '%es%'", Integer.class))
                .extracting(artikel -> artikel.getNaam().toLowerCase())
                .allSatisfy(naam -> assertThat(naam).contains("es"))
                .isSorted();
    }

    @Test
    void verhoogAlleVerkoopPrijzen() {
        assertThat(repository.verhoogAlleVerkoopPrijzen(BigDecimal.TEN)).isEqualTo(super.countRowsInTable("artikels"));
        assertThat(super.jdbcTemplate.queryForObject("" +
                "select verkoopprijs from artikels where id=?", BigDecimal.class, idVanTestFoodArtikel()))
                .isEqualByComparingTo("132");
    }
    @Test
    void kortingenLezen() {
        assertThat(repository.findById(idVanTestFoodArtikel()).get().getKortingen())
                .containsOnly(new Korting(1, BigDecimal.TEN));
    }


}
