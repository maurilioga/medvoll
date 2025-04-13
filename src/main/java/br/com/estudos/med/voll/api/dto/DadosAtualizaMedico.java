package br.com.estudos.med.voll.api.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizaMedico(
        @NotNull
        Long id,

        String nome,
        String email,
        String telefone) {
}
