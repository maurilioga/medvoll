package br.com.estudos.med.voll.api.model;

import br.com.estudos.med.voll.api.dto.DadosAtualizaMedico;
import br.com.estudos.med.voll.api.dto.DadosCadastroMedico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_MEDICO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String crm;

    private String telefone;

    private Boolean ativo;

    @OneToMany(mappedBy = "medico", fetch = FetchType.EAGER)
    private List<Consulta> consulta = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    public Medico(DadosCadastroMedico dados) {

        this.nome = dados.nome();
        this.email = dados.email();
        this.crm = dados.crm();
        this.telefone = dados.telefone();
        this.ativo = true;
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarDados(DadosAtualizaMedico dados) {

        if (dados.nome() != null) {
            this.nome = dados.nome();
        }

        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }

        if (dados.email() != null) {
            this.email = dados.email();
        }
    }

    public void excluir() {

        this.ativo = false;
    }
}
