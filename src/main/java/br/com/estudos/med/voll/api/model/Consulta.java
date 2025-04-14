package br.com.estudos.med.voll.api.model;

import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_CONSULTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Medico medico;

    private LocalDateTime horario;

    @Enumerated(EnumType.STRING)
    private MotivoCancelamentoConsulta motivoCancelamento;

    public Consulta(DadosMarcaConsulta dadosConsulta) {

    }
}
