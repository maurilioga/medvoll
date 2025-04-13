package br.com.estudos.med.voll.api.model;

import br.com.estudos.med.voll.api.dto.DadosAtualizaPaciente;
import br.com.estudos.med.voll.api.dto.DadosCadastroPaciente;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_PACIENTE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cpf;

    private Endereco endereco;

    private Boolean ativo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Consulta> consulta = new ArrayList<>();

    public Paciente(DadosCadastroPaciente dadosPaciente) {

        this.nome = dadosPaciente.nome();
        this.email = dadosPaciente.email();
        this.telefone = dadosPaciente.telefone();
        this.cpf = dadosPaciente.cpf();
        this.endereco = new Endereco(dadosPaciente.endereco());
        this.ativo = true;
    }

    public void atualizaDados(DadosAtualizaPaciente dadosPaciente)  {

        if(dadosPaciente.nome() != null) {
            this.nome = dadosPaciente.nome();
        }

        if(dadosPaciente.telefone() != null) {
            this.nome = dadosPaciente.telefone();
        }

        if(dadosPaciente.endereco() != null) {
            this.endereco = endereco.atualizaDados(dadosPaciente.endereco());
        }
    }

    public void exclui() {

        this.ativo = false;
    }
}
