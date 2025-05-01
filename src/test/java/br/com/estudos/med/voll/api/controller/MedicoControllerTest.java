package br.com.estudos.med.voll.api.controller;

import br.com.estudos.med.voll.api.dto.DadosAtualizaMedico;
import br.com.estudos.med.voll.api.dto.DadosCadastroMedico;
import br.com.estudos.med.voll.api.dto.DadosEndereco;
import br.com.estudos.med.voll.api.dto.DadosListagemMedico;
import br.com.estudos.med.voll.api.model.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedico;

    @Autowired
    private JacksonTester<DadosAtualizaMedico> dadosAtualizaMedico;

    @Autowired
    private JacksonTester<DadosListagemMedico> dadosListagemMedico;

    @Test
    @DisplayName("Deve retornar 201 ao tentar cadastrar novo caso os dados inseridos sejam válidos")
    @WithMockUser
    void testCadastrarMedico() throws Exception {

        DadosEndereco dadosEndereco = new DadosEndereco("logradouro", "bairro","12345678", "cidade", "uf", "complemento","123");

        var response = mockMvc.perform(post("/medicos").contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroMedico.write(
                        new DadosCadastroMedico("nome", "email@email.com", "12345", "123456789", Especialidade.CARDIOLOGIA, dadosEndereco)
                ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar cadastrar novo caso os dados inseridos sejam inválidos")
    @WithMockUser
    void testCadastrarMedicoNull() throws Exception {

        var response = mockMvc.perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar 200 ao tentar listar médico caso os dados inseridos sejam válidos")
    @WithMockUser
    void testListarMedico() throws Exception {

        var response = mockMvc.perform(get("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve retornar 200 ao tentar atualizar cadastro do médico caso os dados inseridos sejam válidos")
    @WithMockUser
    void testAtualizarMedico() throws Exception {

        var response = mockMvc.perform(put("/medicos").contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAtualizaMedico.write(
                                new DadosAtualizaMedico(1L, "nome","email@email.com","12345678")
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Deve retornar 400 ao tentar atualizar cadastro do médico caso os dados inseridos sejam inválidos")
    @WithMockUser
    void testAtualizarMedicoNull() throws Exception {

        var response = mockMvc.perform(put("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar 204 ao tentar inativar médico caso os dados inseridos sejam válidos")
    @WithMockUser
    void testInativarMedico() throws Exception {

        var response = mockMvc.perform(delete("/medicos/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

//    @Test
//    @DisplayName("Deve retornar 400 ao tentar inativar médico caso os dados inseridos sejam inválidos")
//    @WithMockUser
//    void testInativarMedicoNull() throws Exception {
//
//        var response = mockMvc.perform(delete("/medicos/"))
//                .andReturn().getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
//    }

    @Test
    @DisplayName("Deve retornar 200 ao tentar detalhar médico caso os dados inseridos sejam válidos")
    @WithMockUser
    void testDetalharMedico() throws Exception {

        var response = mockMvc.perform(get("/medicos/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}