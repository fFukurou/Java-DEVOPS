package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.motorista.CreateMotoristaDto;
import br.com.fiap.otmav.domain.motorista.ReadMotoristaDto;
import br.com.fiap.otmav.domain.motorista.UpdateMotoristaDto;
import br.com.fiap.otmav.service.MotoristaService;
import br.com.fiap.otmav.domain.dados.DadosRepository;
import br.com.fiap.otmav.web.form.MotoristaCreateForm;
import br.com.fiap.otmav.web.form.MotoristaUpdateForm;
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
@RequestMapping("/motoristas")
public class MotoristaWebController {

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private DadosRepository dadosRepository;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            Model model) {

        int p = (page == null) ? 0 : page;
        int s = (size == null) ? 10 : size;
        Pageable pageable = PageRequest.of(p, s);
        Page<ReadMotoristaDto> pageResp = motoristaService.findAllFiltered(null, null, pageable);

        model.addAttribute("motoristasPage", pageResp);
        return "motoristas/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new MotoristaCreateForm());
        model.addAttribute("dadosList", dadosRepository.findAll());
        return "motoristas/form";
    }

    // HANDLE CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid MotoristaCreateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("dadosList", dadosRepository.findAll());
            return "motoristas/form";
        }

        CreateMotoristaDto dto = new CreateMotoristaDto(form.getPlano(), form.getDadosId());
        motoristaService.create(dto);

        redirectAttributes.addFlashAttribute("success", "Motorista criado com sucesso!");
        return "redirect:/motoristas";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var rm = motoristaService.findById(id);
        MotoristaUpdateForm form = new MotoristaUpdateForm(rm.plano(), rm.dados() != null ? rm.dados().id() : null);
        model.addAttribute("updateForm", form);
        model.addAttribute("motoristaId", id);
        model.addAttribute("dadosList", dadosRepository.findAll());
        return "motoristas/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid MotoristaUpdateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("motoristaId", id);
            model.addAttribute("dadosList", dadosRepository.findAll());
            return "motoristas/form";
        }

        UpdateMotoristaDto dto = new UpdateMotoristaDto(form.getPlano(), form.getDadosId());
        motoristaService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Motorista atualizado com sucesso!");
        return "redirect:/motoristas";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        motoristaService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Motorista excluído com sucesso!");
        return "redirect:/motoristas";
    }
}
