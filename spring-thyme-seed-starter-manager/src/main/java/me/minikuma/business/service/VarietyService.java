package me.minikuma.business.service;

import lombok.RequiredArgsConstructor;
import me.minikuma.business.entity.Variety;
import me.minikuma.business.entity.repository.VarietyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VarietyService {
    private final VarietyRepository varietyRepository;

    public List<Variety> findAll() {
        return varietyRepository.findAll();
    }

    public Variety findById(final Integer id) {
        return varietyRepository.findById(id);
    }
}
