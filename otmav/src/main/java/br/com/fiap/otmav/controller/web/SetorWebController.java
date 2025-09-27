package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.setor.CreateSetorDto;
import br.com.fiap.otmav.domain.setor.ReadSetorDto;
import br.com.fiap.otmav.domain.setor.UpdateSetorDto;
import br.com.fiap.otmav.domain.patio.PatioRepository;
import br.com.fiap.otmav.domain.regiao.ReadRegiaoDto;
import br.com.fiap.otmav.domain.regiao.RegiaoRepository;
import br.com.fiap.otmav.service.SetorService;
import br.com.fiap.otmav.web.form.SetorCreateForm;
import br.com.fiap.otmav.web.form.SetorUpdateForm;
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
@RequestMapping("/setores")
public class SetorWebController {

    @Autowired
    private SetorService setorService;

    @Autowired
    private PatioRepository patioRepository;

    @Autowired
    private RegiaoRepository regiaoRepository;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) Integer qtdMoto,
            @RequestParam(required = false) Integer capacidade,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) Long patioId,
            @RequestParam(required = false) Long regiaoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadSetorDto> pageResult = setorService.findAllFiltered(qtdMoto, capacidade, nome, cor, patioId, regiaoId, pageable);

        model.addAttribute("setoresPage", pageResult);
        model.addAttribute("qtdMoto", qtdMoto);
        model.addAttribute("capacidade", capacidade);
        model.addAttribute("nome", nome);
        model.addAttribute("cor", cor);
        model.addAttribute("patioId", patioId);
        model.addAttribute("regiaoId", regiaoId);

        // patios (entities are fine: templates will show patio.id + maybe filial inside)
        model.addAttribute("patios", patioRepository.findAll());

        // convert regioes to DTOs for using .localizacao / .area in template
        List<ReadRegiaoDto> regioes = regiaoRepository.findAll().stream().map(ReadRegiaoDto::new).collect(Collectors.toList());
        model.addAttribute("regioes", regioes);

        return "setores/list";
    }

    // NEW FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new SetorCreateForm());
        model.addAttribute("patios", patioRepository.findAll());
        List<ReadRegiaoDto> regioes = regiaoRepository.findAll().stream().map(ReadRegiaoDto::new).collect(Collectors.toList());
        model.addAttribute("regioes", regioes);
        return "setores/form";
    }

    // CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid SetorCreateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            model.addAttribute("patios", patioRepository.findAll());
            List<ReadRegiaoDto> regioes = regiaoRepository.findAll().stream().map(ReadRegiaoDto::new).collect(Collectors.toList());
            model.addAttribute("regioes", regioes);
            return "setores/form";
        }

        CreateSetorDto dto = form.toCreateDto();
        setorService.create(dto);

        redirectAttributes.addFlashAttribute("success", "Setor criado com sucesso!");
        return "redirect:/setores";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadSetorDto s = setorService.findById(id);
        SetorUpdateForm form = new SetorUpdateForm(s.qtdMoto(), s.capacidade(), s.nomeSetor(), s.descricao(), s.cor(), s.patio() != null ? s.patio().id() : null, s.regiao() != null ? s.regiao().id() : null);
        model.addAttribute("updateForm", form);
        model.addAttribute("setorId", id);
        model.addAttribute("patios", patioRepository.findAll());
        List<ReadRegiaoDto> regioes = regiaoRepository.findAll().stream().map(ReadRegiaoDto::new).collect(Collectors.toList());
        model.addAttribute("regioes", regioes);
        return "setores/form";
    }

    // UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid SetorUpdateForm form,
            BindingResult binding,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            model.addAttribute("setorId", id);
            model.addAttribute("patios", patioRepository.findAll());
            List<ReadRegiaoDto> regioes = regiaoRepository.findAll().stream().map(ReadRegiaoDto::new).collect(Collectors.toList());
            model.addAttribute("regioes", regioes);
            return "setores/form";
        }

        UpdateSetorDto dto = form.toUpdateDto();
        setorService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Setor atualizado com sucesso!");
        return "redirect:/setores";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        setorService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Setor excluído com sucesso!");
        return "redirect:/setores";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        ReadSetorDto rd = setorService.findById(id);
        model.addAttribute("setor", rd);
        return "setores/show";
    }

}
