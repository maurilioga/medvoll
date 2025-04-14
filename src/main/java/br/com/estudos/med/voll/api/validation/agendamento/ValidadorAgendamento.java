package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;

public interface ValidadorAgendamento {

    void validar(DadosMarcaConsulta dados);
}
