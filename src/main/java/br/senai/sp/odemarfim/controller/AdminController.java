package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.model.Admin;
import br.senai.sp.odemarfim.repository.AdminRepository;
import br.senai.sp.odemarfim.repository.RoleRepository;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /*Método para adicionar novo admin*/

    @GetMapping
    public String listagem(Model model){
        List<Admin> listaAdmins = adminRepository.findAll();

        model.addAttribute("admins", listaAdmins);

        return "admin/listagem";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nomeAdmin") String nomeAdmin) {
        if (nomeAdmin == null) {
            return "redirect:/";
        }
        List<Admin> listaAdmins = adminRepository.findByNomeContainingIgnoreCase(nomeAdmin);
        model.addAttribute("admin",listaAdmins);
        return "admin/listagem";
    }

    @GetMapping("/novo")
    public String cadastrar(Model model){

        //Adiciona um objeto admin vazio para ser carregado no formulário
        model.addAttribute("admin", new Admin());

        // Retorna o template admin/inserir.html
        return "admin/inserir";
    }

    @PostMapping("/salvar")
    public String salvarAdmin(@Valid Admin admin, BindingResult result,
                             RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template admin/inserir.html
        if (result.hasErrors()) {
            if (admin.getId() != null) {
                return "admin/alterar";
            }
            return "admin/inserir";
        }

        if(!admin.getSenha().isEmpty()){
            admin.getUser().setEmail(admin.getEmail());
            admin.getUser().setSenha(bCryptPasswordEncoder.encode(admin.getSenha()));
            admin.addRole(roleRepository);

        }


        // Salva o admin no banco de dados
        adminRepository.save(admin);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Administrador salvo com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/admin";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o admin no banco de dados
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o admin no objeto model para ser carregado no formulário
        model.addAttribute("admin", admin);

        // Retorna o template admin/alterar.html
        return "admin/alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Admin admin = adminRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        adminRepository.delete(admin);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Administrador excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/admin";
    }
}

