package fiap.com.sensorium.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import fiap.com.sensorium.domain.filial.Filial;
import fiap.com.sensorium.domain.filial.FilialRepository;
import fiap.com.sensorium.domain.filial.ReadFilialDto;
import fiap.com.sensorium.infra.exception.EntityNotFoundException;
import fiap.com.sensorium.service.FilialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("filiais")
@Tag(name = "Filial", description = "Operações com filiais")
public class FilialController {

    @Autowired
    private FilialRepository filialRepository;
    @Autowired
    private FilialService filialService;

    // GET
    @Operation(
            summary = "Listar filiais",
            description = "Retorna lista paginada com filtros opcionais"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadFilialDto>> listAll(
            @Parameter(description = "Nome da filial (opcional)", example = "Filial Zona Sul")
            @RequestParam(required = false) String nomeFilial,

            @Parameter(description = "Estado (opcional)", example = "SP")
            @RequestParam(required = false) String estado,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "nomeFilial") Pageable pageable
    ) {
        return ResponseEntity.ok(
                filialService.filterQuery(nomeFilial, estado, pageable)
                        .map(ReadFilialDto::new)
        );
    }

    // GET BY ID
    @Operation(summary = "Buscar filial por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filial encontrada"),
            @ApiResponse(responseCode = "404", description = "Filial não encontrada")
    })
    @GetMapping("/{id}")
    public ReadFilialDto getById(
            @Parameter(description = "ID da filial", example = "1")
            @PathVariable Long id
    ) {
        return filialService.findById(id)
                .map(ReadFilialDto::new)
                .orElseThrow(() -> new EntityNotFoundException("Filial não encontrada com ID: " + id));
    }

    // CLEAR CACHE
    @Operation(summary = "Limpar cache de filiais")
    @ApiResponse(responseCode = "204", description = "Cache limpo")
    @PostMapping("/clear-cache")
    public ResponseEntity<Void> clearCache() {
        filialService.clearCache();
        return ResponseEntity.noContent().build();
    }
}