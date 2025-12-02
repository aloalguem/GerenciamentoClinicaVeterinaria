package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.enums.Status;
import br.senai.sp.odemarfim.model.*;
import br.senai.sp.odemarfim.repository.AgendamentoRepository;
import br.senai.sp.odemarfim.repository.PetRepository;
import br.senai.sp.odemarfim.repository.ProcedimentoRepository;
import br.senai.sp.odemarfim.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/meupet")
public class MeuPetController {

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ProcedimentoRepository procedimentoRepository;
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/home")
    public String homePet(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Tutor tutor = tutorRepository.findByUser(user);

        List<Pet> pets = petRepository.findByTutorId(tutor.getId());
        List<Agendamento> agendamentos = agendamentoRepository.findByTutorId(tutor.getId());

        model.addAttribute("tutor", tutor);
        model.addAttribute("pets", pets);
        model.addAttribute("agendamentos", agendamentos);
        model.addAttribute("procedimentos", procedimentoRepository.findAll());
        model.addAttribute("agendamento", new Agendamento());
        model.addAttribute("pet", new Pet());

        return "meupet/homepet";
    }

    @PostMapping("/salvarPet")
    public String salvarPet(@Valid Pet pet, BindingResult result, RedirectAttributes attributes) {
        // NOTE: If validation fails, you need to return a view that knows how to handle errors (e.g., an insertion form).
        // Since we don't have pet/inserir or pet/alterar, this is the current best guess:
        if (result.hasErrors()) {
            // Assuming this is used for insertion/alteration views outside the scope of homepet.
            return pet.getId() != null ? "pet/alterar" : "pet/inserir";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Tutor tutor = tutorRepository.findByUser(user);
        pet.setTutorId(tutor.getId());

        petRepository.save(pet);
        attributes.addFlashAttribute("mensagem", "Pet salvo com sucesso!");
        return "redirect:home";
    }

    @PostMapping("/salvarAgendamento")
    public String salvarAgendamento(@Valid Agendamento agendamento, BindingResult result,
                                    RedirectAttributes attributes, Model model) {

        if (result.hasErrors()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) auth.getPrincipal();
            Tutor tutor = tutorRepository.findByUser(user);

            model.addAttribute("tutor", tutor);
            model.addAttribute("pets", petRepository.findByTutorId(tutor.getId()));
            model.addAttribute("agendamentos", agendamentoRepository.findByTutorId(tutor.getId()));
            model.addAttribute("procedimentos", procedimentoRepository.findAll());
            model.addAttribute("pet", new Pet());
            model.addAttribute("agendamento", agendamento);

            return "meupet/homepet";
        }

        Long petId = agendamento.getPet() != null ? agendamento.getPet().getId() : null;
        if (petId == null) {
            attributes.addFlashAttribute("erro", "Pet não informado!");
            return "redirect:home";
        }

        Pet pet = petRepository.findById(petId).orElse(null);
        if (pet == null) {
            attributes.addFlashAttribute("erro", "Pet não encontrado!");
            return "redirect:home";
        }

        Long procedimentoId = agendamento.getProcedimento() != null ? agendamento.getProcedimento().getId() : null;
        if (procedimentoId == null) {
            attributes.addFlashAttribute("erro", "Procedimento não informado!");
            return "redirect:home";
        }

        Procedimento procedimento = procedimentoRepository.findById(procedimentoId).orElse(null);
        if (procedimento == null) {
            attributes.addFlashAttribute("erro", "Procedimento não encontrado!");
            return "redirect:home";
        }

        agendamento.setPet(pet);
        agendamento.setProcedimento(procedimento);
        agendamento.setServico(procedimento.getNome());
        agendamento.setStatus(String.valueOf(Status.PENDENTE));
        agendamentoRepository.save(agendamento);

        attributes.addFlashAttribute("mensagem", "Agendamento salvo com sucesso!");
        return "redirect:home";
    }

    @GetMapping("/cancelarAgendamento/{id}")
    public String cancelarAgendamento(@PathVariable("id") Long id, RedirectAttributes attributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Tutor tutor = tutorRepository.findByUser(user);

        Agendamento agendamento = agendamentoRepository.findById(id).orElse(null);
        
        if (agendamento == null) {
            attributes.addFlashAttribute("erro", "Agendamento não encontrado!");
            return "redirect:home";
        }

        // Verificar se o agendamento pertence a um pet do tutor
        if (agendamento.getPet() == null || !agendamento.getPet().getTutorId().equals(tutor.getId())) {
            attributes.addFlashAttribute("erro", "Você não tem permissão para cancelar este agendamento!");
            return "redirect:home";
        }

        // Verificar se já está cancelado
        if ("CANCELADO".equals(agendamento.getStatus())) {
            attributes.addFlashAttribute("erro", "Este agendamento já está cancelado!");
            return "redirect:/meupet/home";
        }

        agendamento.setStatus(String.valueOf(Status.CANCELADO));
        agendamentoRepository.save(agendamento);

        attributes.addFlashAttribute("mensagem", "Agendamento cancelado com sucesso!");
        return "redirect:/meupet/home";
    }
}