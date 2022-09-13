package br.com.zup.edu.gestao.funcionarios;

public class DetalhesDoFuncionarioResponse {

    private final Long id;
    private final String nome;
    private final String cpf;
    private final Cargo cargo;

    public DetalhesDoFuncionarioResponse(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.cargo = funcionario.getCargo();
    }

    public Long getId() {
        return id;
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
}
