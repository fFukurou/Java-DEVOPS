package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.situacao.ReadSituacaoDto;
import fiap.com.sensorium.domain.situacao.SituacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("situacao")
public class SituacaoController {

    @Autowired
    private SituacaoRepository repository;

    @GetMapping
    public ResponseEntity<Page<ReadSituacaoDto>> listAll(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable pageable) {
        var page = repository.findAll(pageable).map(ReadSituacaoDto::new);
        return ResponseEntity.ok(page);
    }
}
