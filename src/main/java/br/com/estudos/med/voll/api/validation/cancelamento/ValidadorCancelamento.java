package br.com.estudos.med.voll.api.validation.cancelamento;

import br.com.estudos.med.voll.api.dto.DadosCancelaConsulta;

public interface ValidadorCancelamento {

    public void validar(DadosCancelaConsulta dadosConsulta);
}
