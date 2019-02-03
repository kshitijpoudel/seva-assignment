package com.sevadev.javaassignment.repo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PersonRepository personRepositoryTest;

    @Before
    public void initializeDatabase() {
        /*
        insert one data before each test case
         */
        Person firstPerson = new Person();
        firstPerson.setFirstName("Test First Name");
        firstPerson.setLastName("Test Last Name");
        firstPerson.getAddress().setCountry("Test Country");
        entityManager.persist(firstPerson);
        entityManager.flush();
    }

    @Test
    public void whenFindAll() {
        /*
        given
         */

        int personsSizeBefore = personRepositoryTest.findAll().size();

        Person firstPerson = new Person();
        firstPerson.setFirstName("Kshitij");
        firstPerson.setLastName("Poudel");
        firstPerson.getAddress().setCountry("Nepal");
        entityManager.persist(firstPerson);
        entityManager.flush();

        Person secondPerson = new Person();
        secondPerson.setFirstName("Pawan");
        secondPerson.setMiddleName("Krishna");
        secondPerson.setLastName("Shrestha");
        secondPerson.getAddress().setCity("Pokhara");
        entityManager.persist(secondPerson);
        entityManager.flush();

        /*
        when
         */
        List<Person> persons = personRepositoryTest.findAll();

        /*
        then
         */
        assertThat(persons.size()).isEqualTo(personsSizeBefore + 2);
        assertThat(persons.get(personsSizeBefore)).isEqualTo(firstPerson);
        assertThat(persons.get(personsSizeBefore + 1).getLastName()).isEqualTo("Shrestha");
    }

    @Test
    public void whenFindById() {
        /*
        given
         */
        Person person = new Person();
        person.setFirstName("Kshitij");
        person.setLastName("Poudel");
        person.getAddress().setCountry("Nepal");
        entityManager.persist(person);
        entityManager.flush();

        //when
        Person testPerson = personRepositoryTest.findById(person.getId()).orElse(null);

        //then
        assertThat(person.getAddress().getCountry()).isEqualTo(testPerson.getAddress().getCountry());
    }

    @Test
    public void whenFindByFirstNameAndLastName() {
        /*
        given
         */
        Person person = new Person();
        person.setFirstName("Kshitij");
        person.setLastName("Poudel");
        person.getAddress().setCountry("Nepal");
        entityManager.persist(person);
        entityManager.flush();

        //when
        personRepositoryTest.findByFirstNameAndLastName("Kshitij", "Poudel").forEach(testPerson -> {
            //then
            assertThat(testPerson.getFirstName()).isEqualTo("Kshitij");
            assertThat(testPerson.getLastName()).isEqualTo("Poudel");
        });

    }

    @Test
    public void whenFindByFirstName() {
        /*
        given
         */
        Person person = new Person();
        person.setFirstName("Kshitij");
        person.setLastName("Poudel");
        person.getAddress().setCountry("Nepal");
        entityManager.persist(person);
        entityManager.flush();

        //when
        personRepositoryTest.findByFirstName("Kshitij").forEach(testPerson -> {
            //then
            assertThat(testPerson.getFirstName()).isEqualTo("Kshitij");
        });

    }

    @Test
    public void whenFindByLastName() {
        /*
        given
         */
        Person person = new Person();
        person.setFirstName("Kshitij");
        person.setLastName("Poudel");
        person.getAddress().setCountry("Nepal");
        entityManager.persist(person);
        entityManager.flush();

        //when
        personRepositoryTest.findByLastName("Poudel").forEach(testPerson -> {
            //then
            assertThat(testPerson.getLastName()).isEqualTo("Poudel");
        });

    }

}