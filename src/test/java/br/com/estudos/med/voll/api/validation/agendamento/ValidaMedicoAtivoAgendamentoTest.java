package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Especialidade;
import br.com.estudos.med.voll.api.repository.MedicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidaMedicoAtivoAgendamentoTest {

    @InjectMocks
    private ValidaMedicoAtivoAgendamento validaMedicoAtivoAgendamento;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private DadosMarcaConsulta dadosMarcaConsulta;

    @Test
    void testValidaMedicoAtivoAgendamento() {

        given(medicoRepository.findAtivoById(dadosMarcaConsulta.idMedico())).willReturn(true);

        assertDoesNotThrow(() -> validaMedicoAtivoAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidaMedicoAtivoAgendamentoMedicoNull() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(null, null, null, null);

        assertDoesNotThrow(() -> validaMedicoAtivoAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidaMedicoAtivoAgendamentoException() {

        given(medicoRepository.findAtivoById(dadosMarcaConsulta.idMedico())).willReturn(false);

        assertThrows(ValidacaoException.class, () -> validaMedicoAtivoAgendamento.validar(dadosMarcaConsulta));
    }
}