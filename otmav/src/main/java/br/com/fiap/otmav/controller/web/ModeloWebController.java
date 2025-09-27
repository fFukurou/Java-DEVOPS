package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.modelo.ReadModeloDto;
import br.com.fiap.otmav.domain.modelo.UpdateModeloDto;
import br.com.fiap.otmav.service.ModeloService;
import br.com.fiap.otmav.web.form.ModeloCreateForm;
import br.com.fiap.otmav.web.form.ModeloUpdateForm;
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
@RequestMapping("/modelos")
public class ModeloWebController {

    @Autowired
    private ModeloService modeloService;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String tipoCombustivel,
            @RequestParam(required = false) Integer tanque,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadModeloDto> modelos = modeloService.findAllFiltered(nome, tipoCombustivel, tanque, pageable);

        model.addAttribute("modelosPage", modelos);
        model.addAttribute("nome", nome);
        model.addAttribute("tipoCombustivel", tipoCombustivel);
        model.addAttribute("tanque", tanque);
        return "modelos/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new ModeloCreateForm());
        return "modelos/form";
    }

    // HANDLE CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid ModeloCreateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "modelos/form";
        }

        modeloService.create(form.toCreateDto());
        redirectAttributes.addFlashAttribute("success", "Modelo criado com sucesso!");
        return "redirect:/modelos";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadModeloDto rd = modeloService.findById(id);
        ModeloUpdateForm form = new ModeloUpdateForm(rd.nomeModelo(), rd.frenagem(), rd.sisPartida(), rd.tanque(), rd.tipoCombustivel(), rd.consumo());
        model.addAttribute("updateForm", form);
        model.addAttribute("modeloId", id);
        return "modelos/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid ModeloUpdateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("modeloId", id);
            return "modelos/form";
        }

        UpdateModeloDto dto = form.toUpdateDto();
        modeloService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Modelo atualizado com sucesso!");
        return "redirect:/modelos";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        modeloService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Modelo excluído com sucesso!");
        return "redirect:/modelos";
    }
}
