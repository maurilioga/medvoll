package br.com.estudos.med.voll.api.validation;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidaPacienteJaPossuiAgendamento implements ValidadorAgendamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosMarcaConsulta dados) {

        LocalDateTime primeiroHorario = dados.data().withHour(7);
        LocalDateTime ultimoHorario = dados.data().withHour(18);

        if(consultaRepository.existsByPacienteIdAndHorarioBetween(dados.idPaciente(), primeiroHorario, ultimoHorario)) {
            throw new ValidacaoException("Paciente já possui consulta agendada para esse dia!");
        }
    }
}
