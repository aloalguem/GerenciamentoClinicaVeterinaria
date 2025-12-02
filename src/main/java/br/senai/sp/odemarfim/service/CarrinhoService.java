package br.senai.sp.odemarfim.service;

import br.senai.sp.odemarfim.model.CarrinhoItem;
import br.senai.sp.odemarfim.model.Estoque;
import br.senai.sp.odemarfim.model.User;
import br.senai.sp.odemarfim.repository.CarrinhoRepository;
import br.senai.sp.odemarfim.repository.EstoqueRepository;
import br.senai.sp.odemarfim.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final EstoqueRepository estoqueRepository;
    private final UserRepository userRepository;

    public CarrinhoService(CarrinhoRepository carrinhoRepository,
                           EstoqueRepository estoqueRepository,
                           UserRepository userRepository) {
        this.carrinhoRepository = carrinhoRepository;
        this.estoqueRepository = estoqueRepository;
        this.userRepository = userRepository;
    }

    public List<CarrinhoItem> buscarItensDoUsuario(Long userId) {
        validarUsuario(userId);
        return carrinhoRepository.findByUserIdOrderByIdAsc(userId);
    }

    public void adicionarItem(Long userId, Long estoqueId, Integer quantidade) {
        validarUsuario(userId);
        int quantidadeNormalizada = quantidade == null || quantidade < 1 ? 1 : quantidade;

        Estoque estoque = estoqueRepository.findById(estoqueId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        User user = carregarUsuario(userId);

        CarrinhoItem item = carrinhoRepository.findByUserIdAndEstoqueId(userId, estoqueId)
                .orElseGet(() -> {
                    CarrinhoItem novoItem = new CarrinhoItem();
                    novoItem.setUser(user);
                    novoItem.setEstoque(estoque);
                    novoItem.setQuantidade(0);
                    return novoItem;
                });

        item.setQuantidade(item.getQuantidade() + quantidadeNormalizada);
        carrinhoRepository.save(item);
    }

    public void atualizarQuantidade(Long userId, Long itemId, Integer quantidade) {
        validarUsuario(userId);
        if (quantidade == null || quantidade < 1) {
            throw new IllegalArgumentException("Quantidade inválida");
        }
        CarrinhoItem item = carrinhoRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));
        item.setQuantidade(quantidade);
        carrinhoRepository.save(item);
    }

    public void removerItem(Long userId, Long itemId) {
        validarUsuario(userId);
        CarrinhoItem item = carrinhoRepository.findByIdAndUserId(itemId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));
        carrinhoRepository.delete(item);
    }

    public void limparCarrinho(Long userId) {
        validarUsuario(userId);
        buscarItensDoUsuario(userId).forEach(carrinhoRepository::delete);
    }

    public BigDecimal calcularTotal(List<CarrinhoItem> itens) {
        return itens.stream()
                .map(item -> BigDecimal.valueOf(item.getEstoque().getPrecoVenda())
                        .multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public int calcularQuantidadeTotal(List<CarrinhoItem> itens) {
        return itens.stream()
                .mapToInt(CarrinhoItem::getQuantidade)
                .sum();
    }

    private void validarUsuario(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("Usuário não autenticado");
        }
    }

    private User carregarUsuario(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
}

