package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.funcionario.FuncionarioRepository;
import fiap.com.sensorium.domain.funcionario.ReadFuncionarioDto;
import fiap.com.sensorium.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("funcionarios")
@Tag(name = "Funcionário", description = "Operações com funcionários")
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    // GET
    @Operation(
            summary = "Listar funcionários",
            description = "Retorna lista paginada com filtros opcionais"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadFuncionarioDto>> listAll(
            @Parameter(description = "Cargo para filtro (opcional)", example = "Vendedor")
            @RequestParam(required = false) String cargo,

            @Parameter(description = "ID da filial (opcional)", example = "3")
            @RequestParam(required = false) Long filialId,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        return ResponseEntity.ok(
                funcionarioService.filterQuery(cargo, filialId, pageable)
                        .map(ReadFuncionarioDto::new)
        );
    }

    // GET BY ID
    @Operation(summary = "Buscar funcionário por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Funcionário encontrado"),
            @ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
    })
    @GetMapping("/{id}")
    public ReadFuncionarioDto getById(
            @Parameter(description = "ID do funcionário", example = "104")
            @PathVariable Long id
    ) {
        return new ReadFuncionarioDto(funcionarioService.findByIdOrThrow(id));
    }

    // CLEAR CACHE
    @Operation(summary = "Limpar cache de funcionários")
    @ApiResponse(responseCode = "204", description = "Cache limpo")
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        funcionarioService.clearCache();
        return ResponseEntity.noContent().build();
    }
}