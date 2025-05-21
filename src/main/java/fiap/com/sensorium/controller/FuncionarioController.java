package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.funcionario.Funcionario;
import fiap.com.sensorium.domain.funcionario.FuncionarioRepository;
import fiap.com.sensorium.domain.funcionario.ReadFuncionarioDto;
import fiap.com.sensorium.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    // GET
    @GetMapping
    public ResponseEntity<Page<ReadFuncionarioDto>> listAll(
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) Long filialId,
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        Page<Funcionario> funcionarios = funcionarioService.filterQuery(
                cargo, filialId, pageable);

        return ResponseEntity.ok(funcionarios.map(ReadFuncionarioDto::new));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ReadFuncionarioDto getById(@PathVariable Long id) {
        // Service throws EntityNotFoundException if not found
        return new ReadFuncionarioDto(funcionarioService.findByIdOrThrow(id));
    }

    // CLEAR CACHE
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        funcionarioService.clearCache();
        return ResponseEntity.noContent().build();
    }
}