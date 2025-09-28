package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.endereco.EnderecoRepository;
import br.com.fiap.otmav.domain.filial.CreateFilialDto;
import br.com.fiap.otmav.domain.filial.ReadFilialDto;
import br.com.fiap.otmav.domain.filial.UpdateFilialDto;
import br.com.fiap.otmav.service.FilialService;
import br.com.fiap.otmav.web.form.FilialCreateForm;
import br.com.fiap.otmav.web.form.FilialUpdateForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/filiais")
public class FilialWebController {

    @Autowired
    private FilialService filialService;

    @Autowired
    private EnderecoRepository enderecoRepository;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long enderecoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadFilialDto> filiais = filialService.findAllFiltered(nome, enderecoId, pageable);

        model.addAttribute("filiaisPage", filiais);
        model.addAttribute("nome", nome);
        model.addAttribute("enderecoId", enderecoId);
        model.addAttribute("enderecos", enderecoRepository.findAll());
        return "filiais/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new FilialCreateForm());
        model.addAttribute("enderecos", enderecoRepository.findAll());
        return "filiais/form";
    }

    // HANDLE CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid FilialCreateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("enderecos", enderecoRepository.findAll());
            return "filiais/form";
        }

        CreateFilialDto dto = new CreateFilialDto(form.getNome(), form.getEnderecoId());
        filialService.create(dto);

        redirectAttributes.addFlashAttribute("success", "Filial criada com sucesso!");
        return "redirect:/filiais";
    }

    // SHOW Filial
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        ReadFilialDto rd = filialService.findById(id);
        model.addAttribute("filial", rd);
        return "filiais/show";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadFilialDto rf = filialService.findById(id);
        FilialUpdateForm form = new FilialUpdateForm(rf.nome(), rf.endereco() != null ? rf.endereco().id() : null);
        model.addAttribute("updateForm", form);
        model.addAttribute("filialId", id);
        model.addAttribute("enderecos", enderecoRepository.findAll());
        return "filiais/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid FilialUpdateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("enderecos", enderecoRepository.findAll());
            model.addAttribute("filialId", id);
            return "filiais/form";
        }

        UpdateFilialDto dto = new UpdateFilialDto(form.getNome(), form.getEnderecoId());
        filialService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Filial atualizada com sucesso!");
        return "redirect:/filiais";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        filialService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Filial excluída com sucesso!");
        return "redirect:/filiais";
    }
}
