package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.entities.VetSpecialtyId;
import com.tecsup.petclinic.exceptions.VetSpecialtyAlreadyAssignedException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

	private final VetSpecialtyRepository vetSpecialtyRepository;

	private final VetRepository vetRepository;

	@Override
	public VetSpecialty assignSpecialtyToVet(int vetId, int specialtyId)
			throws VetSpecialtyAlreadyAssignedException {

		VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);

		// no se permite asignar dos veces la misma especialidad
		if (vetSpecialtyRepository.existsById(id))
			throw new VetSpecialtyAlreadyAssignedException(
					"Vet " + vetId + " already has specialty " + specialtyId);

		VetSpecialty vetSpecialty = new VetSpecialty();
		vetSpecialty.setVetId(vetId);
		vetSpecialty.setSpecialtyId(specialtyId);

		return vetSpecialtyRepository.save(vetSpecialty);
	}

	@Override
	public void removeSpecialtyFromVet(int vetId, int specialtyId)
			throws VetSpecialtyNotFoundException {

		VetSpecialtyId id = new VetSpecialtyId(vetId, specialtyId);

		// si no existe la asignación no hay nada que quitar
		VetSpecialty vetSpecialty = vetSpecialtyRepository.findById(id)
				.orElseThrow(() -> new VetSpecialtyNotFoundException(
						"Vet " + vetId + " does not have specialty " + specialtyId));

		vetSpecialtyRepository.delete(vetSpecialty);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Specialty> findSpecialtiesByVet(Integer vetId) {
		log.info("Finding specialties for vet: {}", vetId);
		return vetRepository.findByIdWithSpecialties(vetId)
				.map(vet -> new ArrayList<>(vet.getSpecialties()))
				.orElseGet(ArrayList::new);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Vet> findVetsBySpecialty(Integer specialtyId) {
		log.info("Finding vets for specialty: {}", specialtyId);
		return vetRepository.findBySpecialtyId(specialtyId);
	}

}
