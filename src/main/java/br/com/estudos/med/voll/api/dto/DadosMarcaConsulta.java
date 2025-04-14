package br.com.estudos.med.voll.api.dto;

import br.com.estudos.med.voll.api.model.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosMarcaConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull @Future
        LocalDateTime data,

        Especialidade especialidade) {
}
