package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyAssignedException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

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

	/**
	 * Lista las especialidades de un veterinario (vacía si no tiene ninguna).
	 */
	List<Specialty> findSpecialtiesByVet(Integer vetId);

	/**
	 * Lista los veterinarios que tienen una especialidad.
	 */
	List<Vet> findVetsBySpecialty(Integer specialtyId);

}
