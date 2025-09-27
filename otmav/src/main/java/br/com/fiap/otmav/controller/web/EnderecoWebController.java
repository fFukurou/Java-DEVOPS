package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.endereco.ReadEnderecoDto;
import br.com.fiap.otmav.domain.endereco.UpdateEnderecoDto;
import br.com.fiap.otmav.service.EnderecoService;
import br.com.fiap.otmav.web.form.EnderecoCreateForm;
import br.com.fiap.otmav.web.form.EnderecoUpdateForm;
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
@RequestMapping("/enderecos")
public class EnderecoWebController {

    @Autowired
    private EnderecoService enderecoService;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) Integer numero,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String codigoPais,
            @RequestParam(required = false) String codigoPostal,
            @RequestParam(required = false) String rua,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadEnderecoDto> pageResult = enderecoService.findAllFiltered(numero, estado, codigoPais, codigoPostal, rua, pageable);

        model.addAttribute("enderecosPage", pageResult);
        model.addAttribute("numero", numero);
        model.addAttribute("estado", estado);
        model.addAttribute("codigoPais", codigoPais);
        model.addAttribute("codigoPostal", codigoPostal);
        model.addAttribute("rua", rua);
        return "enderecos/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new EnderecoCreateForm());
        return "enderecos/form";
    }

    // HANDLE CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid EnderecoCreateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "enderecos/form";
        }

        enderecoService.create(form.toCreateDto());
        redirectAttributes.addFlashAttribute("success", "Endereço criado com sucesso!");
        return "redirect:/enderecos";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadEnderecoDto rd = enderecoService.findById(id);
        EnderecoUpdateForm form = new EnderecoUpdateForm(rd.numero(), rd.estado(), rd.codigoPais(), rd.codigoPostal(), rd.complemento(), rd.rua());
        model.addAttribute("updateForm", form);
        model.addAttribute("enderecoId", id);
        return "enderecos/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid EnderecoUpdateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("enderecoId", id);
            return "enderecos/form";
        }

        UpdateEnderecoDto dto = form.toUpdateDto();
        enderecoService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Endereço atualizado com sucesso!");
        return "redirect:/enderecos";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        enderecoService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Endereço excluído com sucesso!");
        return "redirect:/enderecos";
    }
}
