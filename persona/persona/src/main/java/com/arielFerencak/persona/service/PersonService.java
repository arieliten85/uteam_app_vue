package com.arielFerencak.persona.service;

import com.arielFerencak.persona.model.Comic;
import com.arielFerencak.persona.model.Person;
import com.arielFerencak.persona.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Optional<Person> findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person update(Long id, Person personDetails) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            if (personDetails.getFirstName() != null) {
                person.setFirstName(personDetails.getFirstName());
            }
            if (personDetails.getLastName() != null) {
                person.setLastName(personDetails.getLastName());
            }
            if (personDetails.getBirthdate() != null) {
                person.setBirthdate(personDetails.getBirthdate());
            }
            person.setHasInsurance(personDetails.isHasInsurance());
            return personRepository.save(person);
        }
        return null; // o lanzar una excepción
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public List<Comic> getComicsByPersonId(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.map(Person::getFavouriteComics).orElse(null);
    }

    public Person addComicToPerson(Long personId, Comic comic) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.getFavouriteComics().add(comic);
            return personRepository.save(person);
        }
        return null; // o lanzar una excepción
    }

    public Person removeComicFromPerson(Long personId, Long comicId) {
        Optional<Person> optionalPerson = personRepository.findById(personId);
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            person.getFavouriteComics().removeIf(comic -> comic.getId().equals(comicId));
            return personRepository.save(person);
        }
        return null; // o lanzar una excepción
    }
}
