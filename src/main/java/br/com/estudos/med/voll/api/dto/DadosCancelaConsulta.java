package br.com.estudos.med.voll.api.dto;

import br.com.estudos.med.voll.api.model.MotivoCancelamentoConsulta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelaConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamentoConsulta motivoCancelamento) {
}
