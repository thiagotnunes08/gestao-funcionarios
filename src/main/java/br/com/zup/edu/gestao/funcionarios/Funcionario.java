package br.com.zup.edu.gestao.funcionarios;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    private BigDecimal salario;

    @Deprecated
    public Funcionario(){}

    public Funcionario(String nome, String cpf, Cargo cargo, BigDecimal salario) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getCpf() {
        return cpf;
    }
}
