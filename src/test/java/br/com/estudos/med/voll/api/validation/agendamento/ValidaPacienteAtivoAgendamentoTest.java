package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidaPacienteAtivoAgendamentoTest {

    @InjectMocks
    private ValidaPacienteAtivoAgendamento validaPacienteAtivoAgendamento;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private DadosMarcaConsulta dadosMarcaConsulta;

    @Test
    void testValidarPacienteAtivoAgendamento() {

        given(pacienteRepository.findAtivoById(dadosMarcaConsulta.idPaciente())).willReturn(true);

        assertDoesNotThrow(() -> validaPacienteAtivoAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidarPacienteAtivoAgendamentoException() {

        given(pacienteRepository.findAtivoById(dadosMarcaConsulta.idPaciente())).willReturn(false);

        assertThrows(ValidacaoException.class, () -> validaPacienteAtivoAgendamento.validar(dadosMarcaConsulta));
    }
}