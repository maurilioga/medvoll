package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidaDisponibilidadeMedicoAgendamentoTest {

    @InjectMocks
    private ValidaDisponibilidadeMedicoAgendamento validaDisponibilidadeMedicoAgendamento;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private DadosMarcaConsulta dadosMarcaConsulta;

    @Mock
    private Consulta consulta;

    @Test
    void testValidaDisponibilidadeMedicoAgendamento() {

        given(consultaRepository.buscarDisponibilidadeMedico(dadosMarcaConsulta.idMedico(), dadosMarcaConsulta.data())).willReturn(null);

        assertDoesNotThrow(() -> validaDisponibilidadeMedicoAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidaDisponibilidadeMedicoAgendamentoMedicoNull() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(null, null, null, null);

        assertDoesNotThrow(() -> validaDisponibilidadeMedicoAgendamento.validar(dadosMarcaConsulta));
    }

    @Test
    void testValidaDisponibilidadeMedicoAgendamentoException() {

        given(consultaRepository.buscarDisponibilidadeMedico(dadosMarcaConsulta.idMedico(), dadosMarcaConsulta.data())).willReturn(consulta);

        assertThrows(ValidacaoException.class, () -> validaDisponibilidadeMedicoAgendamento.validar(dadosMarcaConsulta));
    }
}