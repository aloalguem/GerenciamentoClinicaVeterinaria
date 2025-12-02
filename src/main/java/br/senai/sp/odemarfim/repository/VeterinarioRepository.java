package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.Tutor;
import br.senai.sp.odemarfim.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    List<Veterinario> findByEmailContaining(String email);
}
