package br.senai.sp.odemarfim.controller;


import br.senai.sp.odemarfim.model.Pet;
import br.senai.sp.odemarfim.model.Tutor;
import br.senai.sp.odemarfim.repository.RoleRepository;
import br.senai.sp.odemarfim.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/tutor")
public class TutorController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @GetMapping
    public String listagem(Model model) {
        List<Tutor> listaTutores = tutorRepository.findAll();
        
        model.addAttribute("tutores", listaTutores);
        
        return "tutor/listagem";
    }



    @PostMapping("/buscar")
    public String buscar(Model model, @Param("email") String email) {
        if (email == null) {
            return "redirect:/tutor";
        }
        List<Tutor> listaTutores = tutorRepository.findByEmailContaining(email);
        model.addAttribute("tutor",listaTutores);
        return "tutor/listagem";
    }


    @GetMapping("/novo")
    public String cadastrar(Model model){

        //Adiciona um objeto tutor vazio para ser carregado no formulário
        model.addAttribute("tutor", new Tutor());

        // Retorna o template tutor/inserir.html
        return "tutor/inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Tutor tutor, BindingResult result,
                         RedirectAttributes attributes ) {

        // 1. Tratamento de Erros de Validação
        if (result.hasErrors()) {
            if (tutor.getId() != null) {
                // Se for alteração e tiver erro, retorna para o formulário de alteração
                return "tutor/alterar";
            }
            // Se for inclusão e tiver erro, retorna para o formulário de inclusão
            return "tutor/inserir";
        }

        // Variável para armazenar a senha a ser salva/mantida
        String senhaASalvar = tutor.getSenha();

        // 2. Lógica para Edição (ID existe)
        if (tutor.getId() != null) {
            // Busca o tutor original no banco
            Tutor tutorOriginal = tutorRepository.findById(tutor.getId()).orElse(null);

            if (tutorOriginal != null) {
                // Se o campo de senha do formulário veio VAZIO (edição sem mudança de senha)
                if (!org.springframework.util.StringUtils.hasText(senhaASalvar)) {
                    // USA A SENHA ANTIGA do objeto original
                    tutor.getUser().setSenha(tutorOriginal.getUser().getSenha());
                } else {
                    // Se a senha foi preenchida, criptografa a nova senha
                    tutor.getUser().setSenha(bCryptPasswordEncoder.encode(senhaASalvar));
                }

                // Garante que o objeto User no Tutor que será salvo seja o User original,
                // que já tem a Role e outros dados (apenas a senha e email serão atualizados, se necessário).
                tutor.setUser(tutorOriginal.getUser());
                tutor.getUser().setEmail(tutor.getEmail()); // Garante que o email seja atualizado no User

            } else {
                // Caso não encontre o tutor, redireciona com erro.
                attributes.addFlashAttribute("mensagem", "Erro: Tutor não encontrado para edição.");
                return "redirect:/tutor";
            }
        }

        // 3. Lógica para Novo Cadastro (ID não existe)
        else {
            // Se for um novo cadastro, a senha DEVE ter sido preenchida.
            if (org.springframework.util.StringUtils.hasText(senhaASalvar)) {
                tutor.getUser().setSenha(bCryptPasswordEncoder.encode(senhaASalvar));
                tutor.getUser().setEmail(tutor.getEmail()); // Define o email no User
                tutor.addRole(roleRepository); // Adiciona a Role, se for o primeiro cadastro
            } else {
                // Se a senha não foi preenchida no novo cadastro, adicione erro e retorne.
                result.rejectValue("senha", "error.tutor", "A senha é obrigatória para novos cadastros.");
                return "tutor/inserir";
            }
        }


        // 4. Salva o Tutor
        tutorRepository.save(tutor);

        attributes.addFlashAttribute("mensagem", "Tutor salvo com sucesso!");
        return "redirect:/tutor";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o tutor no banco de dados
        Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o tutor no objeto model para ser carregado no formulário
        model.addAttribute("tutor", tutor);

        // Retorna o template tutor/alterar.html
        return "tutor/alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o tutor no banco de dados
        Tutor tutor = tutorRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o tutor do banco de dados
        tutorRepository.delete(tutor);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Tutor excluído com sucesso!");

        // Redireciona para a página de listagem de usuários
        return "redirect:/tutor";
    }
}
