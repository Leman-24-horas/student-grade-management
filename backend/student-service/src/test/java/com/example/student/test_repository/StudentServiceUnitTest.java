package com.example.student.test_repository;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.student.b_repository.StudentRepository;
import com.example.student.c_service.StudentServiceImpl;
import com.example.student.e_exceptions.StudentAlreadyExistsException;
import com.example.student.e_exceptions.StudentNotFoundException;
import com.example.student.a_entity.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceUnitTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks // Mocks cannot be injected into interfaces
    private StudentServiceImpl studentService; // hence StudentServiceImpl used instead of StudentService

    private Student mockStudent;

    @BeforeEach
    void setUp() {
        mockStudent = new Student(1L, "Bhanu Pratap", 88L); // so that don't need to create this obj again and again
    
    }

    @Test
    void listAllStudents_NoStudentAddedYet_ReturnEmptyList() {
        // Arrange - Mock repo to return an empty list
        // this is also supposed to take the mocked object as its parameter
        // when(studentService.listAllStudents()).thenReturn(new ArrayList<Student>()); // is it supposed to be like this?
        when(studentRepository.findAll()).thenReturn(new ArrayList<Student>());

        // Act - Call the actual service method
        List<Student> resultList = studentService.listAllStudents();

        // Assert - Ensure returned list is empty. This is for the actual service method too
        assertEquals(resultList.size(), 0);

        // Verify that findAll() was called exactly once
        // *verify(mockedObj).someMethod()
        // verify(studentService).listAllStudents();
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void listAllStudents_StudentsAdded_ReturnNonEmptyArrayList() {
        // Arrange 
        List<Student> expectedList = new ArrayList<>();
        Student s1 = new Student(1L, "Ramu", 76L);
        Student s2 = new Student(2L, "Lalu", 81L);
        Student s3 = new Student(3L, "Virat", 100L);

        expectedList.add(s1);
        expectedList.add(s2);
        expectedList.add(s3);

        when(studentRepository.findAll()).thenReturn(expectedList);

        // Act 
        List<Student> actualList = studentService.listAllStudents();

        // Assert
        assertNotNull(actualList);
        assertEquals(3, actualList.size());
        assertEquals(expectedList, actualList, "Lists should match"); 

        // Verify
        verify(studentRepository, times(1)).findAll();

        // Just a little extra
        // List<Student> list1 = new ArrayList<>();
        // list1.add(new Student(1L, "Ramu", 76L));
        // list1.add(new Student(2L, "Lalu", 81L));

        // List<Student> list2 = new ArrayList<>();
        // list2.add(new Student(1L, "Ramu", 76L));
        // list2.add(new Student(2L, "Lalu", 81L));

        // assertEquals(list1, list2);
    }

    @Test
    void findStudentById_InvalidId_ReturnSNFException() {
        // Arrange
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        // Student expected = studentService.findStudentById(1L); this throws SNF exception
        // studentRepository.findById(id).orElse(null) -> this returns null
        // studentService.findStudentById(1L) -> this doesn't, it only returns exception

        // Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.findStudentById(2L)); // There is no student with sid = 2

        // Verify
        verify(studentRepository, times(1)).findById(2L);
    }

    @Test
    void findStudentById_ValidId_ReturnStudent() {
        // Arrange
        // Student mockStudent = new Student(1L, "Bhanu", 88L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent)); //findById is of return type Optional

        // Act
        Student actualStudent = studentService.findStudentById(1L);

        // Assert
        assertNotNull(actualStudent);
        assertEquals(mockStudent, actualStudent);

        // Verify
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void addStudent_Valid_ReturnStudent() {
        // Arrange
        // Student mockStudent = new Student(1L, "Bhanu", 88L);
        when(studentRepository.save(mockStudent)).thenReturn(mockStudent);

        // Act
        // Student actualStudent = studentService.addStudent(mockStudent);
        studentService.addStudent(mockStudent);

        // Assert
        // assertNotNull(actualStudent);
        // assertEquals(mockStudent, actualStudent);

        // Verify
        verify(studentRepository, times(1)).save(mockStudent);
    }

    @Test
    void addStudent_NameNull_ReturnIllegalArgException() {
        // Arrange
        Student illegalStudent = new Student(2L, null, 86L);
        // when(studentRepository.save(illegalStudent)).thenThrow(IllegalArgumentException.class); // this is wrong 
                                                                                                   // for methods with non-void return type use .thenThrow()
        // for methods of void return type
        doThrow(new DataIntegrityViolationException("Name cannot be null")).when(studentRepository).save(illegalStudent);  

        // Assert
        assertThrows(IllegalArgumentException.class, () -> studentService.addStudent(illegalStudent));


        // Verify
        verify(studentRepository, times(1)).save(illegalStudent); // the save method shouldn't be invoked at all
    }
    
    @Test
    void addStudent_DuplicateEntry_ReturnStudentAlrExistsException() {
        /* Assume student already exists in database
         * So when you try to save the student again throw StuAlrExistsExcep
         */
        // Arrange
        // doThrow() is used when a method directly throws an exception
        // because we used things like "not null" and "unique" for student entity - studentRepo.save() can directly throw dataIntegrityViolation excep
        doThrow(new DataIntegrityViolationException("Duplicate entry")).when(studentRepository).save(mockStudent);  

        // Assert
        assertThrows(StudentAlreadyExistsException.class, () -> studentService.addStudent(mockStudent));


        // Verify
        verify(studentRepository, times(1)).save(mockStudent); // the save method shouldn't be invoked at all
    }
    
    @Test
    void updateStudent_InvalidStudent_ReturnSNFException() {
        // Assume student with id = 1L doesn't exist
        // Arrange
        // when(studentRepository.findById(1L)).thenThrow(StudentNotFoundException.class); this is wrong when student doesn't exist findBy return Optional.empty() - it doesn't throw exception straight away
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        // Student actualStudent = studentService.updateStudent(2L, 0L); // if student doesn't exist then updateStudent() will throw an exception
                                                                      // actualStudent will never be assigned any value because of the exception and hence it will not be null


        // Assert
        // assertNull(actualStudent);
        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(2L, 0L));

        // Verify
        verify(studentRepository, times(1)).findById(2L); 
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void updateStudent_ValidStudent_ReturnUpdatedStudent() {
        // Arrange
        // Long marks = 100L;
        Student originalStudent = new Student(mockStudent.getId(), mockStudent.getName(), mockStudent.getMarks());
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));
        // Student originalStudent = mockStudent; // this doesn't do shit
                                               // you are still referencing the same object so even though marks are changed later, that change is reflected here too hence orignal student = updated student 
        
        // mockStudent.setMarks(marks); // no need as it will be taken care by the updateStudent() method
        // when(studentRepository.save(mockStudent)).thenReturn(mockStudent); // no need for this because you are not saving a new object - you're just updating marks
        // mock save only when saving new objects

        // Act
        Student updatedStudent = studentService.updateStudent(1L, 100L);

        // Assert
        assertNotNull(updatedStudent);
        assertEquals(100L, updatedStudent.getMarks());

        assertNotEquals(originalStudent, updatedStudent);
        assertEquals(originalStudent.getId(), updatedStudent.getId());
        assertEquals(originalStudent.getName(), updatedStudent.getName());
        assertNotEquals(originalStudent.getMarks(), updatedStudent.getMarks());

        // Verify
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(mockStudent);

        /* What did I learn from writing this test case?
         * 1) Test case logic should not be a repetition of previous code logic i.e. updateStudent() logic in studentService
         *      No need: setMarks() or save() etc
         * 2) We just need the student hence only findById() is mocked so that there is mocked Student entity in the mocked repository which can be found and upated accordingly
         *      So see if you can pee, I just need to give you water
         * 3) In Java, if you create another object = older object it's bascially a ptr pointing to the same thing so if the older object changed then change will be reflected for the ptr too
         *    That's why we need to create a new instance.
         * 4) 
         */
    }

    @Test
    void removeStudent_InvalidStudent_ReturnsSNFException() {
        // Arrange
        // doThrow(new StudentNotFoundException(2L)).when(studentRepository.findById(2L));
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        // studentService.removeStudent(2L);

        // Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.removeStudent(2L));

        // Verify
        verify(studentRepository, times(1)).findById(2L);
        verify(studentRepository, never()).delete(any(Student.class));
        System.out.println("Success");
    }

    @Test
    void removeStudent_ValidStudent_ReturnsVoid() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

        // Act

        // Assert
        assertNotNull(mockStudent);
        assertDoesNotThrow(() -> studentService.removeStudent(1L));

        // Verify
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(mockStudent);
    }
}

/* You are testing the service layer
 * But the service layer calls methods of the repository layer
 * So the mock object is the repo
 */