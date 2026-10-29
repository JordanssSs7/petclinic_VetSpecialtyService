package com.tecsup.petclinic.exceptions;

/**
 * Se lanza cuando el veterinario no tiene la especialidad indicada.
 */
public class VetSpecialtyNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VetSpecialtyNotFoundException(String message) {
		super(message);
	}

}
