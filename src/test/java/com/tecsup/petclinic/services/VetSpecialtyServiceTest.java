package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyAssignedException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Grupo 5 - Integrante A: asignación de especialidades.
 * Datos de data.sql: el vet 1 no tiene especialidades, el vet 2 tiene la 1.
 */
@Slf4j
@SpringBootTest
@Transactional
public class VetSpecialtyServiceTest {

	@Autowired
	private VetSpecialtyService vetSpecialtyService;

	// se usa para comprobar directamente en la BD el resultado de cada prueba
	@Autowired
	private VetSpecialtyRepository vetSpecialtyRepository;

	// asigna una especialidad nueva y verifica que quedó guardada
	@Test
	public void testAssignSpecialtyToVet() throws Exception {

		int VET_ID = 1;
		int SPECIALTY_ID = 2;

		VetSpecialty assigned = this.vetSpecialtyService.assignSpecialtyToVet(VET_ID, SPECIALTY_ID);

		log.info("{}", assigned);

		assertEquals(VET_ID, assigned.getVetId());
		assertEquals(SPECIALTY_ID, assigned.getSpecialtyId());
		assertTrue(this.vetSpecialtyRepository.existsById(new VetSpecialtyId(VET_ID, SPECIALTY_ID)));
	}

	// quita una especialidad existente y verifica que ya no está
	@Test
	public void testRemoveSpecialtyFromVet() throws Exception {

		int VET_ID = 2;
		int SPECIALTY_ID = 1;

		assertTrue(this.vetSpecialtyRepository.existsById(new VetSpecialtyId(VET_ID, SPECIALTY_ID)));

		this.vetSpecialtyService.removeSpecialtyFromVet(VET_ID, SPECIALTY_ID);

		assertFalse(this.vetSpecialtyRepository.existsById(new VetSpecialtyId(VET_ID, SPECIALTY_ID)));
	}

	// quitar una especialidad que no tiene debe lanzar excepción
	@Test
	public void testRemoveSpecialtyFromVet_NotAssigned() {

		assertThrows(VetSpecialtyNotFoundException.class,
				() -> this.vetSpecialtyService.removeSpecialtyFromVet(1, 3));
	}

	// asignar dos veces la misma especialidad debe fallar
	@Test
	public void testAssignDuplicate_ShouldFail() {

		// el vet 2 ya tiene la especialidad 1
		assertThrows(VetSpecialtyAlreadyAssignedException.class,
				() -> this.vetSpecialtyService.assignSpecialtyToVet(2, 1));
	}

}
