package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.model.User;
import br.senai.sp.odemarfim.repository.UserRepository;
import br.senai.sp.odemarfim.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    /*Método para adicionar novo usuário*/

    @GetMapping
    public String listagem(Model model){
        List<User> listaUsuarios = userRepository.findAll();

        model.addAttribute("users", listaUsuarios);

        return "user/listagem";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("email") String email) {
        if (email == null) {
            return "redirect:/user";
        }
        List<User> listaUsers = userRepository.findByEmailContaining(email);
        model.addAttribute("users",listaUsers);
        return "user/listagem";
    }

    @GetMapping("/novo")
    public String cadastrar(Model model){

        //Adiciona um objeto user vazio para ser carregado no formulário
        model.addAttribute("user", new User());

        // Retorna o template user/inserir.html
        return "user/inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid User user, BindingResult result,
                              RedirectAttributes attributes){

        // Se houver erro de validação, retorna para o template user/inserir.html
        if (result.hasErrors()) {
            if (user.getId() != null) {
                return "user/alterar";
            }
            return "user/listagem";
        }




        // Salva o aluno no banco de dados
        userRepository.save(user);


        userRepository.save(user);



        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/user";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o user no banco de dados
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o user no objeto model para ser carregado no formulário
        model.addAttribute("user", user);

        // Retorna o template user/alterar.html
        return "user/alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        User user = userRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        userRepository.delete(user);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Usuário excluído com sucesso!");

        // Redireciona para a página de listagem de usuários
        return "redirect:/user";
    }
}
