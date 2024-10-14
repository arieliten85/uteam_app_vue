package com.arielFerencak.persona.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.arielFerencak.persona.model.Comic;
import com.arielFerencak.persona.service.ComicService;

import java.util.List;

@RestController
@RequestMapping("/api/comics")
public class ComicController {
    @Autowired
    private ComicService comicService;

    @GetMapping
    public List<Comic> getAllComics() {
        return comicService.findAll();
    }

    @PostMapping
    public Comic createComic(@RequestBody Comic comic) {
        return comicService.createComic(comic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComic(@PathVariable Long id) {
        comicService.deleteComic(id);
        return ResponseEntity.noContent().build();
    }
}
