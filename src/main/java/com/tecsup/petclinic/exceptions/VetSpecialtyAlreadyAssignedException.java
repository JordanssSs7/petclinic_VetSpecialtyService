package com.tecsup.petclinic.exceptions;

/**
 * Se lanza cuando se intenta asignar una especialidad que el veterinario ya tiene.
 */
public class VetSpecialtyAlreadyAssignedException extends Exception {

	private static final long serialVersionUID = 1L;

	public VetSpecialtyAlreadyAssignedException(String message) {
		super(message);
	}

}
