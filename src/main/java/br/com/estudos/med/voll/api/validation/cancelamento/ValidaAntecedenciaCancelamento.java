package br.com.estudos.med.voll.api.validation.cancelamento;

import br.com.estudos.med.voll.api.dto.DadosCancelaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidaAntecedenciaCancelamento implements ValidadorCancelamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosCancelaConsulta dadosConsulta) {

        LocalDateTime horaAtual = LocalDateTime.now();
        Consulta consulta = consultaRepository.getReferenceById(dadosConsulta.idConsulta());

        if (horaAtual.isAfter(consulta.getHorario().minusDays(1))) {
            throw new ValidacaoException("Cancelamentos só podem ser realizado com mínimo de 24h de antecedência!");
        }
    }
}
