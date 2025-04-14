package br.com.estudos.med.voll.api.controller;

import br.com.estudos.med.voll.api.dto.DadosAtualizaPaciente;
import br.com.estudos.med.voll.api.dto.DadosCadastroPaciente;
import br.com.estudos.med.voll.api.dto.DadosDetalhamentoPaciente;
import br.com.estudos.med.voll.api.dto.DadosListagemPaciente;
import br.com.estudos.med.voll.api.model.Paciente;
import br.com.estudos.med.voll.api.repository.PacienteRepository;
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

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dadosPaciente, UriComponentsBuilder uriBuilder) {

        Paciente paciente = new Paciente(dadosPaciente);
        pacienteRepository.save(paciente);

        var uri = uriBuilder.path("/paciente/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharPaciente(@PathVariable Long id) {

        Paciente paciente = pacienteRepository.getReferenceById(id);
        DadosDetalhamentoPaciente dadosPaciente = new DadosDetalhamentoPaciente(paciente);

        return ResponseEntity.ok().body(dadosPaciente);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPaciente(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {

        List<Paciente> pacienteList = pacienteRepository.findAll();
        Page<DadosListagemPaciente> listagemPacientes = pacienteRepository.findAllByAtivoTrue(pageable).map(DadosListagemPaciente::new);

        return ResponseEntity.ok().body(listagemPacientes);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarPaciente(@RequestBody @Valid DadosAtualizaPaciente dadosPaciente) {

        Paciente paciente = pacienteRepository.getReferenceById(dadosPaciente.id());
        paciente.atualizaDados(dadosPaciente);

        return ResponseEntity.ok().body(new DadosListagemPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirPaciente(@PathVariable Long id) {

        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.exclui();

        return ResponseEntity.noContent().build();
    }
}
