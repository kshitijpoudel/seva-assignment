package com.sevadev.javaassignment.api;

import com.sevadev.javaassignment.exception.SevaException;
import com.sevadev.javaassignment.repo.Person;
import com.sevadev.javaassignment.repo.PersonRepository;
import com.sevadev.javaassignment.dto.PersonDTO;
import com.sevadev.javaassignment.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SevaPersonController {

    final Logger logger = LoggerFactory.getLogger(SevaPersonController.class);

    @Autowired
    PersonRepository personRepository;

    @GetMapping(path = "/api/persons", produces = "application/json")
    List<PersonDTO> getAllPersons() {

        logger.info("Getting all persons...");
        List<PersonDTO> personDto = new ArrayList<>();
        personRepository.findAll().stream().forEach(person -> {
            personDto.add(new PersonDTO(person));
        });
        return personDto;
    }

    @GetMapping(path = "/api/person/{id}", produces = "application/json")
    PersonDTO getPerson(@PathVariable Long id) throws SevaException {
        logger.info("Getting person with id: " + id);
        return new PersonDTO(personRepository.findById(id)
                .orElseThrow(() -> new SevaException("Person could not found for id=" + id)));
    }

    @GetMapping(path = "/api/person", produces = "application/json")
    public List<PersonDTO> getPersons(@RequestParam(value = "fname", defaultValue = "") String firstName,
                                      @RequestParam(value = "lname", defaultValue = "") String lastName) {
        List<PersonDTO> personDto = new ArrayList<>();
        if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
            logger.info("Getting person with first name: " + firstName + " and last name: " + lastName);
            personRepository.findByFirstNameAndLastName(firstName, lastName).stream().forEach(person -> {
                personDto.add(new PersonDTO(person));
            });
        } else if (StringUtils.isNotEmpty(firstName)) {
            logger.info("Getting person with first name: " + firstName);
            personRepository.findByFirstName(firstName).stream().forEach(person -> {
                personDto.add(new PersonDTO(person));
            });
        } else if (StringUtils.isNotEmpty(lastName)) {
            logger.info("Getting person with last name: " + lastName);
            personRepository.findByLastName(lastName).stream().forEach(person -> {
                personDto.add(new PersonDTO(person));
            });
        }
        return personDto;
    }

    @PutMapping(path = "/api/persons/{id}", produces = "application/json")
    PersonDTO replacePerson(@RequestBody PersonDTO newPerson, @PathVariable Long id) {

        logger.info("Replacing person with id: " + id);
        Person persistablePerson = personRepository.findById(id).orElseGet(() -> {
            Person person = new Person();
            person.setId(id);
            return person;
        });

        persistablePerson.setFirstName(newPerson.getFirstName());
        persistablePerson.setLastName(newPerson.getLastName());
        persistablePerson.setMiddleName(newPerson.getMiddleName());
        persistablePerson.getAddress().setCity(newPerson.getAddress().getCity());
        persistablePerson.getAddress().setState(newPerson.getAddress().getState());
        persistablePerson.getAddress().setZip(newPerson.getAddress().getZip());
        persistablePerson.getAddress().setCountry(newPerson.getAddress().getCountry());
        return new PersonDTO(personRepository.save(persistablePerson));
    }

    @DeleteMapping("/api/person/{id}")
    void deletePerson(@PathVariable Long id) {
        logger.info("Deleting person with id: " + id);
        personRepository.deleteById(id);
    }
}
