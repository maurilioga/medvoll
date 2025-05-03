package br.com.estudos.med.voll.api.service;

import br.com.estudos.med.voll.api.dto.DadosCancelaConsulta;
import br.com.estudos.med.voll.api.dto.DadosDetalhamentoConsulta;
import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.model.Medico;
import br.com.estudos.med.voll.api.model.Paciente;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import br.com.estudos.med.voll.api.repository.MedicoRepository;
import br.com.estudos.med.voll.api.repository.PacienteRepository;
import br.com.estudos.med.voll.api.validation.agendamento.ValidadorAgendamento;
import br.com.estudos.med.voll.api.validation.cancelamento.ValidadorCancelamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorAgendamento> validadorAgendamentos;

    @Autowired
    private List<ValidadorCancelamento> validadorCancelamentos;

    public DadosDetalhamentoConsulta marcarConsulta(DadosMarcaConsulta dadosConsulta) {

        if(!pacienteRepository.existsById(dadosConsulta.idPaciente())) {
            throw new ValidacaoException("O paciente informado não existe!");
        }

        if(dadosConsulta.idMedico() != null && !medicoRepository.existsById(dadosConsulta.idMedico())) {
            throw new ValidacaoException("O médico informado não existe!");
        }

        validadorAgendamentos.forEach(v -> v.validar(dadosConsulta));

        Paciente paciente = pacienteRepository.getReferenceById(dadosConsulta.idPaciente());
        Medico medico = escolherMedico(dadosConsulta);

        Consulta consulta = new Consulta(null, paciente, medico, dadosConsulta.data(), null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosMarcaConsulta dadosConsulta) {

        if(dadosConsulta.idMedico() != null) {
            return medicoRepository.getReferenceById(dadosConsulta.idMedico());
        }

        if(dadosConsulta.especialidade() == null) {
            throw new ValidacaoException("Especialidade não preenchida!");
        }

        Medico medico = medicoRepository.escolherMedicoAleatorioPorData(dadosConsulta.especialidade(), dadosConsulta.data());

        if(medico == null) {
            throw new ValidacaoException("Nenhum médico disponível!");
        }

        return medico;
    }

    public void cancelarConsulta(DadosCancelaConsulta dadosConsulta) {

        validadorCancelamentos.forEach(v -> v.validar(dadosConsulta));

        Consulta consulta = consultaRepository.getReferenceById(dadosConsulta.idConsulta());
        consulta.setMotivoCancelamento(dadosConsulta.motivoCancelamento());
    }
}
