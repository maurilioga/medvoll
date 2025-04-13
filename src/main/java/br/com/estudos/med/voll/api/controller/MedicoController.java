package br.com.estudos.med.voll.api.controller;

import br.com.estudos.med.voll.api.dto.DadosAtualizaMedico;
import br.com.estudos.med.voll.api.dto.DadosCadastroMedico;
import br.com.estudos.med.voll.api.dto.DadosDetalhamentoMedico;
import br.com.estudos.med.voll.api.dto.DadosListagemMedico;
import br.com.estudos.med.voll.api.model.Endereco;
import br.com.estudos.med.voll.api.model.Medico;
import br.com.estudos.med.voll.api.repository.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {

        Medico medico = new Medico(dados);
        medicoRepository.save(medico);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> buscarMedicos(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {

        Page<DadosListagemMedico> page = medicoRepository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarMedico(@RequestBody @Valid DadosAtualizaMedico dados) {

        Medico medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarDados(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity inativarMedico(@PathVariable Long id) {

        Medico medico = medicoRepository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharMedico(@PathVariable Long id) {

        Medico medico = medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
