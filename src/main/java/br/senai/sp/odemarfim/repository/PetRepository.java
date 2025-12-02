package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByTutorId(Long id);

    List<Pet> findByNomeContainingIgnoreCase(String nome);
}
