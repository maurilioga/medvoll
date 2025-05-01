package br.com.estudos.med.voll.api.controller;

import br.com.estudos.med.voll.api.dto.DadosAtualizaPaciente;
import br.com.estudos.med.voll.api.dto.DadosCadastroPaciente;
import br.com.estudos.med.voll.api.dto.DadosDetalhamentoPaciente;
import br.com.estudos.med.voll.api.dto.DadosEndereco;
import br.com.estudos.med.voll.api.repository.PacienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroPaciente> dadosCadastroPaciente;

    @Autowired
    private JacksonTester<DadosDetalhamentoPaciente> dadosDetalhamentoPaciente;

    @Autowired
    private JacksonTester<DadosAtualizaPaciente> dadosAtualizaPaciente;

    @Mock
    private PacienteRepository pacienteRepository;

    @Test
    @WithMockUser
    void testCadastrarPacienteCreated() throws Exception {

        DadosEndereco dadosEndereco = new DadosEndereco("Logradouro", "Bairro", "12345678", "Cidade", "UF", "Complemento", "Numero");

        var response = mockMvc.perform(
                post("/pacientes")
                        .content(dadosCadastroPaciente.write(
                                new DadosCadastroPaciente("Nome", "email@email.com", "12345678", "12345678", dadosEndereco)).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @WithMockUser
    void testCadastrarPacienteBadRequest() throws Exception {

        var response = mockMvc.perform(
                post("/pacientes")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    void testDetalharPacienteOk() throws Exception {

        var response = mockMvc.perform(
                get("/pacientes/1")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testListarPacienteOk() throws Exception {

        var response = mockMvc.perform(
                get("/pacientes")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testAtualizarPacienteOk() throws Exception {

        DadosEndereco dadosEndereco = new DadosEndereco("Logradouro", "Bairro", "12345678", "Cidade", "UF", "Complemento", "Numero");

        var response = mockMvc.perform(
                put("/pacientes")
                        .content(dadosAtualizaPaciente.write(
                                new DadosAtualizaPaciente(1l, "Nome", "Telefone", dadosEndereco)
                        ).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @WithMockUser
    void testExcluirPacienteOk() throws Exception {

        var response = mockMvc.perform(
                delete("/pacientes/1")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}