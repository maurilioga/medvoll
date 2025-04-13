package br.com.estudos.med.voll.api.dto;

import br.com.estudos.med.voll.api.model.Endereco;
import br.com.estudos.med.voll.api.model.Especialidade;
import br.com.estudos.med.voll.api.model.Paciente;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String cpf, String telefone, Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {

        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }
}
