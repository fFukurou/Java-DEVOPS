package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.dados.CreateDadosDto;
import br.com.fiap.otmav.domain.funcionario.CreateFuncionarioDto;
import br.com.fiap.otmav.domain.funcionario.ReadFuncionarioDto;
import br.com.fiap.otmav.service.DadosService;
import br.com.fiap.otmav.service.FilialService;
import br.com.fiap.otmav.service.FuncionarioService;
import br.com.fiap.otmav.web.form.FuncionarioCreateForm;
import br.com.fiap.otmav.web.form.FuncionarioUpdateForm;
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
@RequestMapping("/funcionarios")
public class FuncionarioWebController {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private DadosService dadosService;

    @Autowired
    private FilialService filialService;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) Long filialId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadFuncionarioDto> pageResult = funcionarioService.findAllFiltered(cargo, filialId, null, pageable);

        model.addAttribute("funcionariosPage", pageResult);
        model.addAttribute("cargo", cargo);
        model.addAttribute("filialId", filialId);
        model.addAttribute("filiais", filialService.findAll()); // helper method on service expected, if not replace with repo call
        return "funcionarios/list";
    }

    // SHOW CREATE FORM (inline Dados)
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new FuncionarioCreateForm());
        model.addAttribute("filiais", filialService.findAll());
        return "funcionarios/form";
    }

    // HANDLE CREATE: create Dados then create Funcionario (one flow)
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid FuncionarioCreateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("filiais", filialService.findAll());
            return "funcionarios/form";
        }

        // Build CreateDadosDto for dadosService.create(...)
        CreateDadosDto createDados = new CreateDadosDto(
                form.getCpf(),
                form.getTelefone(),
                form.getEmail(),
                form.getSenha(),
                form.getNome()
        );

        // create dados (dadosService will hash password and validate duplicates)
        var readDados = dadosService.create(createDados);

        // call existing create flow: CreateFuncionarioDto expects cargo, dadosId, filialId
        CreateFuncionarioDto createFuncionarioDto = new CreateFuncionarioDto(
                form.getCargo(),
                readDados.id(),
                form.getFilialId()
        );

        funcionarioService.create(createFuncionarioDto);

        redirectAttributes.addFlashAttribute("success", "Funcionário criado com sucesso!");
        return "redirect:/funcionarios";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadFuncionarioDto rf = funcionarioService.findById(id);
        FuncionarioUpdateForm form = new FuncionarioUpdateForm(rf.cargo(), rf.filial() != null ? rf.filial().id() : null);
        model.addAttribute("updateForm", form);
        model.addAttribute("funcionarioId", id);
        model.addAttribute("filiais", filialService.findAll());
        return "funcionarios/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid FuncionarioUpdateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("filiais", filialService.findAll());
            model.addAttribute("funcionarioId", id);
            return "funcionarios/form";
        }

        // Build Update DTO (you already have UpdateFuncionarioDto in domain)
        var dto = new br.com.fiap.otmav.domain.funcionario.UpdateFuncionarioDto(
                form.getCargo(),
                null, // not editing dados from this form
                form.getFilialId()
        );

        funcionarioService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Funcionário atualizado com sucesso!");
        return "redirect:/funcionarios";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        funcionarioService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Funcionário excluído com sucesso!");
        return "redirect:/funcionarios";
    }
}
