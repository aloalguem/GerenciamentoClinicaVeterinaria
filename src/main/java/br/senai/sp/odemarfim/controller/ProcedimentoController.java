package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.model.Procedimento;
import br.senai.sp.odemarfim.model.Veterinario;
import br.senai.sp.odemarfim.repository.ProcedimentoRepository;
import br.senai.sp.odemarfim.repository.ProcedimentoRepository;
import br.senai.sp.odemarfim.repository.VeterinarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/procedimento")
public class ProcedimentoController {
    
    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    /*Método para adicionar novo procedimento*/

    @GetMapping
    public String listagem(Model model){
        List<Procedimento> listaProcedimentos = procedimentoRepository.findAll();

        model.addAttribute("procedimentos", listaProcedimentos);

        return "procedimento/listagem";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nomeProcedimento") String nomeProcedimento) {
        if (nomeProcedimento == null) {
            return "redirect:/procedimento";
        }
        List<Procedimento> listaProcedimentos = procedimentoRepository.findByNomeContainingIgnoreCase(nomeProcedimento);
        model.addAttribute("procedimentos", listaProcedimentos);
        return "procedimento/listagem";
    }

    @GetMapping("/novo")
    public String cadastrar(Model model){

        //Adiciona um objeto procedimento vazio para ser carregado no formulário
        model.addAttribute("procedimento", new Procedimento());

        model.addAttribute("veterinarios", veterinarioRepository.findAll());

        // Retorna o template procedimento/inserir.html
        return "procedimento/inserir";
    }

    @PostMapping("/salvar")
    public String salvarProcedimento(@Valid Procedimento procedimento, BindingResult result,
                               RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template procedimento/inserir.html
        if (result.hasErrors()) {
            if (procedimento.getId() != null) {
                return "procedimento/alterar";
            }
            return "procedimento/listagem";
        }

        // Salva o procedimento no banco de dados
        procedimentoRepository.save(procedimento);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Procedimento salva com sucesso!");

        // Redireciona para a página de listagem de procedimento
        return "redirect:/procedimento";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o procedimento no banco de dados
        Procedimento procedimento = procedimentoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o procedimento no objeto model para ser carregado no formulário
        model.addAttribute("procedimento", procedimento);
        model.addAttribute("veterinarios", veterinarioRepository.findAll());

        // Retorna o template procedimento/alterar.html
        return "procedimento/alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o procedimento no banco de dados
        Procedimento procedimento = procedimentoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o procedimento do banco de dados
        procedimentoRepository.delete(procedimento);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Procedimento excluída com sucesso!");

        // Redireciona para a página de listagem de procedimento
        return "redirect:/procedimento";
    }
}
