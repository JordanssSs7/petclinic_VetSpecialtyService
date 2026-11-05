package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.services.VetSpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vet-specialties")
@RequiredArgsConstructor
public class VetSpecialtyPrimaryController {

    private final VetSpecialtyService vetSpecialtyService;

    @PutMapping("/{vetId}/{specId}/primary")
    public ResponseEntity<VetSpecialty> setPrimarySpecialty(
            @PathVariable("vetId") int vetId,
            @PathVariable("specId") int specId) {

        try {
            return ResponseEntity.ok(
                    vetSpecialtyService.setPrimarySpecialty(vetId, specId));
        } catch (VetSpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}