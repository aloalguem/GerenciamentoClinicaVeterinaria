package br.senai.sp.odemarfim.controller;


import br.senai.sp.odemarfim.model.Procedimento;
import br.senai.sp.odemarfim.model.Veterinario;
import br.senai.sp.odemarfim.repository.ProcedimentoRepository;
import br.senai.sp.odemarfim.repository.RoleRepository;
import br.senai.sp.odemarfim.repository.TutorRepository;
import br.senai.sp.odemarfim.repository.VeterinarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
@RequestMapping("/veterinario")
public class VeterinarioController {
    
    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;
    
    @GetMapping
    public String listagem(Model model) {
        List<Veterinario> listaVeterinarios = veterinarioRepository.findAll();
        
        model.addAttribute("veterinarios", listaVeterinarios);
        
        return "veterinario/listagem";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("email") String email) {
        if (email == null) {
            return "redirect:/veterinario";
        }
        List<Veterinario> listaVeterinarios = veterinarioRepository.findByEmailContaining(email);
        model.addAttribute("veterinario",listaVeterinarios);
        return "veterinario/listagem";
    }

    @GetMapping("/novo")
    public String cadastrar(Model model){

        //Adiciona um objeto veterinario vazio para ser carregado no formulário
        model.addAttribute("veterinario", new Veterinario());

        // Retorna o template veterinario/inserir.html
        return "veterinario/inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Veterinario veterinario, BindingResult result,
                         RedirectAttributes attributes ) {

        // Se houver erro de validação, retorna para o template veterinario/inserir.html
        if (result.hasErrors()) {
            if (veterinario.getId() != null) {
                return "veterinario/alterar";
            }
            return "veterinario/listagem";
        }

        if(!veterinario.getSenha().isEmpty()){
            veterinario.getUser().setEmail(veterinario.getEmail());
            veterinario.getUser().setSenha(bCryptPasswordEncoder.encode(veterinario.getSenha()));
            veterinario.addRole(roleRepository);

        }


        // Salva o veterinario no banco de dados
        veterinarioRepository.save(veterinario);



        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Veterinario salvo com sucesso!");

        // Redireciona para a página de listagem de veterinarios
        return "redirect:/veterinario";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o veterinario no banco de dados
        Veterinario veterinario = veterinarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o veterinario no objeto model para ser carregado no formulário
        model.addAttribute("veterinario", veterinario);

        // Retorna o template veterinario/alterar.html
        return "veterinario/alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o veterinario no banco de dados
        Veterinario veterinario = veterinarioRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        List<Procedimento> procedimentos = procedimentoRepository.findByVeterinario(veterinario);


        // Exclui o veterinario do banco de dados
        if(!procedimentos.isEmpty()){
            attributes.addFlashAttribute("mensagem",
                    "Veterinario não pode ser excluído, pois possui procedimentos vinculados!");
            return "redirect:/veterinario";
        }
        veterinarioRepository.delete(veterinario);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Veterinario excluído com sucesso!");

        // Redireciona para a página de listagem de usuários
        return "redirect:/veterinario";
    }
}
