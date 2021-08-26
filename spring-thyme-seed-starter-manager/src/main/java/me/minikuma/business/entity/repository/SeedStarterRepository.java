package me.minikuma.business.entity.repository;

import me.minikuma.business.entity.SeedStarter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SeedStarterRepository {
    private final List<SeedStarter> seedStarters = new ArrayList<>();

    public List<SeedStarter> findAll() {
        return new ArrayList<>(this.seedStarters);
    }

    public void add(final SeedStarter seedStarter) {
        this.seedStarters.add(seedStarter);
    }
}
