package br.com.zup.edu.gestao.funcionarios;

import base.SpringBootIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

class DetalhaFuncionarioControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private FuncionarioRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void deveDetalharFuncionario() throws Exception {
        // cenário
        Funcionario funcionario = new Funcionario("Jordi",
                "994.300.560-26", Cargo.DESENVOLVEDOR, new BigDecimal("3.99"));
        repository.save(funcionario);

        // ação e validação
        mockMvc.perform(GET("/api/funcionarios/{id}", funcionario.getId())
                        .with(jwt()
                                .authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(funcionario.getId()))
                .andExpect(jsonPath("$.nome").value(funcionario.getNome()))
                .andExpect(jsonPath("$.cpf").value(funcionario.getCpf()))
                .andExpect(jsonPath("$.cargo").value(funcionario.getCargo().name()))
        ;
    }

    @Test
    public void naoDeveDetalharFuncionario_quandoNaoEncontrado() throws Exception {
        // cenário
        Funcionario funcionario = new Funcionario("Jordi",
                "994.300.560-26", Cargo.DESENVOLVEDOR, new BigDecimal("3.99"));
        repository.save(funcionario);

        // ação e validação
        mockMvc.perform(GET("/api/funcionarios/{id}", -9999)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:read"))))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("funcionário não encontrado"))
        ;
    }

    @Test
    @DisplayName("nao deve detalhar funcionario quando escopo eh invalido ou inexistente")
    void test1() throws Exception{
        Funcionario funcionario = new Funcionario("THIAGO","12345-33",Cargo.DESENVOLVEDOR,BigDecimal.TEN);

        mockMvc.perform(GET("/api/funcionarios/{id}",funcionario.getId()).with(jwt())).andExpect(status().isForbidden());



    }

    @Test
    @DisplayName("nao deve detalhar funcionar quando token eh invalido ou inexistente")
    void test2() throws Exception {
        Funcionario funcionario = new Funcionario("THIAGO","123456-22",Cargo.DESENVOLVEDOR,BigDecimal.TEN);

        mockMvc.perform(GET("/api/funcionarios/{id}",funcionario.getId())).andExpect(status().isUnauthorized());
    }
}