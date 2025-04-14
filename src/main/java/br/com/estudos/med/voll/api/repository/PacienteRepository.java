package br.com.estudos.med.voll.api.repository;

import br.com.estudos.med.voll.api.model.Paciente;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Page<Paciente> findAllByAtivoTrue(Pageable pageable);

    @Query("SELECT p.ativo FROM Paciente p WHERE p.id = :idPaciente")
    boolean findAtivoById(Long idPaciente);
}
