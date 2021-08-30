package me.minikuma.business.entity.repository;

import me.minikuma.business.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SeedStarterRepositoryTest {

    @Autowired
    SeedStarterRepository seedStarterRepository;

    // add test repository - entity
    @Test
    @DisplayName("Seed Starter add 메서드 테스트")
    void seed_starter_findAll_after_add() {
        // given
        Feature[] features = {Feature.FERTILIZER, Feature.PH_CORRECTOR};
        List<Row> rows = new ArrayList<>();

        Variety variety = new Variety();
        variety.setId(100);
        variety.setName("test1");

        Row row = new Row();
        row.setVariety(variety);
        row.setSeedsPerCell(100);

        rows.add(row);

        SeedStarter seedStarter = SeedStarter.builder()
                .id(100)
                .covered(false)
                .datePlanted(Calendar.getInstance().getTime())
                .features(features)
                .type(Type.PLASTIC)
                .rows(rows)
                .build();

        // when
        seedStarterRepository.add(seedStarter);
        List<SeedStarter> findSeedStarter = seedStarterRepository.findAll();

        // then
        assertThat(findSeedStarter.size()).isEqualTo(1);
        assertThat(findSeedStarter.get(0).getId()).isEqualTo(seedStarter.getId());
        assertThat(findSeedStarter.get(0).getDatePlanted()).isEqualTo(seedStarter.getDatePlanted());
        assertThat(findSeedStarter.get(0).getType()).isEqualTo(seedStarter.getType());
        assertThat(findSeedStarter.get(0).getFeatures().length).isEqualTo(seedStarter.getFeatures().length);
        assertThat(findSeedStarter.get(0).getRows().get(0).getSeedsPerCell()).isEqualTo(seedStarter.getRows().get(0).getSeedsPerCell());

    }

    // findAll test repository - entity
}