package br.com.fiap.otmav.controller;

import br.com.fiap.otmav.domain.situacao.*;
import br.com.fiap.otmav.service.SituacaoService;
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
@RequestMapping("/api/situacoes")
@Tag(name = "Situacoes", description = "CRUD para SITUACOES")
public class SituacaoController {

    @Autowired
    private SituacaoService situacaoService;

    // CREATE
    @Operation(summary = "CREATE SITUACAO")
    @PostMapping
    public ResponseEntity<ReadSituacaoDto> create(@RequestBody @Valid CreateSituacaoDto dto, UriComponentsBuilder uriBuilder) {
        ReadSituacaoDto created = situacaoService.create(dto);
        URI uri = uriBuilder.path("/api/situacoes/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    // GET
    @Operation(summary = "LISTAR SITUACOES")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<Page<ReadSituacaoDto>> findAll(
            @Parameter(description = "Name (parcial)", example = "ATIVO")
            @RequestParam(required = false) String nome,

            @Parameter(description = "STATUS")
            @RequestParam(required = false) SituacaoStatus status,

            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(situacaoService.findAllFiltered(nome, status, pageable));
    }

    // GET BY ID
    @Operation(summary = "Get situacao by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReadSituacaoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(situacaoService.findById(id));
    }

    // UPDATE
    @Operation(summary = "Update situacao")
    @PutMapping("/{id}")
    public ResponseEntity<ReadSituacaoDto> update(@PathVariable Long id, @RequestBody @Valid UpdateSituacaoDto dto) {
        return ResponseEntity.ok(situacaoService.update(id, dto));
    }

    // DELETE
    @Operation(summary = "Delete situacao")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        situacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
