package com.arielFerencak.persona.service;

import org.springframework.stereotype.Service;

import com.arielFerencak.persona.model.Comic;

import com.arielFerencak.persona.repository.ComicRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ComicService {
    @Autowired
    private ComicRepository comicRepository;

    public List<Comic> findAll() {
        return comicRepository.findAll();
    }

    public Comic createComic(Comic comic) {
        return comicRepository.save(comic);
    }

    public void deleteComic(Long id) {
        comicRepository.deleteById(id);
    }
}
