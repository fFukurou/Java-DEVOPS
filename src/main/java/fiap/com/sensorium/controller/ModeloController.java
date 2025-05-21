package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.modelo.*;
import fiap.com.sensorium.infra.exception.EntityNotFoundException;
import fiap.com.sensorium.service.ModeloService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.URI;

@RestController
@RequestMapping("modelos")
@Tag(name = "Modelo", description = "CRUD completo de modelos de motos")

public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private ModeloRepository modeloRepository;

    // GET
    @Operation(
            summary = "Listar modelos",
            description = "Retorna modelos paginados, com filtros opcionais"
    )
    @ApiResponse(responseCode = "200", description = "Lista de modelos retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadModeloDto>> listAll(
            @Parameter(description = "Nome para filtro (opcional)", example = "Kawasaki Ninja")
            @RequestParam(required = false) String nome,

            @Parameter(description = "Tipo de combustível para filtro (opcional)", example = "Gasolina")
            @RequestParam(required = false) String combustivel,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "nome") Pageable pageable
    ) {
        Page<Modelo> modelos = modeloService.filterQuery(nome, combustivel, pageable);
        return ResponseEntity.ok(modelos.map(ReadModeloDto::new));
    }

    @Operation(summary = "Buscar modelo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo encontrado"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @GetMapping("/{id}")
    public ReadModeloDto getById(
            @Parameter(description = "ID do modelo", example = "4")
            @PathVariable Long id
    ) {
        Modelo modelo = modeloService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Modelo não encontrado com ID: " + id));
        return new ReadModeloDto(modelo);
    }

    @Operation(summary = "Criar novo modelo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Modelo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    @Transactional
    public ResponseEntity<ReadModeloDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação do modelo",
                    required = true
            )
            @RequestBody @Valid CreateModeloDto dto
    ) {
        Modelo modelo = new Modelo();
        modeloService.copyDtoToModel(dto, modelo);

        Modelo saved = modeloService.create(modelo);
        return ResponseEntity
                .created(URI.create("/modelos/" + saved.getId()))
                .body(new ReadModeloDto(saved));
    }

    @Operation(summary = "Atualizar modelo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modelo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @PutMapping("/{id}")
    @Transactional
    public ReadModeloDto update(
            @Parameter(description = "ID do modelo a ser atualizado", example = "4")
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados atualizados do modelo",
                    required = true
            )
            @RequestBody @Valid UpdateModeloDto dto
    ) {
        Modelo modelo = modeloService.findByIdOrThrow(id);
        modeloService.copyDtoToModel(dto, modelo);
        return new ReadModeloDto(modeloService.update(modelo));
    }

    @Operation(summary = "Excluir modelo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Modelo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Modelo não encontrado")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do modelo a ser excluído", example = "3")
            @PathVariable Long id
    ) {
        modeloService.findByIdOrThrow(id);
        modeloService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Limpar cache", description = "Limpa o cache de modelos")
    @ApiResponse(responseCode = "204", description = "Cache limpo com sucesso")
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearModeloCache() {
        modeloService.clearCache();
        return ResponseEntity.noContent().build();
    }
}
