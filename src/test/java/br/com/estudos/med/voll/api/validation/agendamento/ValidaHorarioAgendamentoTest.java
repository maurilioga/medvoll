package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Especialidade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidaHorarioAgendamentoTest {

    @InjectMocks
    private ValidaHorarioAgendamento validaHorarioAgendamento;

    @Test
    void testValidarHorarioAgendamento() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.of(2025,04,30,15,00), Especialidade.CARDIOLOGIA);

        assertDoesNotThrow(() -> validaHorarioAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidarHorarioAgendamentoExceptionHoraMenor() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.of(2025,04,30,6,00), Especialidade.CARDIOLOGIA);

        assertThrows(ValidacaoException.class, () -> validaHorarioAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidarHorarioAgendamentoExceptionHoraMaior() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.of(2025,04,30,20,00), Especialidade.CARDIOLOGIA);

        assertThrows(ValidacaoException.class, () -> validaHorarioAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidarHorarioAgendamentoExceptionHoraMaiorDomingo() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.of(2025,04,27,20,00), Especialidade.CARDIOLOGIA);

        assertThrows(ValidacaoException.class, () -> validaHorarioAgendamento.validar(dadosMarcaConsulta));
    }
}