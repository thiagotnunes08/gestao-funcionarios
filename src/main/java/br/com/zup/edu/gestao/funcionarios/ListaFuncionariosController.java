package br.com.zup.edu.gestao.funcionarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ListaFuncionariosController {

    @Autowired
    private FuncionarioRepository repository;

    @GetMapping("/api/funcionarios")
    public ResponseEntity<?> lista() {

        List<FuncionarioResponse> funcionarios = repository.findAll(Sort.by("nome")).stream().map(funcionario -> {
            return new FuncionarioResponse(funcionario);
        }).collect(toList());

        return ResponseEntity
                .ok(funcionarios);
    }
}
