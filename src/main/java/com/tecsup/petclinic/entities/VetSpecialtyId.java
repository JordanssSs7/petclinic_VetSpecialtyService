package com.tecsup.petclinic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Clave primaria compuesta de vet_specialties (veterinario + especialidad).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VetSpecialtyId implements Serializable {

	private static final long serialVersionUID = 1L;

	private int vetId;
	private int specialtyId;

}
