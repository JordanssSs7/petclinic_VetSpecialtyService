package com.tecsup.petclinic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Relación entre un veterinario y una especialidad (tabla vet_specialties).
 */
@Entity
@Table(name = "vet_specialties")
@IdClass(VetSpecialtyId.class) // la clave es la pareja vetId + specialtyId
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VetSpecialty {

	@Id
	@Column(name = "vet_id")
	private int vetId;

	@Id
	@Column(name = "specialty_id")
	private int specialtyId;

	@Column(name = "certification_date")
	private LocalDate certificationDate;

	@Column(name = "years_experience")
	private Integer yearsExperience;

	// true si es la especialidad principal del veterinario (la usará el Integrante C)
	@Column(name = "is_primary")
	private boolean primary;

	private String notes;

}
