package br.com.zup.edu.gestao.funcionarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    public boolean existsByCpf(String cpf);
}
