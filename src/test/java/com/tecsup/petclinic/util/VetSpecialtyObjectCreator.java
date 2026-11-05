package com.tecsup.petclinic.util;

import com.tecsup.petclinic.entities.VetSpecialty;

public class VetSpecialtyObjectCreator {

    public static VetSpecialty assignment(
            int vetId, int specialtyId, boolean primary) {

        VetSpecialty assignment = new VetSpecialty();
        assignment.setVetId(vetId);
        assignment.setSpecialtyId(specialtyId);
        assignment.setPrimary(primary);

        return assignment;
    }
}