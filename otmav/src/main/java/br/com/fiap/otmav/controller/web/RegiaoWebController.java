package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.regiao.CreateRegiaoDto;
import br.com.fiap.otmav.domain.regiao.UpdateRegiaoDto;
import br.com.fiap.otmav.domain.regiao.ReadRegiaoDto;
import br.com.fiap.otmav.service.RegiaoService;
import br.com.fiap.otmav.web.form.RegiaoCreateForm;
import br.com.fiap.otmav.web.form.RegiaoUpdateForm;
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
@RequestMapping("/regioes")
public class RegiaoWebController {

    @Autowired
    private RegiaoService regiaoService;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) Double area,
            @RequestParam(required = false) String searchWkt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadRegiaoDto> regs = regiaoService.findAllFiltered(area, searchWkt, pageable);

        model.addAttribute("regiaoPage", regs);
        model.addAttribute("area", area);
        model.addAttribute("searchWkt", searchWkt);
        return "regioes/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new RegiaoCreateForm());
        return "regioes/form";
    }

    // HANDLE CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid RegiaoCreateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            return "regioes/form";
        }

        CreateRegiaoDto dto = new CreateRegiaoDto(form.getLocalizacao(), form.getArea());
        regiaoService.create(dto);

        redirectAttributes.addFlashAttribute("success", "Região criada com sucesso!");
        return "redirect:/regioes";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadRegiaoDto r = regiaoService.findById(id);
        RegiaoUpdateForm form = new RegiaoUpdateForm(r.localizacao(), r.area());
        model.addAttribute("updateForm", form);
        model.addAttribute("regiaoId", id);
        return "regioes/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid RegiaoUpdateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            model.addAttribute("regiaoId", id);
            return "regioes/form";
        }

        UpdateRegiaoDto dto = new UpdateRegiaoDto(form.getLocalizacao(), form.getArea());
        regiaoService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Região atualizada com sucesso!");
        return "redirect:/regioes";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        regiaoService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Região excluída com sucesso!");
        return "redirect:/regioes";
    }
}
