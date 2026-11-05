package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.services.VetSpecialtyService;
import com.tecsup.petclinic.util.TObjectCreator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
//mockmvc simula un cliente http dentro de test, y simula peticiones g,p,p,d
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




//objetos falsos para simular el comportamiento de las dependencias de una clase 	
//crear y configurar un mock
@AutoConfigureMockMvc
@SpringBootTest

public class VetSpecialtyControllerMockitoTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private VetSpecialtyService vetSpecialtyService;

	@Test
	public void testFindSpecialtiesByVet() throws Exception {
		List<Specialty> expected = TObjectCreator.getSpecialtiesForVet3();

		//si te piden las especialidades del vet 3, devuelve esto
		Mockito.when(vetSpecialtyService.findSpecialtiesByVet(3)).thenReturn(expected);

		mockMvc.perform(get("/vets/3/specialties")) //hace la peticion
				.andDo(print())
				.andExpect(status().isOk()) //revisa el codigo htpp (200)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))) //revisa el json devuelto
				.andExpect(jsonPath("$[0].name", is(expected.get(0).getName())))
				.andExpect(jsonPath("$[1].name", is(expected.get(1).getName())));
		//  Confirmar que el controller llamó al servicio con el valor 3
		Mockito.verify(vetSpecialtyService).findSpecialtiesByVet(3);
	}

	@Test
	public void testFindVetsBySpecialty() throws Exception {
		List<Vet> expected = TObjectCreator.getVetsForSpecialty1();

		Mockito.when(vetSpecialtyService.findVetsBySpecialty(1)).thenReturn(expected);

		mockMvc.perform(get("/specialties/1/vets"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(2)))
				.andExpect(jsonPath("$[1].id", is(5)));

		Mockito.verify(vetSpecialtyService).findVetsBySpecialty(1);
	}

	@Test
	public void testFindSpecialtiesByVet_Empty() throws Exception {
		Mockito.when(vetSpecialtyService.findSpecialtiesByVet(1)).thenReturn(List.of());

		mockMvc.perform(get("/vets/1/specialties"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
}






