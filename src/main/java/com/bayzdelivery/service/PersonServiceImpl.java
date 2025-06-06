
package com.bayzdelivery.service;

import com.bayzdelivery.exceptions.UserConflictException;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> getAll() {
        return (List<Person>) personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        Optional<Person> existing = personRepository.findByEmail(person.getEmail());
        if (existing.isPresent()) {
            throw new UserConflictException("User is already registered. Please try logging through UserId and Password");
        }
        return personRepository.save(person);
    }

    @Override
    public Person findById(Long personId) {
        return personRepository.findById(personId).orElse(null);
    }

    public void delete(Long personId){
        personRepository.deleteById(personId);
    }
}
