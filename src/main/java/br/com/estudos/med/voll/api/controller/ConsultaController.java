package br.com.estudos.med.voll.api.controller;

import br.com.estudos.med.voll.api.dto.DadosDetalhamentoConsulta;
import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import br.com.estudos.med.voll.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity marcarConsulta(@RequestBody DadosMarcaConsulta dadosConsulta, UriComponentsBuilder uriBuilder) {

        Consulta consulta = consultaService.marcarConsulta(dadosConsulta);
        consultaRepository.save(consulta);

        var uri = uriBuilder.path("/consulta/{id}").buildAndExpand(consulta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoConsulta(consulta));
    }
}
