package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyAssignedException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

public interface VetSpecialtyService {

	/**
	 * Asigna una especialidad a un veterinario.
	 * @throws VetSpecialtyAlreadyAssignedException si ya la tiene asignada
	 */
	VetSpecialty assignSpecialtyToVet(int vetId, int specialtyId)
			throws VetSpecialtyAlreadyAssignedException;

	/**
	 * Quita una especialidad a un veterinario.
	 * @throws VetSpecialtyNotFoundException si no la tiene asignada
	 */
	void removeSpecialtyFromVet(int vetId, int specialtyId)
			throws VetSpecialtyNotFoundException;

}
