package fiap.com.sensorium.controller;

import fiap.com.sensorium.domain.situacao.ReadSituacaoDto;
import fiap.com.sensorium.domain.situacao.Situacao;
import fiap.com.sensorium.domain.situacao.SituacaoRepository;
import fiap.com.sensorium.service.SituacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("situacao")
public class SituacaoController {

    @Autowired
    private SituacaoRepository repository;

    @Autowired
    private SituacaoService situacaoService;

    @GetMapping
    public ResponseEntity<Page<ReadSituacaoDto>> listByStatus(
            @PageableDefault(size = 10, sort = "status") Pageable pageable,
            @RequestParam(required = false) String status
    ) {
        Page<Situacao> page = (status != null)
                ? situacaoService.findByStatus(status, pageable)
                : situacaoService.findAll(pageable);

        return ResponseEntity.ok(page.map(ReadSituacaoDto::new));
    }
}
