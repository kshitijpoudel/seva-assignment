package com.sevadev.javaassignment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByLastName(String lastName);
    List<Person> findByFirstName(String firstName);
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);
}
