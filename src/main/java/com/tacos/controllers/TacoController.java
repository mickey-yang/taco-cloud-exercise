package com.tacos.controllers;

import com.tacos.domain.Taco;
import com.tacos.repository.TacoRepositroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/tacos",
                produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {

    private TacoRepositroy tacoRepositroy;

    public TacoController(TacoRepositroy tacoRepositroy) {
        this.tacoRepositroy = tacoRepositroy;
    }

    @GetMapping(params = "recent")
    public Iterable<Taco> recentTacos() {

        PageRequest pageRequest = PageRequest.of(
                0, 12, Sort.by("createdAt").descending()
        );

        return tacoRepositroy.findAll(pageRequest).getContent();
    }

}
