package br.com.estudos.med.voll.api.service;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.model.Medico;
import br.com.estudos.med.voll.api.model.Paciente;
import br.com.estudos.med.voll.api.repository.MedicoRepository;
import br.com.estudos.med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public Consulta marcarConsulta(DadosMarcaConsulta dadosConsulta) {

        Paciente paciente = pacienteRepository.getReferenceById(dadosConsulta.paciente());
        Medico medico;

        if(dadosConsulta.medico() != null) {
            medico = medicoRepository.getReferenceById(dadosConsulta.medico());
        } else {
            medico = medicoRepository.getReferenceById(2L);
        }

        List<Consulta> pacienteConsulta = paciente.getConsulta();
        List<Consulta> medicoConsulta = medico.getConsulta();

//        for(Consulta consulta : pacienteConsulta) {
//            if (consulta.getPaciente().getId().equals(paciente.getId()) &&
//                    consulta.getHorario().isEqual(dadosConsulta.horario())) {
//                throw new IllegalArgumentException("Horário indisponível");
//            }
//        }
//
//        for(Consulta consulta : medicoConsulta) {
//            if (consulta.getMedico().getId().equals(medico.getId()) &&
//                    consulta.getHorario().isEqual(dadosConsulta.horario())) {
//                throw new IllegalArgumentException("Horário indisponível");
//            }
//        }

        if(!paciente.getAtivo()) {
            throw new IllegalArgumentException("Paciente inativo!");
        }

        if(!medico.getAtivo()) {
            throw new IllegalArgumentException("Médico inativo!");
        }

        return new Consulta(null, paciente, medico, LocalDateTime.now());
    }
}
