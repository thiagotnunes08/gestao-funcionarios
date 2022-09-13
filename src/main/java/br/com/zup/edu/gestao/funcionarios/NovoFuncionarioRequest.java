package br.com.zup.edu.gestao.funcionarios;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovoFuncionarioRequest {

    @NotBlank
    private String nome;

    @CPF
    @NotBlank
    private String cpf;

    @NotNull
    private Cargo cargo;

    @NotNull
    @Positive
    private BigDecimal salario;

    public NovoFuncionarioRequest(String nome, String cpf, Cargo cargo, BigDecimal salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public Cargo getCargo() {
        return cargo;
    }
    public BigDecimal getSalario() {
        return salario;
    }

    @Override
    public String toString() {
        return "NovoFuncionarioRequest{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cargo=" + cargo +
                ", salario=" + salario +
                '}';
    }

    public Funcionario toModel() {
        return new Funcionario(nome, cpf, cargo, salario);
    }
}
