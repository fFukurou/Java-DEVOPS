package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.filial.*;
import br.com.fiap.otmav.service.FilialService;
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

import java.net.URI;

@RestController
@RequestMapping("/api/filiais")
@Tag(name = "Filiais", description = "CRUD de filiais")
public class FilialController {

    @Autowired
    private FilialService filialService;

    // CREATE
    @Operation(summary = "Cadastrar nova filial")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Filial criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ReadFilialDto> create(
            @RequestBody @Valid CreateFilialDto dto,
            UriComponentsBuilder uriBuilder) {
        ReadFilialDto created = filialService.create(dto);
        URI uri = uriBuilder.path("/api/filiais/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET
    @Operation(
            summary = "Listar filiais",
            description = "Retorna filiais paginadas com filtros opcionais"
    )
    @ApiResponse(responseCode = "200", description = "Lista de filiais retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ReadFilialDto>> findAll(
            @Parameter(description = "Nome da filial (opcional)", example = "Loja Central")
            @RequestParam(required = false) String nome,

            @Parameter(description = "ID do endereco (opcional)", example = "1")
            @RequestParam(required = false) Long enderecoId,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(filialService.findAllFiltered(nome, enderecoId, pageable));
    }


    // GET BY ID
    @Operation(summary = "Get Filial By ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadFilialDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(filialService.findById(id));
    }

    // UPDATE
    @Operation(summary = "Update Filial")
    @PutMapping("/{id}")
    public ResponseEntity<ReadFilialDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateFilialDto dto) {
        return ResponseEntity.ok(filialService.update(id, dto));
    }
    // DELETE
    @Operation(summary = "Delete Filial")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Filial excluída")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        filialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}