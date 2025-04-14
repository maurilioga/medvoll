package br.com.estudos.med.voll.api.validation.agendamento;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaPacienteAtivoAgendamento implements ValidadorAgendamento{

    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosMarcaConsulta dados) {

        if(!pacienteRepository.findAtivoById(dados.idPaciente())) {
            throw new ValidacaoException("Paciente inativo!");
        }
    }
}
