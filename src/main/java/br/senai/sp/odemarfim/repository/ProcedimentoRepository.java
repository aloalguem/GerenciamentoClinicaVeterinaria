package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.Procedimento;
import br.senai.sp.odemarfim.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {

    // FIX: Changed from findByNomeProcedimentoContainingIgnoreCase to findByNomeContainingIgnoreCase
    List<Procedimento> findByNomeContainingIgnoreCase(String nome);

    List<Procedimento> findByVeterinario(Veterinario veterinario);
}