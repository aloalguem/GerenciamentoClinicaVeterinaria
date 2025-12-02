package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.model.CarrinhoItem;
import br.senai.sp.odemarfim.model.Estoque;
import br.senai.sp.odemarfim.repository.EstoqueRepository;
import br.senai.sp.odemarfim.service.CarrinhoService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final EstoqueRepository estoqueRepository;
    private final CarrinhoService carrinhoService;

    public ProdutoController(EstoqueRepository estoqueRepository,
                             CarrinhoService carrinhoService) {
        this.estoqueRepository = estoqueRepository;
        this.carrinhoService = carrinhoService;
    }

    @GetMapping("/visualizar")
    public String visualizar(
            @RequestParam(value = "tipo", required = false) String categoria,
            @RequestParam(value = "preco", required = false) String preco,
            Model model) {


        Double min = null;
        Double max = null;

        if (preco != null && !preco.isEmpty()) {
            String[] faixa = preco.split("-");
            min = Double.parseDouble(faixa[0]);
            max = Double.parseDouble(faixa[1]);
        }

        List<Estoque> estoques = estoqueRepository.findByFiltros(
                (categoria == null || categoria.isEmpty()) ? null : categoria,
                min,
                max
        );

        model.addAttribute("estoques", estoques);
        return "produtos/visualizar";
    }

    @PostMapping("/carrinho/adicionar/{estoqueId}")
    public String adicionarAoCarrinho(@PathVariable Long estoqueId,
                                      @RequestParam(defaultValue = "1") Integer quantidade,
                                      @AuthenticationPrincipal(expression = "id") Long usuarioId,
                                      RedirectAttributes redirectAttributes) {

        carrinhoService.adicionarItem(usuarioId, estoqueId, quantidade);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto adicionado ao carrinho!");
        return "redirect:/produtos/visualizar";
    }

    @PostMapping("/carrinho/atualizar/{itemId}")
    public String atualizarQuantidade(@PathVariable Long itemId,
                                      @RequestParam Integer quantidade,
                                      @AuthenticationPrincipal(expression = "id") Long usuarioId,
                                      RedirectAttributes redirectAttributes) {
        try {
            carrinhoService.atualizarQuantidade(usuarioId, itemId, quantidade);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Quantidade atualizada com sucesso!");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("mensagemErro", ex.getMessage());
        }
        return "redirect:/produtos/carrinho";
    }

    @PostMapping("/carrinho/remover/{itemId}")
    public String removerItem(@PathVariable Long itemId,
                              @AuthenticationPrincipal(expression = "id") Long usuarioId,
                              RedirectAttributes redirectAttributes) {
        carrinhoService.removerItem(usuarioId, itemId);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Item removido do carrinho.");
        return "redirect:/produtos/carrinho";
    }

    @GetMapping("/carrinho")
    public String carrinho(Model model,
                           @AuthenticationPrincipal(expression = "id") Long usuarioId) {
        List<CarrinhoItem> itens = carrinhoService.buscarItensDoUsuario(usuarioId);
        model.addAttribute("itens", itens);
        model.addAttribute("total", carrinhoService.calcularTotal(itens));
        model.addAttribute("quantidadeItens", carrinhoService.calcularQuantidadeTotal(itens));
        return "produtos/carrinho";
    }

    @GetMapping("/finalizarCompra")
    public String finalizarCompra(Model model,
                                  @AuthenticationPrincipal(expression = "id") Long usuarioId,
                                  RedirectAttributes redirectAttributes) {

        List<CarrinhoItem> itens = carrinhoService.buscarItensDoUsuario(usuarioId);

        if (itens.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Adicione itens ao carrinho antes de finalizar a compra.");
            return "redirect:/produtos/visualizar";
        }

        model.addAttribute("itens", itens);
        model.addAttribute("total", carrinhoService.calcularTotal(itens));
        model.addAttribute("quantidadeItens", carrinhoService.calcularQuantidadeTotal(itens));
        return "produtos/finalizarCompra";
    }
}
