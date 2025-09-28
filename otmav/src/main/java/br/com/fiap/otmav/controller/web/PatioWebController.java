package br.com.fiap.otmav.controller.web;
import br.com.fiap.otmav.domain.filial.FilialRepository;
import br.com.fiap.otmav.domain.patio.CreatePatioDto;
import br.com.fiap.otmav.domain.patio.ReadPatioDto;
import br.com.fiap.otmav.domain.patio.UpdatePatioDto;
import br.com.fiap.otmav.domain.regiao.ReadRegiaoDto;
import br.com.fiap.otmav.domain.regiao.RegiaoRepository;
import br.com.fiap.otmav.service.PatioService;
import br.com.fiap.otmav.web.form.PatioCreateForm;
import br.com.fiap.otmav.web.form.PatioUpdateForm;
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

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/patios")
public class PatioWebController {

    @Autowired
    private PatioService patioService;

    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private RegiaoRepository regiaoRepository;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) Integer totalMotos,
            @RequestParam(required = false) Integer capacidadeMoto,
            @RequestParam(required = false) Long filialId,
            @RequestParam(required = false) Long regiaoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadPatioDto> pageResult = patioService.findAllFiltered(totalMotos, capacidadeMoto, filialId, regiaoId, pageable);

        model.addAttribute("patiosPage", pageResult);
        model.addAttribute("totalMotos", totalMotos);
        model.addAttribute("capacidadeMoto", capacidadeMoto);
        model.addAttribute("filialId", filialId);
        model.addAttribute("regiaoId", regiaoId);

        model.addAttribute("filiais", filialRepository.findAll());

        List<ReadRegiaoDto> regioesDto = regiaoRepository.findAll()
                .stream()
                .map(ReadRegiaoDto::new)
                .collect(Collectors.toList());
        model.addAttribute("regioes", regioesDto);

        return "patios/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new PatioCreateForm());
        model.addAttribute("filiais", filialRepository.findAll());
        List<ReadRegiaoDto> regioesDto = regiaoRepository.findAll()
                .stream()
                .map(ReadRegiaoDto::new)
                .collect(Collectors.toList());
        model.addAttribute("regioes", regioesDto);
        return "patios/form";
    }

    // CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid PatioCreateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            model.addAttribute("filiais", filialRepository.findAll());
            List<ReadRegiaoDto> regioesDto = regiaoRepository.findAll()
                    .stream()
                    .map(ReadRegiaoDto::new)
                    .collect(Collectors.toList());
            model.addAttribute("regioes", regioesDto);
            return "patios/form";
        }

        CreatePatioDto dto = new CreatePatioDto(form.getTotalMotos(), form.getCapacidadeMoto(), form.getFilialId(), form.getRegiaoId());
        patioService.create(dto);

        redirectAttributes.addFlashAttribute("success", "Pátio criado com sucesso!");
        return "redirect:/patios";
    }

    // SHOW  Patio
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        ReadPatioDto rd = patioService.findById(id);
        model.addAttribute("patio", rd);
        return "patios/show";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadPatioDto p = patioService.findById(id);
        PatioUpdateForm form = new PatioUpdateForm(p.totalMotos(), p.capacidadeMoto(), p.filial() != null ? p.filial().id() : null, p.regiao() != null ? p.regiao().id() : null);
        model.addAttribute("updateForm", form);
        model.addAttribute("patioId", id);
        model.addAttribute("filiais", filialRepository.findAll());
        List<ReadRegiaoDto> regioesDto = regiaoRepository.findAll()
                .stream()
                .map(ReadRegiaoDto::new)
                .collect(Collectors.toList());
        model.addAttribute("regioes", regioesDto);
        return "patios/form";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid PatioUpdateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            model.addAttribute("patioId", id);
            model.addAttribute("filiais", filialRepository.findAll());
            List<ReadRegiaoDto> regioesDto = regiaoRepository.findAll()
                    .stream()
                    .map(ReadRegiaoDto::new)
                    .collect(Collectors.toList());
            model.addAttribute("regioes", regioesDto);
            return "patios/form";
        }

        UpdatePatioDto dto = new UpdatePatioDto(form.getTotalMotos(), form.getCapacidadeMoto(), form.getFilialId(), form.getRegiaoId());
        patioService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Pátio atualizado com sucesso!");
        return "redirect:/patios";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        patioService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Pátio excluído com sucesso!");
        return "redirect:/patios";
    }
}
