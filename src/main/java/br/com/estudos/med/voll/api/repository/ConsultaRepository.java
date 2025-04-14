package br.com.estudos.med.voll.api.repository;

import br.com.estudos.med.voll.api.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :idMedico AND c.horario = :data AND c.motivoCancelamento IS NULL")
    Consulta buscarDisponibilidadeMedico(Long idMedico, LocalDateTime data);

    boolean existsByPacienteIdAndHorarioBetweenAndMotivoCancelamentoIsNull(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}
