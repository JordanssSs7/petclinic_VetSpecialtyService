package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class VetSpecialtyServicePrimaryTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Autowired
    private VetSpecialtyRepository vetSpecialtyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testSetPrimarySpecialty() throws Exception {
        VetSpecialtyId id = new VetSpecialtyId(5, 1);

        VetSpecialty before = vetSpecialtyRepository.findById(id)
                .orElseThrow();

        assertFalse(before.isPrimary());

        vetSpecialtyService.setPrimarySpecialty(5, 1);

        vetSpecialtyRepository.flush();
        entityManager.clear();

        VetSpecialty saved = vetSpecialtyRepository.findById(id)
                .orElseThrow();

        assertEquals(5, saved.getVetId());
        assertEquals(1, saved.getSpecialtyId());
        assertTrue(saved.isPrimary());
    }

    @Test
    public void testSetPrimarySpecialty_OnlyOnePrimary() throws Exception {
        vetSpecialtyService.setPrimarySpecialty(3, 3);

        vetSpecialtyRepository.flush();
        entityManager.clear();

        List<VetSpecialty> assignments =
                vetSpecialtyRepository.findByVetId(3);

        long primaryCount = assignments.stream()
                .filter(VetSpecialty::isPrimary)
                .count();

        assertEquals(1L, primaryCount);

        VetSpecialty previous = vetSpecialtyRepository.findById(
                new VetSpecialtyId(3, 2)).orElseThrow();

        VetSpecialty current = vetSpecialtyRepository.findById(
                new VetSpecialtyId(3, 3)).orElseThrow();

        assertFalse(previous.isPrimary());
        assertTrue(current.isPrimary());
    }

    @Test
    public void testSetPrimarySpecialty_NotAssigned() {
        assertThrows(VetSpecialtyNotFoundException.class,
                () -> vetSpecialtyService.setPrimarySpecialty(3, 1));

        vetSpecialtyRepository.flush();
        entityManager.clear();

        VetSpecialty original = vetSpecialtyRepository.findById(
                new VetSpecialtyId(3, 2)).orElseThrow();

        assertTrue(original.isPrimary());

        assertFalse(vetSpecialtyRepository.existsById(
                new VetSpecialtyId(3, 1)));
    }
}