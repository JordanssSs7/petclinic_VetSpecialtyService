package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
@Transactional
public class VetSpecialtyPrimaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VetSpecialtyRepository repository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testSetPrimarySpecialty() throws Exception {
        VetSpecialtyId id = new VetSpecialtyId(5, 1);

        assertFalse(repository.findById(id).orElseThrow().isPrimary());

        mockMvc.perform(put("/vet-specialties/5/1/primary"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.vetId").value(5))
                .andExpect(jsonPath("$.specialtyId").value(1))
                .andExpect(jsonPath("$.primary").value(true));

        repository.flush();
        entityManager.clear();

        assertTrue(repository.findById(id).orElseThrow().isPrimary());
    }

    @Test
    public void testSetPrimarySpecialty_OnlyOnePrimary() throws Exception {
        mockMvc.perform(put("/vet-specialties/3/3/primary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialtyId").value(3))
                .andExpect(jsonPath("$.primary").value(true));

        repository.flush();
        entityManager.clear();

        long totalPrimary = repository.findByVetId(3).stream()
                .filter(VetSpecialty::isPrimary)
                .count();

        assertEquals(1L, totalPrimary);

        assertFalse(repository.findById(
                new VetSpecialtyId(3, 2)).orElseThrow().isPrimary());

        assertTrue(repository.findById(
                new VetSpecialtyId(3, 3)).orElseThrow().isPrimary());
    }

    @Test
    public void testSetPrimarySpecialty_NotAssigned() throws Exception {
        mockMvc.perform(put("/vet-specialties/3/1/primary"))
                .andExpect(status().isNotFound());

        repository.flush();
        entityManager.clear();

        assertFalse(repository.existsById(new VetSpecialtyId(3, 1)));

        assertTrue(repository.findById(
                new VetSpecialtyId(3, 2)).orElseThrow().isPrimary());
    }
}