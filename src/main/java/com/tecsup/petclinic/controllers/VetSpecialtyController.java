package com.tecsup.petclinic.controllers;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.services.VetSpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//clase atiende peticiones HTTP, lo q devuelve sus metodos se convierten a json
@RestController
@RequiredArgsConstructor
public class VetSpecialtyController {

    private final VetSpecialtyService vetSpecialtyService;

    // Integrante B - Consultas
    //atiende peticiones GET a esa ruta.

    @GetMapping("/vets/{vetId}/specialties")
    public List<Specialty> getSpecialtiesByVet(@PathVariable int vetId) {
        return vetSpecialtyService.findSpecialtiesByVet(vetId);
    }

    @GetMapping("/specialties/{specId}/vets")
    public List<Vet> getVetsBySpecialty(@PathVariable int specId) {
        return vetSpecialtyService.findVetsBySpecialty(specId);
    }
}

