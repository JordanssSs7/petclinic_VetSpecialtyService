package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.services.VetSpecialtyService;
import com.tecsup.petclinic.util.VetSpecialtyObjectCreator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
public class VetSpecialtyPrimaryControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetSpecialtyService vetSpecialtyService;

    @Test
    public void testSetPrimarySpecialty() throws Exception {
        when(vetSpecialtyService.setPrimarySpecialty(5, 1))
                .thenReturn(
                        VetSpecialtyObjectCreator.assignment(5, 1, true));

        mockMvc.perform(put("/vet-specialties/5/1/primary"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.vetId").value(5))
                .andExpect(jsonPath("$.specialtyId").value(1))
                .andExpect(jsonPath("$.primary").value(true));

        verify(vetSpecialtyService).setPrimarySpecialty(5, 1);
    }

    @Test
    public void testSetPrimarySpecialty_OnlyOnePrimary() throws Exception {
        when(vetSpecialtyService.setPrimarySpecialty(3, 2))
                .thenReturn(
                        VetSpecialtyObjectCreator.assignment(3, 2, true));

        when(vetSpecialtyService.setPrimarySpecialty(3, 3))
                .thenReturn(
                        VetSpecialtyObjectCreator.assignment(3, 3, true));

        mockMvc.perform(put("/vet-specialties/3/2/primary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialtyId").value(2))
                .andExpect(jsonPath("$.primary").value(true));

        mockMvc.perform(put("/vet-specialties/3/3/primary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialtyId").value(3))
                .andExpect(jsonPath("$.primary").value(true));

        var order = inOrder(vetSpecialtyService);
        order.verify(vetSpecialtyService).setPrimarySpecialty(3, 2);
        order.verify(vetSpecialtyService).setPrimarySpecialty(3, 3);
    }

    @Test
    public void testSetPrimarySpecialty_NotAssigned() throws Exception {
        when(vetSpecialtyService.setPrimarySpecialty(3, 1))
                .thenThrow(new VetSpecialtyNotFoundException(
                        "La especialidad no está asignada"));

        mockMvc.perform(put("/vet-specialties/3/1/primary"))
                .andExpect(status().isNotFound());

        verify(vetSpecialtyService).setPrimarySpecialty(3, 1);
    }
}