package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Especialidade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidaAntecedenciaAgendamentoTest {

    @InjectMocks
    private ValidaAntecedenciaAgendamento validaAntecedenciaAgendamento;

    @Test
    void testValidarAntecedenciaAgendamento() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA);

        assertDoesNotThrow(() -> validaAntecedenciaAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidarAntecedenciaAgendamentoException() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.now(), Especialidade.CARDIOLOGIA);

        assertThrows(ValidacaoException.class, () -> validaAntecedenciaAgendamento.validar(dadosMarcaConsulta));
    }
}