package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.modelo.*;
import br.com.fiap.otmav.service.ModeloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/modelos")
@Tag(name = "Modelos", description = "CRUD para Modelos")
public class ModeloController {

    @Autowired
    private ModeloService modeloService;

    // CREATE
    @Operation(summary = "CIAR MODELO")
    @PostMapping
    public ResponseEntity<ReadModeloDto> create(@RequestBody @Valid CreateModeloDto dto, UriComponentsBuilder uriBuilder) {
        ReadModeloDto created = modeloService.create(dto);
        URI uri = uriBuilder.path("/api/modelos/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET
    @Operation(summary = "LIST MODELOS")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadModeloDto>> findAll(
            @Parameter(description = "Nome do Modelo", example = "Civic")
            @RequestParam(required = false) String nome,

            @Parameter(description = "TIPO DE COMBUSTIVEL", example = "Gasolina")
            @RequestParam(required = false) String tipoCombustivel,

            @Parameter(description = "CAPACIDADE DO TANQUE", example = "50")
            @RequestParam(required = false) Integer tanque,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(modeloService.findAllFiltered(nome, tipoCombustivel, tanque, pageable));
    }

    // GET BY ID
    @Operation(summary = "Get Modelo by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadModeloDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(modeloService.findById(id));
    }

    // UPDATE
    @Operation(summary = "Update Modelo")
    @PutMapping("/{id}")
    public ResponseEntity<ReadModeloDto> update(@PathVariable Long id, @RequestBody @Valid UpdateModeloDto dto) {
        return ResponseEntity.ok(modeloService.update(id, dto));
    }

    // DELETE
    @Operation(summary = "Delete Modelo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        modeloService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
