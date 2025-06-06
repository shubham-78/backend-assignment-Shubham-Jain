package com.bayzdelivery.controller;

import java.util.List;

import com.bayzdelivery.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bayzdelivery.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  PersonService personService;

  @PostMapping
  public ResponseEntity<Person> register(@RequestBody Person p) {
    return ResponseEntity.ok(personService.save(p));
  }

  @GetMapping
  public ResponseEntity<List<Person>> getAllPersons() {
    return ResponseEntity.ok(personService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
    Person person = personService.findById(id);
    return (person != null) ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePersonById(@PathVariable Long id){
    Person person = personService.findById(id);
    if(person==null){
      return ResponseEntity.notFound().build();
    }
    personService.delete(id);
    return ResponseEntity.noContent().build();
  }
}