package me.minikuma.business.entity.repository;

import me.minikuma.business.entity.Variety;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VarietyRepository {

    private final Map<Integer, Variety> varietiesById;

    public VarietyRepository() {
        this.varietiesById = new LinkedHashMap<>();

        final Variety var1 = Variety.builder()
                .id(1)
                .name("Thymus vulgaris")
                .build();
        varietiesById.put(var1.getId(), var1);

        final Variety var2 = Variety.builder()
                .id(2)
                .name("Thymus x citriodorus")
                .build();
        varietiesById.put(var2.getId(), var2);

        final Variety var3 = Variety.builder()
                .id(3)
                .name("Thymus herba-barona")
                .build();
        varietiesById.put(var3.getId(), var3);

        final Variety var4 = Variety.builder()
                .id(4)
                .name("Thymus pseudolaginosus")
                .build();
        varietiesById.put(var4.getId(), var4);

        final Variety var5 = Variety.builder()
                .id(4)
                .name("Thymus serpyllum")
                .build();
        varietiesById.put(var5.getId(), var5);
    }

    public List<Variety> findAll() {
        return new ArrayList<>(this.varietiesById.values());
    }

    public Variety findById(final Integer id) {
        return this.varietiesById.get(id);
    }
}
