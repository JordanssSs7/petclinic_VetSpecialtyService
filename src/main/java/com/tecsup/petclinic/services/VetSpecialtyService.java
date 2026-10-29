package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;

import java.util.List;

public interface VetSpecialtyService {

    List<Specialty> findSpecialtiesByVet(Integer vetId);

    List<Vet> findVetsBySpecialty(Integer specialtyId);
}
