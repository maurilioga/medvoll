package br.com.estudos.med.voll.api.dto;

import br.com.estudos.med.voll.api.model.Endereco;

public record DadosAtualizaPaciente(Long id, String nome, String telefone, Endereco endereco) {
}
