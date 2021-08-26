package me.minikuma.business.service;

import lombok.RequiredArgsConstructor;
import me.minikuma.business.entity.SeedStarter;
import me.minikuma.business.entity.repository.SeedStarterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedStarterService {
    private final SeedStarterRepository seedStarterRepository;

    public List<SeedStarter> findAll() {
        return seedStarterRepository.findAll();
    }

    public void add(final SeedStarter seedStarter) {
        seedStarterRepository.add(seedStarter);
    }
}
