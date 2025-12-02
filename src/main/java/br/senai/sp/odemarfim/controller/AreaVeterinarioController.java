package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fragments")
public class AreaVeterinarioController {

    @GetMapping
    public String redirectToHome() {
        return "redirect:/areaVeterinario";
    }

    @GetMapping("/areaVeterinario")
    public String areaVeterinario(Model model) {

                return "fragments/areaVeterinario";
    }

}
