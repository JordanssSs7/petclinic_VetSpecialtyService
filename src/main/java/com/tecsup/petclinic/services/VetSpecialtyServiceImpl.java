package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetRepository vetRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Specialty> findSpecialtiesByVet(Integer vetId) {
        log.info("Finding specialties for vet: {}", vetId);
        return vetRepository.findByIdWithSpecialties(vetId)
                .map(vet -> new ArrayList<>(vet.getSpecialties()))
                .orElseGet(ArrayList::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vet> findVetsBySpecialty(Integer specialtyId) {
        log.info("Finding vets for specialty: {}", specialtyId);
        return vetRepository.findBySpecialtyId(specialtyId);
    }
}
