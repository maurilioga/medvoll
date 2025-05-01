package br.com.estudos.med.voll.api.validation.cancelamento;

import br.com.estudos.med.voll.api.dto.DadosCancelaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidaAntecedenciaCancelamentoTest {

    @InjectMocks
    private ValidaAntecedenciaCancelamento validaAntecedenciaCancelamento;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private DadosCancelaConsulta dadosCancelaConsulta;

    @Test
    void testValidarAntecedenciaCancelamento() {

        Consulta consulta = new Consulta();
        consulta.setHorario(LocalDateTime.now().plusDays(2));

        given(consultaRepository.getReferenceById(dadosCancelaConsulta.idConsulta())).willReturn(consulta);

        assertDoesNotThrow(() -> validaAntecedenciaCancelamento.validar(dadosCancelaConsulta));
    }

    @Test
    void testValidarAntecedenciaCancelamentoException() {

        Consulta consulta = new Consulta();
        consulta.setHorario(LocalDateTime.now());

        given(consultaRepository.getReferenceById(dadosCancelaConsulta.idConsulta())).willReturn(consulta);

        assertThrows(ValidacaoException.class, () -> validaAntecedenciaCancelamento.validar(dadosCancelaConsulta));
    }
}