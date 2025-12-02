package br.senai.sp.odemarfim.controller;

import br.senai.sp.odemarfim.model.Estoque;
import br.senai.sp.odemarfim.repository.EstoqueRepository;
import br.senai.sp.odemarfim.util.FileUploadUtil;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.fileupload.FileUpload;
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
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueRepository estoqueRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/assets/img/estoque/";

    /* Listagem */
    @GetMapping
    public String listagem(Model model) {
        List<Estoque> listaEstoques = estoqueRepository.findAll();
        model.addAttribute("estoques", listaEstoques);
        return "estoque/listagem";
    }

    /* Buscar por nome */
    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null) {
            return "redirect:/estoque";
        }
        List<Estoque> listaEstoques = estoqueRepository.findByNomeContainingIgnoreCase(nome);
        model.addAttribute("estoques", listaEstoques);
        return "estoque/listagem";
    }

    /* Formulário novo */
    @GetMapping("/novo")
    public String cadastrar(Model model) {
        model.addAttribute("estoque", new Estoque());
        return "estoque/inserir";
    }

    /* Salvar com upload */
    @PostMapping("/salvar")
    public String salvarEstoque(@Valid Estoque estoque,
                                BindingResult result,
                                @RequestParam("imagem") MultipartFile file,
                                RedirectAttributes attributes) throws IOException {




        estoqueRepository.save(estoque);

        String extensao = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = estoque.getId() + "." + extensao;
        estoque.setImagem("/assets/img/estoque/" + fileName);
        String uploadPasta = "src/main/resources/static/assets/img/estoque/";
        FileUploadUtil.saveFile(uploadPasta, fileName, file);

        estoqueRepository.save(estoque);

        attributes.addFlashAttribute("mensagem", "Estoque salvo com sucesso!");
        return "redirect:/estoque";
    }

    /* Alterar */
    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        model.addAttribute("estoque", estoque);
        return "estoque/alterar";
    }

    /* Excluir */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido"));
        estoqueRepository.delete(estoque);
        attributes.addFlashAttribute("mensagem", "Estoque excluído com sucesso!");
        return "redirect:/estoque";
    }
}
