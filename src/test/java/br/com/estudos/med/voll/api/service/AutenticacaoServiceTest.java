package br.com.estudos.med.voll.api.service;

import br.com.estudos.med.voll.api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UserDetails userDetails;

    @Test
    void testLoadUserByUsername() {

        given(usuarioRepository.findByLogin(anyString())).willReturn(userDetails);

        assertNotNull(autenticacaoService.loadUserByUsername(anyString()));
    }
}