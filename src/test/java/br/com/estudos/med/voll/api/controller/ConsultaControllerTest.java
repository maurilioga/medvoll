package br.com.estudos.med.voll.api.controller;

import br.com.estudos.med.voll.api.dto.DadosCancelaConsulta;
import br.com.estudos.med.voll.api.dto.DadosDetalhamentoConsulta;
import br.com.estudos.med.voll.api.dto.DadosMarcaConsulta;
import br.com.estudos.med.voll.api.model.*;
import br.com.estudos.med.voll.api.service.ConsultaService;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DadosMarcaConsulta> dadosConsulta;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsulta;

    @Autowired
    private JacksonTester<DadosMarcaConsulta> dadosMarcaConsulta;

    @Autowired
    private JacksonTester<DadosCancelaConsulta> dadosCancelamentoConsulta;

    @MockitoBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deve retornar 400 quando informacoes estao invalidas")
    @WithMockUser
    void testMarcarConsultaNull() throws Exception {

        var response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar 201 quando informacoes estao validas")
    @WithMockUser
    void testMarcarConsulta() throws Exception {

        Paciente paciente = new Paciente();
        paciente.setId(1L);

        Medico medico = new Medico();
        medico.setId(1L);

        DadosDetalhamentoConsulta dadosConsulta = new DadosDetalhamentoConsulta(null, 1l, 1l, LocalDateTime.now());

        when(consultaService.marcarConsulta(any())).thenReturn(dadosConsulta);

        var response = mockMvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON)
                        .content(dadosMarcaConsulta.write(
                                new DadosMarcaConsulta(1L,1L, LocalDateTime.now().plusHours(1), Especialidade.CARDIOLOGIA)
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonResponse = dadosDetalhamentoConsulta.write(
                dadosConsulta
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonResponse);
    }

    @Test
    @WithMockUser
    void testCancelarConsulta() throws Exception {

        var response = mockMvc.perform(delete("/consultas").contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCancelamentoConsulta.write(
                                new DadosCancelaConsulta(1L, MotivoCancelamentoConsulta.DESISTENCIA)
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}