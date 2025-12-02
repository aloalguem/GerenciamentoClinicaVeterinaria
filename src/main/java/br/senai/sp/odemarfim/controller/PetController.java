package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.model.Pet;
import br.senai.sp.odemarfim.model.Tutor;
import br.senai.sp.odemarfim.repository.PetRepository;
import br.senai.sp.odemarfim.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @GetMapping
    public String listagem(Model model) {
        List<Pet> listaPets = petRepository.findAll();
        model.addAttribute("pets", listaPets);

        return "pet/listagem";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return "redirect:/pet";
        }
        List<Pet> listaPets = petRepository.findByNomeContainingIgnoreCase(nome);
        model.addAttribute("pets", listaPets); // Alterado de "pet" para "pets"
        return "pet/listagem";
    }

    @GetMapping("/novo")
    public String cadastrar(Model model){
        model.addAttribute("pet", new Pet());
        return "pet/inserir";
    }

    @GetMapping("/inserir/{id}")
    public String inserir(@PathVariable("id") Long id, Model model){
        Pet pet = new Pet();
        pet.setTutorId(id);

        model.addAttribute("pet", pet);
        return "pet/inserir";
    }


    @PostMapping("/salvar")
    public String salvar(@Valid Pet pet, BindingResult result, RedirectAttributes attributes) {
        // Se houver erro de validação, retorna para o formulário correto
        if (result.hasErrors()) {
            if (pet.getId() != null) {
                return "pet/alterar";
            }
            return "pet/inserir"; // Corrigido para retornar para a página de inserção
        }

        // Se o pet já tiver um ID, ele será atualizado. Caso contrário, será salvo como um novo registro.
        petRepository.save(pet);

        attributes.addFlashAttribute("mensagem", "Pet salvo com sucesso!");
        return "redirect:/tutor/alterar/"+pet.getTutorId();
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o pet no banco de dados
        Pet pet = petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o pet no objeto model para ser carregado no formulário
        model.addAttribute("pet", pet);

        // Retorna o template pet/alterar.html
        return "pet/alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attributes) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        petRepository.delete(pet);
        attributes.addFlashAttribute("mensagem", "Pet excluído com sucesso!");
        return "/pet/listagem";
    }


    }
