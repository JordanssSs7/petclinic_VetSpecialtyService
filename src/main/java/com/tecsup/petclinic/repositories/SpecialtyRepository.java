package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {

    @Query("SELECT s FROM Specialty s JOIN FETCH s.vets WHERE s.id = :id")
    Optional<Specialty> findByIdWithVets(@Param("id") Integer id);

    @Query("SELECT s FROM Specialty s WHERE s.name = :name")
    Optional<Specialty> findByName(@Param("name") String name);

    @Query("SELECT DISTINCT s FROM Specialty s LEFT JOIN FETCH s.vets")
    List<Specialty> findAllWithVets();
}