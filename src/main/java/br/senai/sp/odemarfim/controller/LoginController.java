package br.senai.sp.odemarfim.controller;


import br.senai.sp.odemarfim.model.Tutor;
import br.senai.sp.odemarfim.repository.RoleRepository;
import br.senai.sp.odemarfim.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginController(TutorRepository tutorRepository, RoleRepository roleRepository) {
        this.tutorRepository = tutorRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "login/acessoNegado";  // nome do HTML
    }

    @GetMapping
    public String homePet(){
        return "login/login";
    }

    @GetMapping("/registrar")
    public String fazerCadastro(Model model){

        model.addAttribute("tutor", new Tutor());

        return "login/register";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Tutor tutor, BindingResult result,
                         RedirectAttributes attributes ) {

        // Se houver erro de validação, retorna para o template tutor/inserir.html
        if (result.hasErrors()) {
            if (tutor.getId() != null) {
                return "tutor/alterar";
            }
            return "login/register";
        }

        if(!tutor.getSenha().isEmpty()){
            tutor.getUser().setEmail(tutor.getEmail());
            tutor.getUser().setSenha(bCryptPasswordEncoder.encode(tutor.getSenha()));
            tutor.addRole(roleRepository);

        }


        // Salva o tutor no banco de dados
        tutorRepository.save(tutor);


        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Tutor salvo com sucesso!");

        // Redireciona para a página de listagem de tutors
        return "redirect:/login";
    }

}