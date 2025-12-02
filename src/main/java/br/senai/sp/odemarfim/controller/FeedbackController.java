package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.model.Feedback;
import br.senai.sp.odemarfim.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/novo")
    public String form(Model model) {
        if (!model.containsAttribute("feedback")) {
            model.addAttribute("feedback", new Feedback());
        }
        return "feedback/feedback"; // feedback.html
    }

    @PostMapping("/enviar")
    public String enviar(@Valid @ModelAttribute("feedback") Feedback feedback,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.feedback", result);
            redirectAttributes.addFlashAttribute("feedback", feedback);
            return "redirect:/feedback/novo";
        }

        feedbackService.save(feedback);

        return "redirect:/feedback/success";
    }

    @GetMapping("/success")
    public String success() {
        return "feedback/feedback-success";
    }
}
