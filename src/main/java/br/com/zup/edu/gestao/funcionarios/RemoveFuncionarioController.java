package br.com.zup.edu.gestao.funcionarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RemoveFuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @Transactional
    @DeleteMapping("/api/funcionarios/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") Long id) {

        Funcionario funcionario = repository.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "funcionário não encontrado");
        });

        repository.delete(funcionario);

        return ResponseEntity
                .noContent().build();
    }
}
