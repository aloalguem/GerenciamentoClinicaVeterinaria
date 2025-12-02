package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.controller.EstoqueController;
import br.senai.sp.odemarfim.model.Admin;
import br.senai.sp.odemarfim.model.Estoque;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    List<Estoque> findAll();

    @Query("SELECT e FROM Estoque e WHERE e.precoVenda BETWEEN :min AND :max")
    List<Estoque> findByPrecoRange(double min, double max);

    // Combinação com tipo + preço
    @Query("SELECT e FROM Estoque e WHERE (:tipo IS NULL OR LOWER(e.tipo) = LOWER(:tipo)) " +
            "AND (:min IS NULL OR e.precoVenda >= :min) " +
            "AND (:max IS NULL OR e.precoVenda <= :max)")
    List<Estoque> findByFiltros(String tipo, Double min, Double max);

    List<Estoque> findByTipo(String tipo);

    List<Estoque> findByTipoIgnoreCase(String tipo);

    List<Estoque> findByNomeContainingIgnoreCase(String nome);

}
