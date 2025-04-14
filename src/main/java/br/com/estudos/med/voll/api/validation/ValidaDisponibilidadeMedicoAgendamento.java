package br.com.estudos.med.voll.api.validation;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaDisponibilidadeMedicoAgendamento implements ValidadorAgendamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DadosMarcaConsulta dados) {

        if(dados.idMedico() == null) {
            return;
        }

        Consulta consulta = consultaRepository.buscarDisponibilidadeMedico(dados.idMedico(), dados.data());

        if(consulta != null) {
            throw new ValidacaoException("Horário indisponível para o médico selecionado!");
        }
    }
}
