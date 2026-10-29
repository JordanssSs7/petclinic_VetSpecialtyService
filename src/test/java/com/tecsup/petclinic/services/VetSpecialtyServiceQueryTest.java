package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
public class VetSpecialtyServiceQueryTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    public void testFindSpecialtiesByVet() {

        final Integer VET_ID = 3;
        final Set<String> NAMES_EXPECTED = Set.of("surgery", "dentistry");

        List<Specialty> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_ID);

        log.info("" + specialties);

        assertEquals(NAMES_EXPECTED.size(), specialties.size());
        assertEquals(NAMES_EXPECTED,
                specialties.stream().map(Specialty::getName).collect(Collectors.toSet()));
    }

    @Test
    public void testFindVetsBySpecialty() {

        final Integer SPECIALTY_ID = 1;
        final Set<Integer> IDS_EXPECTED = Set.of(2, 5);

        List<Vet> vets = this.vetSpecialtyService.findVetsBySpecialty(SPECIALTY_ID);

        log.info("" + vets);

        assertEquals(IDS_EXPECTED.size(), vets.size());
        assertEquals(IDS_EXPECTED,
                vets.stream().map(Vet::getId).collect(Collectors.toSet()));
    }

    @Test
    public void testFindSpecialtiesByVet_Empty() {

        final Integer VET_WITHOUT_SPECIALTIES_ID = 1;

        List<Specialty> specialties = this.vetSpecialtyService.findSpecialtiesByVet(VET_WITHOUT_SPECIALTIES_ID);

        assertNotNull(specialties);
        assertTrue(specialties.isEmpty());
    }
}
