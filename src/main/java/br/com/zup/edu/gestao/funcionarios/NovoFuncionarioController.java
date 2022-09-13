package br.com.zup.edu.gestao.funcionarios;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class NovoFuncionarioController {

    @Autowired
    private FuncionarioRepository repository;

    @Transactional
    @PostMapping("/api/funcionarios")
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoFuncionarioRequest request, UriComponentsBuilder uriBuilder) {

        if (repository.existsByCpf(request.getCpf())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "funcionário com CPF já existente");
        }

        Funcionario funcionario = request.toModel();
        repository.save(funcionario);

        URI location = uriBuilder
                .path("/api/funcionarios/{id}")
                .buildAndExpand(funcionario.getId()).toUri();

        return ResponseEntity
                .created(location).build();
    }

    /**
     * Exception Handler para Unique Constraint de funcionário
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleUniqueConstraintError(ConstraintViolationException exception) {
        Map<String, Object> body = Map.of(
                "message", "funcionário já existente (db)",
                "timestamp", LocalDateTime.now()
        );
        return ResponseEntity
                .unprocessableEntity().body(body);
    }
}
