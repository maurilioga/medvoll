package br.com.estudos.med.voll.api.service;

import br.com.estudos.med.voll.api.dto.DadosCancelaConsulta;
import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.*;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import br.com.estudos.med.voll.api.repository.MedicoRepository;
import br.com.estudos.med.voll.api.repository.PacienteRepository;
import br.com.estudos.med.voll.api.validation.agendamento.ValidadorAgendamento;
import br.com.estudos.med.voll.api.validation.cancelamento.ValidadorCancelamento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTest {

    @InjectMocks
    private ConsultaService consultaService;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private Paciente paciente;

    @Mock
    private Medico medico;

    @Spy
    private Consulta consulta;

    @Spy
    private List<ValidadorAgendamento> validadorAgendamentos = new ArrayList<>();

    @Spy
    private List<ValidadorCancelamento> validadorCancelamentos = new ArrayList<>();

    @Test
    void testMarcarConsulta() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA);

        given(pacienteRepository.existsById(dadosMarcaConsulta.idPaciente())).willReturn(true);
        given(medicoRepository.existsById(dadosMarcaConsulta.idMedico())).willReturn(true);
        given(medicoRepository.getReferenceById(dadosMarcaConsulta.idMedico())).willReturn(medico);
        given(pacienteRepository.getReferenceById(dadosMarcaConsulta.idPaciente())).willReturn(paciente);

        assertNotNull(consultaService.marcarConsulta(dadosMarcaConsulta));
    }

    @Test
    void testMarcarConsultaMedicoNull() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(null, 1l,
                LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA);

        given(pacienteRepository.existsById(dadosMarcaConsulta.idPaciente())).willReturn(true);
        given(pacienteRepository.getReferenceById(dadosMarcaConsulta.idPaciente())).willReturn(paciente);
        given(medicoRepository.escolherMedicoAleatorioPorData(dadosMarcaConsulta.especialidade(), dadosMarcaConsulta.data())).willReturn(medico);

        assertNotNull(consultaService.marcarConsulta(dadosMarcaConsulta));
    }

    @Test
    void testMarcarConsultaPacienteNaoExiste() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA);

        given(pacienteRepository.existsById(dadosMarcaConsulta.idPaciente())).willReturn(false);

        assertThrows(ValidacaoException.class, () -> consultaService.marcarConsulta(dadosMarcaConsulta));
    }

    @Test
    void testMarcarConsultaMedicoNaoExiste() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(1l, 1l,
                LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA);

        given(pacienteRepository.existsById(dadosMarcaConsulta.idPaciente())).willReturn(true);
        given(medicoRepository.existsById(dadosMarcaConsulta.idMedico())).willReturn(false);

        assertThrows(ValidacaoException.class, () -> consultaService.marcarConsulta(dadosMarcaConsulta));
    }

    @Test
    void testMarcarConsultaEpecialidadeNull() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(null, 1l,
                LocalDateTime.now().plusHours(1), null);

        given(pacienteRepository.existsById(dadosMarcaConsulta.idPaciente())).willReturn(true);
        given(pacienteRepository.getReferenceById(dadosMarcaConsulta.idPaciente())).willReturn(paciente);

        assertThrows(ValidacaoException.class, () -> consultaService.marcarConsulta(dadosMarcaConsulta));
    }

    @Test
    void testMarcarConsultaNenhumMedicoDisponivel() {

        DadosMarcaConsulta dadosMarcaConsulta = new DadosMarcaConsulta(null, 1l,
                LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA);

        given(pacienteRepository.existsById(dadosMarcaConsulta.idPaciente())).willReturn(true);
        given(pacienteRepository.getReferenceById(dadosMarcaConsulta.idPaciente())).willReturn(paciente);
        given(medicoRepository.escolherMedicoAleatorioPorData(dadosMarcaConsulta.especialidade(), dadosMarcaConsulta.data())).willReturn(null);

        assertThrows(ValidacaoException.class, () -> consultaService.marcarConsulta(dadosMarcaConsulta));
    }

    @Test
    void testCancelarConsulta() {

        DadosCancelaConsulta dadosCancelaConsulta = new DadosCancelaConsulta(1l, MotivoCancelamentoConsulta.DESISTENCIA);

        given(consultaRepository.getReferenceById(dadosCancelaConsulta.idConsulta())).willReturn(consulta);

        consultaService.cancelarConsulta(dadosCancelaConsulta);

        assertEquals(MotivoCancelamentoConsulta.DESISTENCIA, consulta.getMotivoCancelamento());
    }
}