package com.example.student.test_repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.example.student.a_entity.Student;
import com.example.student.c_service.StudentServiceImpl;
import com.example.student.d_controller.*;
import com.example.student.e_exceptions.StudentAlreadyExistsException;
import com.example.student.e_exceptions.StudentNotFoundException;

@ExtendWith(MockitoExtension.class)
public class StudentControllerUnitTest {
    @Mock
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private Student mockStudent;

    @BeforeEach
    void setUp() {
        mockStudent = new Student(1L, "Bhanu");
    }

    @Test
    void getAllStudents_IfEmpty_ReturnOk() {
        // Arrange
        when(studentService.listAllStudents()).thenReturn(new ArrayList<Student>());
        // Act
        ResponseEntity<?> response = studentController.getAllStudents();
        // Assert
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Table is empty - Please add Student Details");
        //Verify
        verify(studentService, times(1)).listAllStudents();
    }

    @Test
    void getAllStudents_NotEmpty_ReturnOk() {
        //Arrange
        List<Student> listOfStudents = new ArrayList<>();
        listOfStudents.add(mockStudent);
        when(studentService.listAllStudents()).thenReturn(listOfStudents);
        // Act
        ResponseEntity<?> response = studentController.getAllStudents();
        // Assert
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(listOfStudents, response.getBody()); // that's why it's good to have overriden the equals method
        List<Student> responseList = (List<Student>) response.getBody();
        assertEquals(listOfStudents.size(), responseList.size());
        //Verify
        verify(studentService, times(1)).listAllStudents();
    }

    @Test
    void getStudent_InvalidId_ReturnNotFound() {
        // Arrange
        when(studentService.findStudentById(2L)).thenThrow(new StudentNotFoundException(2L));
        // Act
        ResponseEntity<?> response = studentController.getStudent(2L);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // reverse order (expected, actual)
        // String responseMsg = (String) response.getBody();
        // assertEquals("Could not find Student with Id = 2", responseMsg);
        assertTrue(response.getBody() instanceof String);
        assertEquals("Could not find Student with Id = 2", response.getBody());
        // Verify
        verify(studentService, times(1)).findStudentById(2L);
    }

    @Test
    void getStudent_ValidId_ReturnOk() {
        // Arrange
        when(studentService.findStudentById(1L)).thenReturn(mockStudent);
        // Act
        ResponseEntity<?> response = studentController.getStudent(1L);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStudent, response.getBody());
        // Verify
        verify(studentService, times(1)).findStudentById(1L);
    }

    @Test
    void postStudent_NewStudent_ReturnOk() {
        // Arrange
        // when(studentService.addStudent(mockStudent)).thenReturn(mockStudent);
        // Act
        ResponseEntity<?> response = studentController.postStudent(mockStudent);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student successfully added to database", response.getBody());
        // Verify
        verify(studentService, times(1)).addStudent(mockStudent);
    }

    @Test
    void postStudent_DuplicateEntry_ReturnBadRequest() {
        // Arrange
        doThrow(new StudentAlreadyExistsException(mockStudent.getStudentName())).when(studentService).addStudent(mockStudent);
        // Act
        ResponseEntity<?> response = studentController.postStudent(mockStudent);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Student with name = " + mockStudent.getStudentName() + " already exists.", response.getBody());
        // Verify
        verify(studentService, times(1)).addStudent(mockStudent);
    }

    @Test
    void putStudent_ValidId_ReturnOk() {
        //Arrange
        Student updatedStudent = new Student(1L, "Bob"); // student with new name
        // MarksDTO marks = new MarksDTO(100L);

        /* This method will not update the marks of mockStudent
         * You have to do that manually or create a new entity like above
         * The point of testing is not to test the internal logic of service/controller
         * It's just to check whether the method is being called correctly ie returning the correct values etc
         */
        when(studentService.updateStudent(1L, updatedStudent)).thenReturn(updatedStudent); 
        
        // Act
        ResponseEntity<?> response = studentController.putStudent(1L, updatedStudent);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Student responseStudent = (Student) response.getBody();
        assertEquals(updatedStudent, responseStudent);
        assertEquals("Bob", responseStudent.getStudentName());
        assertEquals(1L, responseStudent.getStudentId());
        // Verify
        verify(studentService, times(1)).updateStudent(1L, updatedStudent);
        // verify(studentService, times(1)).findStudentById(1L); // not invoked because not mocked + it's nested inside updateStudent
    }

    @Test
    void putStudent_InvalidId_ReturnBadRequest() {
        /* Testing is not for internal logic
         * Test cases are not going to employ method chaining and shit unless you specify manually
         * This is why verify for findByStudentId() doesn't work
         * because you've only specified behavior for updateStudent
         * 
         * when() only sets up return values and doesn't actually call methods
         */

        /* The entire problem here is:
         *      If you mock updateStudent() - you don't call findStudentById() at all so the verify() for that doesn't work
         *      If you mock findStudent() - you don't mock updateStudent()
         * I believe that the error here is because of the nesting of findStudent() inside updateStudent()
         */
        // Arrange
        Student newStudent = new Student(2L, "Billabong");
        when(studentService.updateStudent(2L, newStudent)).thenThrow(new StudentNotFoundException(2L));
        // when(studentService.findStudentById(2L)).thenThrow(new StudentNotFoundException(2L));
        // Act
        ResponseEntity<?> response = studentController.putStudent(2L, newStudent);
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Could not find Student with Id = 2", response.getBody());
        // Verify
        // verify(studentService, never()).findStudentById(2L); // same thing not invoked
        verify(studentService, times(1)).updateStudent(2L, newStudent);
    }

    @Test
    void deleteStudent_InvalidId_ReturnBadRequest() {
        // Arrange
        doThrow(new StudentNotFoundException(2L)).when(studentService).removeStudent(2L);
        // Act
        ResponseEntity<?> response = studentController.deleteStudent(2L);
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Could not find Student with Id = 2", response.getBody());
        // Verify
        verify(studentService, times(1)).removeStudent(2L);
    }

    @Test
    void deleteStudent_ValidId_ReturnOk() {
        // Arrange
        // Act
        ResponseEntity<?> response = studentController.deleteStudent(1L);
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully deleted student with Id: 1", response.getBody());
        // Verify
        verify(studentService, times(1)).removeStudent(1L);
    }
}
