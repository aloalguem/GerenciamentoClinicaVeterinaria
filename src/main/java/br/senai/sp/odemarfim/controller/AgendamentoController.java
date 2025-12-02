package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.enums.Status;
import br.senai.sp.odemarfim.model.Agendamento;
import br.senai.sp.odemarfim.model.Tutor;
import br.senai.sp.odemarfim.model.User;
import br.senai.sp.odemarfim.repository.AgendamentoRepository;
import br.senai.sp.odemarfim.repository.PetRepository;
import br.senai.sp.odemarfim.repository.ProcedimentoRepository;
import br.senai.sp.odemarfim.repository.TutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate; // Necessary for date handling
import java.time.format.DateTimeParseException; // Necessary for error handling
import java.util.List;

@Controller
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ProcedimentoRepository procedimentoRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private TutorRepository tutorRepository;




    @GetMapping
    public String listagem(Model model){
        List<Agendamento> listaAgendamentos = agendamentoRepository.findAll();

        model.addAttribute("agendamentos", listaAgendamentos);

        return "agendamento/listagem";
    }

    // --- FIXED METHOD START ---
    @PostMapping("/buscar")
    public String buscar(Model model, @Param("data") String data, RedirectAttributes attributes) {
        // Explicitly check for null or empty input
        if (data == null || data.trim().isEmpty()) {
            attributes.addFlashAttribute("mensagemInfo", "Por favor, insira uma data para busca.");
            return "redirect:/agendamento";
        }

        try {
            // Explicitly parse the String input (e.g., "2025-11-10") to a LocalDate
            LocalDate searchDate = LocalDate.parse(data);

            // Call the correct repository method using the LocalDate object
            List<Agendamento> listaAgendamentos = agendamentoRepository.findByData(searchDate);

            if (listaAgendamentos.isEmpty()) {
                model.addAttribute("mensagemInfo", "Nenhum agendamento encontrado para a data: " + data);
            }

            model.addAttribute("agendamentos", listaAgendamentos);

        } catch (DateTimeParseException e) {
            // Handle invalid date format error explicitly
            attributes.addFlashAttribute("mensagemErro", "Formato de data inválido. Use o formato AAAA-MM-DD.");
            return "redirect:/agendamento";
        }

        return "agendamento/listagem";
    }
    // --- FIXED METHOD END ---

    @GetMapping("/novo")
    public String cadastrar(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        Tutor tutor = tutorRepository.findByUser(user);

        //Adiciona um objeto agendamento vazio para ser carregado no formulário
        model.addAttribute("agendamento", new Agendamento());

        model.addAttribute("procedimentos", procedimentoRepository.findAll());

        model.addAttribute("pets", petRepository.findByTutorId(tutor.getId()));

        // Retorna o template agendamento/inserir.html
        return "agendamento/inserir";
    }

    @PostMapping("/salvar")
    public String salvarAgendamento(@Valid Agendamento agendamento, BindingResult result,
                                    RedirectAttributes attributes) {

        if (result.hasErrors()) {
            if (agendamento.getId() != null) {
                return "agendamento/alterar";
            }
            return "agendamento/inserir";
        }

        if(agendamento.getId() == null){
            agendamento.setStatus(String.valueOf(Status.PENDENTE));
        }

        if (agendamento.getPet() != null && agendamento.getPet().getId() != null) {
            petRepository.findById(agendamento.getPet().getId())
                    .ifPresent(agendamento::setPet);
        }

        if (agendamento.getProcedimento() != null && agendamento.getProcedimento().getId() != null) {
            procedimentoRepository.findById(agendamento.getProcedimento().getId())
                    .ifPresent(proc -> {
                        agendamento.setProcedimento(proc);
                        agendamento.setServico(proc.getNome());
                    });
        }

        agendamentoRepository.save(agendamento);

        attributes.addFlashAttribute("mensagem", "Agendamento salvo com sucesso!");

        return "redirect:/agendamento";
    }

    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o agendamento no banco de dados
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o agendamento no objeto model para ser carregado no formulário
        model.addAttribute("agendamento", agendamento);

        model.addAttribute("procedimentos", procedimentoRepository.findAll());

        model.addAttribute("pets", petRepository.findAll());

        // Retorna o template agendamento/alterar.html
        return "agendamento/alterar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        agendamentoRepository.delete(agendamento);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Agendamento excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/agendamento";
    }
}