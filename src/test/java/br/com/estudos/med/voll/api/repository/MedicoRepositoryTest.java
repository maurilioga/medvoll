package br.com.estudos.med.voll.api.repository;

import br.com.estudos.med.voll.api.dto.DadosCadastroMedico;
import br.com.estudos.med.voll.api.dto.DadosCadastroPaciente;
import br.com.estudos.med.voll.api.dto.DadosEndereco;
import br.com.estudos.med.voll.api.model.Consulta;
import br.com.estudos.med.voll.api.model.Especialidade;
import br.com.estudos.med.voll.api.model.Medico;
import br.com.estudos.med.voll.api.model.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;


    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, paciente, medico, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

    @Test
    @DisplayName("Deve devolver null quando o unico medico cadastrado nao esta disponivel na data")
    void testEscolherMedicoAleatorioPorData() {

        LocalDateTime data = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        Paciente paciente = cadastrarPaciente("Paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, data);

        Medico medicoLivre = medicoRepository.escolherMedicoAleatorioPorData(Especialidade.CARDIOLOGIA, data);
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deve devolver medico cadastrado quando estiver disponivel na data")
    void testEscolherMedicoAleatorioPorDataDisponivel() {

        LocalDateTime data = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        Medico medico = cadastrarMedico("Medico", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        Medico medicoLivre = medicoRepository.escolherMedicoAleatorioPorData(Especialidade.CARDIOLOGIA, data);
        assertThat(medicoLivre).isEqualTo(medico);
    }
}