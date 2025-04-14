package br.com.estudos.med.voll.api.validation;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.exception.ValidacaoException;
import br.com.estudos.med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidaMedicoAtivoAgendamento implements ValidadorAgendamento{

    @Autowired
    private MedicoRepository medicoRepository;

    public void validar(DadosMarcaConsulta dados) {

        if(dados.idMedico() == null) {
            return;
        }

        if (!medicoRepository.findAtivoById(dados.idMedico())) {
            throw new ValidacaoException("Médico inativo!");
        }
    }
}
