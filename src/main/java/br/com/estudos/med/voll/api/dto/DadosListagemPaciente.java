package br.com.estudos.med.voll.api.dto;

import br.com.estudos.med.voll.api.model.Paciente;

public record DadosListagemPaciente(String nome, String email, String cpf) {

    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf());
    }
}
