package com.mark.taco_cloud.controller;

import com.mark.taco_cloud.domain.dto.Taco;
import com.mark.taco_cloud.repository.TacoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/api/tacos",
        produces="application/json")
@CrossOrigin(origins="http://tacocloud:8080")
public class TacoController {

    private TacoRepository tacoRepo;

    public TacoController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(params="recent")
    public Flux<Taco> recentTacos() {
        return tacoRepo.findAll().take(12);
    }

    @GetMapping("/{id}")
    public Mono<Taco> getTacoById(@PathVariable("id") Long id) {
        return tacoRepo.findById(id);
    }

    @PostMapping
    public Mono<Taco> addTaco(@RequestBody Mono<Taco> taco) {
        return tacoRepo.saveAll(taco).next();
    }

}
