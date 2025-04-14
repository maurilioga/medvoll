package br.com.estudos.med.voll.api.dto;

import br.com.estudos.med.voll.api.model.Endereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizaPaciente(
        @NotNull
        Long id,

        String nome,

        String telefone,

        Endereco endereco) {
}
