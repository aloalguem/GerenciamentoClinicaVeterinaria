package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @GetMapping
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("estoques", estoqueRepository.findAll().stream().limit(5));
        return "home/index";
    }

}
