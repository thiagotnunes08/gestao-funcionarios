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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NovoFuncionarioControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private FuncionarioRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    public void deveCadastrarNovoFuncionario() throws Exception {
        // cenário
        NovoFuncionarioRequest novoFuncionario = new NovoFuncionarioRequest("Alberto",
                "785.547.810-82", Cargo.GERENTE, new BigDecimal("10981.99"));

        // ação
        mockMvc.perform(POST("/api/funcionarios", novoFuncionario)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:write"))))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("**/api/funcionarios/*"))
        ;

        // validação
        assertEquals(1, repository.count(), "total de funcionarios");
    }

    @Test
    public void deveCadastrarNovoFuncionario_quandoParametrosInvalidos() throws Exception {
        // cenário
        NovoFuncionarioRequest invalido = new NovoFuncionarioRequest("", "", null, null);

        // ação
        mockMvc.perform(POST("/api/funcionarios", invalido)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:write"))))
                .andExpect(status().isBadRequest())
        ;

        // validação
        assertEquals(0, repository.count(), "total de funcionarios");
    }

    @Test
    public void deveCadastrarNovoFuncionario_quandoFuncionarioJaExistente() throws Exception {
        // cenário
        Funcionario existente = new Funcionario("Alberto",
                            "374.130.840-40", Cargo.GERENTE, new BigDecimal("10981.99"));
        repository.save(existente);

        NovoFuncionarioRequest novoFuncionario = new NovoFuncionarioRequest("outro nome",
                            existente.getCpf(), Cargo.DESENVOLVEDOR, new BigDecimal("5432.99"));

        // ação
        mockMvc.perform(POST("/api/funcionarios", novoFuncionario)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_funcionarios:write"))))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(status().reason("funcionário com CPF já existente"))
        ;

        // validação
        assertEquals(1, repository.count(), "total de funcionarios");
    }

    @Test
    @DisplayName("nao deve cadastrar novo funcionario pois token eh invalido ou inexistente")
    void test1() throws Exception {

        Funcionario funcionario = new Funcionario("thiago","12345-22",Cargo.DESENVOLVEDOR,BigDecimal.TEN);

        mockMvc.perform(POST("/api/funcionarios",funcionario.getId())).andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("nao deve cadastrar novo funcionario, pois escopo eh invalido ou inexistente")
    void test2() throws Exception{

        Funcionario funcionario = new Funcionario("THIAGO","1234-22",Cargo.DESENVOLVEDOR,BigDecimal.TEN);


        mockMvc.perform(POST("/api/funcionarios",funcionario.getId()).with(jwt())).andExpect(status().isForbidden());
    }
}