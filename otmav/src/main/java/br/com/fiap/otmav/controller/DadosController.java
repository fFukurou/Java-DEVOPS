package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.dados.*;
import br.com.fiap.otmav.service.DadosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springdoc.core.annotations.ParameterObject;

import java.net.URI;

@RestController
@RequestMapping("/api/dados")
@Tag(name = "Dados", description = "CRUD for Dados")
public class DadosController {

    @Autowired
    private DadosService dadosService;

    @Operation(summary = "Create new Dados record")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<ReadDadosDto> create(
            @RequestBody @Valid CreateDadosDto dto,
            UriComponentsBuilder uriBuilder) {
        ReadDadosDto created = dadosService.create(dto);
        URI uri = uriBuilder.path("/api/dados/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET
    @Operation(
            summary = "Listar dados",
            description = "Retorna dados paginados com filtros opcionais"
    )
    @ApiResponse(responseCode = "200", description = "Lista de dados retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadDadosDto>> findAll(
            @Parameter(description = "CPF (opcional)", example = "12345678901")
            @RequestParam(required = false) String cpf,

            @Parameter(description = "Nome (opcional)", example = "Vicenzo")
            @RequestParam(required = false) String nome,

            @Parameter(description = "Email (opcional)", example = "email@exemplo.com")
            @RequestParam(required = false) String email,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(dadosService.findAllFiltered(cpf, nome, email, pageable));
    }


    @Operation(summary = "Get Dados by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadDadosDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(dadosService.findById(id));
    }

    @Operation(summary = "Update Dados")
    @PutMapping("/{id}")
    public ResponseEntity<ReadDadosDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateDadosDto dto) {
        return ResponseEntity.ok(dadosService.update(id, dto));
    }

    @Operation(summary = "Delete Dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dadosService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
