package com.arielFerencak.persona.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arielFerencak.persona.model.Person;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstName(String firstName);
}
