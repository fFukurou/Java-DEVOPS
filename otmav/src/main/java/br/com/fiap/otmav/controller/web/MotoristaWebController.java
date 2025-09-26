package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.motorista.CreateMotoristaDto;
import br.com.fiap.otmav.domain.motorista.MotoristaPlano;
import br.com.fiap.otmav.domain.motorista.ReadMotoristaDto;
import br.com.fiap.otmav.domain.dados.CreateDadosDto;
import br.com.fiap.otmav.service.DadosService;
import br.com.fiap.otmav.service.MotoristaService;
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
import br.com.fiap.otmav.web.form.MotoristaCreateForm;
import br.com.fiap.otmav.web.form.MotoristaUpdateForm;

@Controller
@RequestMapping("/motoristas")
public class MotoristaWebController {

    @Autowired
    private MotoristaService motoristaService;

    @Autowired
    private DadosService dadosService;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) MotoristaPlano plano,
            @RequestParam(required = false) Long dadosId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadMotoristaDto> motoristas = motoristaService.findAllFiltered(plano, dadosId, pageable);

        model.addAttribute("motoristasPage", motoristas);
        model.addAttribute("plano", plano);
        model.addAttribute("dadosId", dadosId);
        model.addAttribute("planos", MotoristaPlano.values());
        return "motoristas/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new MotoristaCreateForm());
        model.addAttribute("planos", MotoristaPlano.values());
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
            model.addAttribute("planos", MotoristaPlano.values());
            return "motoristas/form";
        }

        // 1) create Dados (uses DadosService to handle hashing/validation)
        CreateDadosDto dadosDto = form.toCreateDadosDto();
        var readDados = dadosService.create(dadosDto);

        // 2) create Motorista linking to created Dados
        CreateMotoristaDto createMotoristaDto = new CreateMotoristaDto(form.getPlano(), readDados.id());
        motoristaService.create(createMotoristaDto);

        redirectAttributes.addFlashAttribute("success", "Motorista criado com sucesso!");
        return "redirect:/motoristas";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadMotoristaDto rf = motoristaService.findById(id);
        MotoristaUpdateForm form = new MotoristaUpdateForm(rf.plano(), rf.dados() != null ? rf.dados().id() : null);
        model.addAttribute("updateForm", form);
        model.addAttribute("motoristaId", id);
        model.addAttribute("planos", MotoristaPlano.values());
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
            model.addAttribute("planos", MotoristaPlano.values());
            model.addAttribute("motoristaId", id);
            return "motoristas/form";
        }

        motoristaService.update(id, form.toUpdateDto());
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
