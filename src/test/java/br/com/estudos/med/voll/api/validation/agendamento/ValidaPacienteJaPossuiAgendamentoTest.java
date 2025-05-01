package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Especialidade;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidaPacienteJaPossuiAgendamentoTest {

    @InjectMocks
    private ValidaPacienteJaPossuiAgendamento validaPacienteJaPossuiAgendamento;

    @Mock
    private ConsultaRepository consultaRepository;

    @Test
    void testValidaPacienteJaPossuiAgendamento() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.of(2025,04,30,15,00), Especialidade.CARDIOLOGIA);

        given(consultaRepository.existsByPacienteIdAndHorarioBetweenAndMotivoCancelamentoIsNull(dadosMarcaConsulta.idPaciente(),
                dadosMarcaConsulta.data().withHour(7), dadosMarcaConsulta.data().withHour(18))).willReturn(false);

        assertDoesNotThrow(() -> validaPacienteJaPossuiAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidaPacienteJaPossuiAgendamentoException() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l, LocalDateTime.of(2025,04,30,15,00), Especialidade.CARDIOLOGIA);

        given(consultaRepository.existsByPacienteIdAndHorarioBetweenAndMotivoCancelamentoIsNull(dadosMarcaConsulta.idPaciente(),
                dadosMarcaConsulta.data().withHour(7), dadosMarcaConsulta.data().withHour(18))).willReturn(true);

        assertThrows(ValidacaoException.class, () -> validaPacienteJaPossuiAgendamento.validar(dadosMarcaConsulta));
    }
}