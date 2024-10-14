package com.arielFerencak.persona.controller;

import com.arielFerencak.persona.model.Comic;
import com.arielFerencak.persona.model.Person;
import com.arielFerencak.persona.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")

public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.findAll();
        persons.sort((p1, p2) -> {
            int lastNameComparison = p1.getLastName().compareTo(p2.getLastName());
            return lastNameComparison != 0 ? lastNameComparison : p1.getFirstName().compareTo(p2.getFirstName());
        });
        return ResponseEntity.ok(persons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return personService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name")
    public ResponseEntity<Person> getPersonByFirstName(@RequestParam String firstName) {
        return personService.findByFirstName(firstName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://http://localhost:5173/")

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {

        Person createdPerson = personService.save(person);
        return ResponseEntity.status(201).body(createdPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Person updatedPerson = personService.update(id, personDetails);
        return updatedPerson != null ? ResponseEntity.ok(updatedPerson) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/comics")
    public ResponseEntity<List<Comic>> getComicsByPersonId(@PathVariable Long id) {
        List<Comic> comics = personService.getComicsByPersonId(id);
        return ResponseEntity.ok(comics);
    }

    @PostMapping("/{id}/comics")
    public ResponseEntity<Person> addComicToPerson(@PathVariable Long id, @RequestBody Comic comic) {
        Person updatedPerson = personService.addComicToPerson(id, comic);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/{id}/comics/{comicId}")
    public ResponseEntity<Person> removeComicFromPerson(@PathVariable Long id, @PathVariable Long comicId) {
        Person updatedPerson = personService.removeComicFromPerson(id, comicId);
        return ResponseEntity.ok(updatedPerson);
    }
}
