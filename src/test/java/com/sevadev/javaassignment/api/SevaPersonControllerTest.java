package com.sevadev.javaassignment.api;

import com.sevadev.javaassignment.dto.PersonDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(SevaPersonController.class)
public class SevaPersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SevaPersonController personController;

    @Test
    public void getAllPersons() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setFirstName("Kshitij");

        List<PersonDTO> allPerson = Collections.singletonList(person);

        given(personController.getAllPersons()).willReturn(allPerson);

        mvc.perform(get("http://localhost:8080/api/persons")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(person.getFirstName())));
    }

    @Test
    public void getPerson() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setLastName("Poudel");
        Long testId = 15L;

        given(personController.getPerson(testId)).willReturn(person);

        mvc.perform(get("http://localhost:8080/api/person/15")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("lastName", is(person.getLastName())));

    }

    @Test
    public void getPersons() throws Exception {
        PersonDTO person = new PersonDTO();
        person.setFirstName("Kshitij");

        List<PersonDTO> thisPerson = Collections.singletonList(person);

        given(personController.getPersons("Kshitij", "")).willReturn(thisPerson);

        mvc.perform(get("http://localhost:8080/api/person?fname=Kshitij")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(person.getFirstName())));

        given(personController.getPersons("", "Poudel")).willReturn(Collections.singletonList(new PersonDTO()));
        mvc.perform(get("http://localhost:8080/api/person?fname=Poudel")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}