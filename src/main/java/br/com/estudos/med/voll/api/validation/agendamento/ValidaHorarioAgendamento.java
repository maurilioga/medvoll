package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class ValidaHorarioAgendamento implements ValidadorAgendamento{

    public void validar(DadosMarcaConsulta dados) {

        LocalDateTime dataConsulta = dados.data();
        boolean domingo = DayOfWeek.SUNDAY.equals(dataConsulta.getDayOfWeek());
        boolean antesDaAbertura = dataConsulta.getHour() < 7;
        boolean depoisDoEncerramento = dataConsulta.getHour() > 18;

        if(domingo || antesDaAbertura || depoisDoEncerramento) {
            throw new ValidacaoException("Consulta fora do horário de funcionamento!");
        }
    }
}
