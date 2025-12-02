package br.senai.sp.odemarfim.repository;

import br.senai.sp.odemarfim.model.CarrinhoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<CarrinhoItem, Long> {

    List<CarrinhoItem> findByUserIdOrderByIdAsc(Long userId);

    Optional<CarrinhoItem> findByUserIdAndEstoqueId(Long userId, Long estoqueId);

    Optional<CarrinhoItem> findByIdAndUserId(Long itemId, Long userId);
}

