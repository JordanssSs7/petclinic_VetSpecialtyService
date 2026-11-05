package com.tecsup.petclinic.webs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Grupo 5 - Integrante B (Consultas): integracion real, sin mocks.
 * La peticion atraviesa controller -> servicio -> repositorio -> H2 (data.sql).
 */

@AutoConfigureMockMvc
@SpringBootTest

public class VetSpecialtyControllerTest {
    // Spring entrega el MockMvc ya configurado para hacer peticiones simuladas.
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testFindSpecialtiesByVet() throws Exception {
		// vet 3 tiene surgery (2) y dentistry (3)
		this.mockMvc.perform(get("/vets/3/specialties"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].name", containsInAnyOrder("surgery", "dentistry")));
	}

	@Test
	public void testFindVetsBySpecialty() throws Exception {
		// especialidad 1 (radiology) la tienen los vets 2 y 5
		this.mockMvc.perform(get("/specialties/1/vets"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[*].id", containsInAnyOrder(2, 5)));
	}

	@Test
	public void testFindSpecialtiesByVet_Empty() throws Exception {
		// vet 1 no tiene asignaciones
		this.mockMvc.perform(get("/vets/1/specialties"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
}
