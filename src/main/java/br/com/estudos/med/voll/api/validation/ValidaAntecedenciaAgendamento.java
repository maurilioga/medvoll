package br.com.estudos.med.voll.api.validation;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidaAntecedenciaAgendamento implements ValidadorAgendamento{

    public void validar(DadosMarcaConsulta dados) {

        LocalDateTime dataConsulta = dados.data();
        LocalDateTime horaAtual = LocalDateTime.now();

        long diferencaEmMinutos = Duration.between(horaAtual, dataConsulta).toMinutes();

        if(diferencaEmMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendado com minuto de 30 minutos de antecedencia!");
        }
    }
}
