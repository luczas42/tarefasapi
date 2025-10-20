package com.example.tarefasapi.controller;

import com.example.tarefasapi.model.Tarefa;
import com.example.tarefasapi.repository.TarefaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private TarefaRepository repository;

    TarefaController(TarefaRepository tarefaRepository) {
        this.repository = tarefaRepository;
    }

    @GetMapping
    public List<Tarefa> listarTarefas() {
        return repository.findAll();
    }

    @PostMapping
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefa) {
        return repository.findById(id)
                .map(record -> {
                    record.setNome(tarefa.getNome());
                    record.setDataEntrega(tarefa.getDataEntrega());
                    record.setResponsavel(tarefa.getResponsavel());
                    Tarefa updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Tarefa>> deletarTarefa(@PathVariable Long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    List<Tarefa> tarefasRestantes = repository.findAll();
                    return ResponseEntity.ok().body(tarefasRestantes);
                }).orElse(ResponseEntity.notFound().build());
    }
}