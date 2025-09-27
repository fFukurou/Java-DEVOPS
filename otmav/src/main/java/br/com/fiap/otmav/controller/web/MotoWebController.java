package br.com.fiap.otmav.controller.web;

import br.com.fiap.otmav.domain.moto.CreateMotoDto;
import br.com.fiap.otmav.domain.moto.ReadMotoDto;
import br.com.fiap.otmav.domain.moto.UpdateMotoDto;
import br.com.fiap.otmav.domain.motorista.ReadMotoristaDto;
import br.com.fiap.otmav.domain.modelo.ReadModeloDto;
import br.com.fiap.otmav.domain.setor.ReadSetorDto;
import br.com.fiap.otmav.domain.situacao.ReadSituacaoDto;
import br.com.fiap.otmav.service.MotoService;
import br.com.fiap.otmav.domain.motorista.MotoristaRepository;
import br.com.fiap.otmav.domain.modelo.ModeloRepository;
import br.com.fiap.otmav.domain.setor.SetorRepository;
import br.com.fiap.otmav.domain.situacao.SituacaoRepository;
import br.com.fiap.otmav.web.form.MotoCreateForm;
import br.com.fiap.otmav.web.form.MotoUpdateForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/motos")
public class MotoWebController {

    @Autowired
    private MotoService motoService;

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private SituacaoRepository situacaoRepository;

    // LIST
    @GetMapping
    public String list(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) String condicao,
            @RequestParam(required = false) Long motoristaId,
            @RequestParam(required = false) Long modeloId,
            @RequestParam(required = false) Long setorId,
            @RequestParam(required = false) Long situacaoId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReadMotoDto> motos = motoService.findAllFiltered(placa, chassi, condicao, motoristaId, modeloId, setorId, situacaoId, pageable);

        model.addAttribute("motosPage", motos);
        model.addAttribute("placa", placa);
        model.addAttribute("chassi", chassi);
        model.addAttribute("condicao", condicao);
        model.addAttribute("motoristaId", motoristaId);
        model.addAttribute("modeloId", modeloId);
        model.addAttribute("setorId", setorId);
        model.addAttribute("situacaoId", situacaoId);

        // for filters selects
        model.addAttribute("motoristas", motoristaRepository.findAll());
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("setores", setorRepository.findAll());
        model.addAttribute("situacoes", situacaoRepository.findAll());

        return "motos/list";
    }

    //    VIEW SINGLE MOTO
    @GetMapping(path = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String show(@PathVariable Long id, Model model) {
        ReadMotoDto moto = motoService.findById(id);
        model.addAttribute("moto", moto);
        return "motos/show";
    }

    // SHOW CREATE FORM
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("createForm", new MotoCreateForm());
        model.addAttribute("motoristas", motoristaRepository.findAll());
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("setores", setorRepository.findAll());
        model.addAttribute("situacoes", situacaoRepository.findAll());
        return "motos/form";
    }

    // HANDLE CREATE
    @PostMapping
    public String create(
            @ModelAttribute("createForm") @Valid MotoCreateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("motoristas", motoristaRepository.findAll());
            model.addAttribute("modelos", modeloRepository.findAll());
            model.addAttribute("setores", setorRepository.findAll());
            model.addAttribute("situacoes", situacaoRepository.findAll());
            return "motos/form";
        }

        CreateMotoDto dto = form.toCreateDto();
        motoService.create(dto);
        redirectAttributes.addFlashAttribute("success", "Moto criada com sucesso!");
        return "redirect:/motos";
    }

    // SHOW EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ReadMotoDto rf = motoService.findById(id);
        // build update form populated with existing values
        MotoUpdateForm form = new MotoUpdateForm(
                rf.placa(),
                rf.chassi(),
                rf.condicao(),
                rf.localizacao(),
                rf.motorista() != null ? rf.motorista().id() : null,
                rf.modelo() != null ? rf.modelo().id() : null,
                rf.setor() != null ? rf.setor().id() : null,
                rf.situacao() != null ? rf.situacao().id() : null
        );

        model.addAttribute("updateForm", form);
        model.addAttribute("motoId", id);
        model.addAttribute("motoristas", motoristaRepository.findAll());
        model.addAttribute("modelos", modeloRepository.findAll());
        model.addAttribute("setores", setorRepository.findAll());
        model.addAttribute("situacoes", situacaoRepository.findAll());
        return "motos/form";
    }

    // HANDLE UPDATE
    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @ModelAttribute("updateForm") @Valid MotoUpdateForm form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("motoristas", motoristaRepository.findAll());
            model.addAttribute("modelos", modeloRepository.findAll());
            model.addAttribute("setores", setorRepository.findAll());
            model.addAttribute("situacoes", situacaoRepository.findAll());
            model.addAttribute("motoId", id);
            return "motos/form";
        }

        UpdateMotoDto dto = form.toUpdateDto();
        motoService.update(id, dto);

        redirectAttributes.addFlashAttribute("success", "Moto atualizada com sucesso!");
        return "redirect:/motos";
    }

    // HANDLE DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        motoService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Moto excluída com sucesso!");
        return "redirect:/motos";
    }
}
