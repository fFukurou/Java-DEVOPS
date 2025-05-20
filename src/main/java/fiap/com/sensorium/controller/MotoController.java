package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.modelo.ModeloRepository;
import fiap.com.sensorium.domain.moto.*;
import fiap.com.sensorium.domain.motorista.MotoristaRepository;
import fiap.com.sensorium.domain.setor.SetorRepository;
import fiap.com.sensorium.domain.situacao.SituacaoRepository;
import fiap.com.sensorium.service.MotoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("motos")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private MotoristaRepository motoristaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private SituacaoRepository situacaoRepository;

    @Autowired
    private MotoService motoService;


    // GET
    @GetMapping
    public ResponseEntity<Page<ReadMotoDto>> listAll(
            @RequestParam(required = false) String condicao,
            @RequestParam(required = false) Long setorId,
            @PageableDefault(size = 10, sort = "placa") Pageable pageable
    ) {
        Page<Moto> motos = motoService.filterQuery(condicao, setorId, pageable);

        return ResponseEntity.ok(motos.map(ReadMotoDto::new));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ReadMotoDto> getById(@PathVariable Long id) {
        return motoRepository.findById(id)
                .map(moto -> ResponseEntity.ok(new ReadMotoDto(moto)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST
    @PostMapping
    @Transactional
    public ResponseEntity<ReadMotoDto> create(@RequestBody @Valid CreateMotoDto dto) {
        Moto moto = new Moto();
        motoService.updateMotoFromDto(moto, dto);

        Moto savedMoto = motoRepository.save(moto);
        return ResponseEntity
                .created(URI.create("/motos/" + savedMoto.getId()))
                .body(new ReadMotoDto(savedMoto));
    }

    // PUT
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ReadMotoDto> update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMotoDto dto
    ) {
        return motoRepository.findById(id)
                .map(moto -> {
                    motoService.updateMotoFromDto(moto, dto);
                    return ResponseEntity.ok(new ReadMotoDto(moto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (motoRepository.existsById(id)) {
            motoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
