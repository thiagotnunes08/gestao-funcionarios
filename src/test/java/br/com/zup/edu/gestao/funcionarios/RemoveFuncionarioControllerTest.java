package br.com.zup.edu.gestao.funcionarios;

import base.SpringBootIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RemoveFuncionarioControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private FuncionarioRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void deveRemoverAutorExistente() throws Exception {
        // cenário
        Funcionario funcionario = repository.save(new Funcionario("Alberto",
                "785.547.810-82", Cargo.GERENTE, new BigDecimal("10981.99")));
        repository.save(funcionario);

        // ação
        mockMvc.perform(DELETE("/api/funcionarios/{id}", funcionario.getId()).
                        with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:write"))))
                .andExpect(status().isNoContent())
        ;

        // validação
        assertEquals(0, repository.count(), "total de funcionarios");
    }

    @Test
    public void deveRemoverAutorExistente_quandoNaoEncontrado() throws Exception {
        // cenário
        Funcionario funcionario = new Funcionario("Alberto",
                "785.547.810-82", Cargo.GERENTE, new BigDecimal("10981.99"));
        repository.save(funcionario);

        // ação
        mockMvc.perform(DELETE("/api/funcionarios/{id}", -9999)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:write"))))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("funcionário não encontrado"))
        ;

        // validação
        assertEquals(1, repository.count(), "total de funcionarios");
    }

    @Test
    @DisplayName("nao deve remover, pois funcionario nao possui token valido ou existente")
    void test1() throws Exception {
        Funcionario funcionario = new Funcionario("THIAGO","123456-33",Cargo.DESENVOLVEDOR,BigDecimal.TEN);

        mockMvc.perform(DELETE("/api/funcionarios/{id}",funcionario.getId()).with(jwt())).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("nao deve remover, pois funcionario nao tem escopo valido ou existente")
    void name() throws Exception{
        Funcionario funcionario = new Funcionario("thaina","123456-44",Cargo.GERENTE,BigDecimal.TEN);

        mockMvc.perform(DELETE("/api/funcionarios/{id}",funcionario.getId())).andExpect(status().isUnauthorized());

    }
}