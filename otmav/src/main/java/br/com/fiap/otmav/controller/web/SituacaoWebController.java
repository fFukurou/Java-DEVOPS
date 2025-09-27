package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.situacao.*;
import br.com.fiap.otmav.service.SituacaoService;
import br.com.fiap.otmav.web.form.SituacaoCreateForm;
import br.com.fiap.otmav.web.form.SituacaoUpdateForm;
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
@RequestMapping("/situacoes")
public class SituacaoWebController {

    @Autowired
    private SituacaoService situacaoService;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) SituacaoStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadSituacaoDto> pageResult = situacaoService.findAllFiltered(nome, status, pageable);

        model.addAttribute("situacoesPage", pageResult);
        model.addAttribute("nome", nome);
        model.addAttribute("status", status);
        // supply enum values for filter select
        model.addAttribute("allStatuses", SituacaoStatus.values());

        return "situacoes/list";
    }

    // NEW FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new SituacaoCreateForm());
        model.addAttribute("allStatuses", SituacaoStatus.values());
        return "situacoes/form";
    }

    // CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid SituacaoCreateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            model.addAttribute("allStatuses", SituacaoStatus.values());
            return "situacoes/form";
        }

        situacaoService.create(form.toCreateDto());
        redirectAttributes.addFlashAttribute("success", "Situação criada com sucesso!");
        return "redirect:/situacoes";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadSituacaoDto s = situacaoService.findById(id);
        SituacaoUpdateForm form = new SituacaoUpdateForm(s.nome(), s.descricao(), s.status());
        model.addAttribute("updateForm", form);
        model.addAttribute("situacaoId", id);
        model.addAttribute("allStatuses", SituacaoStatus.values());
        return "situacoes/form";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid SituacaoUpdateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            model.addAttribute("situacaoId", id);
            model.addAttribute("allStatuses", SituacaoStatus.values());
            return "situacoes/form";
        }

        situacaoService.update(id, form.toUpdateDto());
        redirectAttributes.addFlashAttribute("success", "Situação atualizada com sucesso!");
        return "redirect:/situacoes";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        situacaoService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Situação excluída com sucesso!");
        return "redirect:/situacoes";
    }

    // SHOW single Situacao
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        ReadSituacaoDto s = situacaoService.findById(id);
        model.addAttribute("situacao", s);
        return "situacoes/show";
    }
}
