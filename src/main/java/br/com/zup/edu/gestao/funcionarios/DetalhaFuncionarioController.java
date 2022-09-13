package br.com.zup.edu.gestao.funcionarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DetalhaFuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @GetMapping("/api/funcionarios/{id}")
    public ResponseEntity<?> detalha(@PathVariable("id") Long id) {

        Funcionario funcionario = repository.findById(id).orElseThrow(() -> {
           return new ResponseStatusException(HttpStatus.NOT_FOUND, "funcionário não encontrado");
        });

        return ResponseEntity
                .ok(new DetalhesDoFuncionarioResponse(funcionario));
    }
}
