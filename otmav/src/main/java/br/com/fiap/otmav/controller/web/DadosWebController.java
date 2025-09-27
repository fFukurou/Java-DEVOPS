package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.dados.CreateDadosDto;
import br.com.fiap.otmav.domain.dados.ReadDadosDto;
import br.com.fiap.otmav.domain.dados.UpdateDadosDto;
import br.com.fiap.otmav.service.DadosService;
import br.com.fiap.otmav.web.form.DadosCreateForm;
import br.com.fiap.otmav.web.form.DadosUpdateForm;
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
@RequestMapping("/dados")
public class DadosWebController {

    @Autowired
    private DadosService dadosService;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadDadosDto> dadosPage = dadosService.findAllFiltered(cpf, nome, email, pageable);

        model.addAttribute("dadosPage", dadosPage);
        model.addAttribute("cpf", cpf);
        model.addAttribute("nome", nome);
        model.addAttribute("email", email);
        return "dados/list";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new DadosCreateForm());
        return "dados/form";
    }

    // HANDLE CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid DadosCreateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "dados/form";
        }

        CreateDadosDto dto = form.toCreateDadosDto();
        dadosService.create(dto);

        redirectAttributes.addFlashAttribute("success", "Usuário criado com sucesso!");
        return "redirect:/dados";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadDadosDto rd = dadosService.findById(id);
        DadosUpdateForm form = new DadosUpdateForm(rd.telefone(), rd.email(), rd.nome());
        model.addAttribute("updateForm", form);
        model.addAttribute("dadosId", id);
        model.addAttribute("cpf", rd.cpf());
        return "dados/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid DadosUpdateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("dadosId", id);
            return "dados/form";
        }

        UpdateDadosDto dto = form.toUpdateDto();
        dadosService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Usuário atualizado com sucesso!");
        return "redirect:/dados";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        dadosService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Usuário excluído com sucesso!");
        return "redirect:/dados";
    }
}
