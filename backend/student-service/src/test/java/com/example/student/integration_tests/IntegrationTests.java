package com.example.student.integration_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.assertj.core.api.UriAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.student.a_entity.Student;
import com.example.student.b_repository.StudentRepository;

// Start an actual HTTP server listening at random port
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    
    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    // This is a thing used for testing
    // Acts as postman - sending http requests and receiving their result
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        // clear DB after each test
        studentRepository.deleteAll();
    }

    @Test
    public void getStudents_Success() throws Exception {
        // Arrange
        URI uri = new URI(baseUrl + port + "/student/all");
        studentRepository.save(new Student(null, "Bhanu", 100L)); // null so that id can be auto assigned as per GenerationType

        // Act
        ResponseEntity<Student[]> result = restTemplate.getForEntity(uri, Student[].class);
        Student[] listOfStudents = result.getBody();

        // Assert
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, listOfStudents.length);
        assertEquals("Bhanu", listOfStudents[0].getName());
        assertEquals(100L, listOfStudents[0].getMarks());
    }

    @Test
    public void getStudent_ValidSid_Sucess() throws Exception {
        // Arrange
        Student student = new Student(null, "Sammy", 76L);
        Long id = studentRepository.save(student).getId(); // currently there is no id, when you save the student, the student entity is returned which has an id so extract and use that
        URI uri = new URI(baseUrl + port + "/student/" + id);

        // Act
        ResponseEntity<Student> resultStudent = restTemplate.getForEntity(uri, Student.class);

        // Assert
        assertEquals(HttpStatus.OK, resultStudent.getStatusCode());
        assertEquals("Sammy", resultStudent.getBody().getName()); // if you specify the ? withing <> of response entity you can make use of method chaining
        assertEquals(76L, resultStudent.getBody().getMarks());

    }
}
