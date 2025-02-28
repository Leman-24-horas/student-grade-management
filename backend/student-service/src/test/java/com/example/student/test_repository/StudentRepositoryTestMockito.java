package com.example.student.test_repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.student.a_entity.Student;
import com.example.student.b_repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
public class StudentRepositoryTestMockito {

    @Mock
    private StudentRepository studentRepository;

    @Test
    void testSaveMethod() {
        // Arrange - Give the jar of water [Setup - instantiate entities etc needed for the actual method to be tested]
        Long sid = 1L; // L means long
        String name = "Ramu";
        Long marks = 69L;
        Student mockStudent = new Student(sid, name, marks); // Mocking student entity instead of calling a real one
        when(studentRepository.save(mockStudent)).thenReturn(mockStudent); // Mocking saved method instead of calling real one.
                                                                           

        // Act - [Calling the method in question to be tested]
        Student result = studentRepository.save(mockStudent); // if you do this wihtout the when().thenReturn() it will save the mockEntity to the DB which we don't want

        // Assert - [Verifying that the output matches the desired output]
        assertNotNull(mockStudent, "Saved student shouldn't be null");
        assertEquals(mockStudent.getId(), result.getId(), "Sids should match");
        assertEquals(mockStudent.getName(), result.getName(), "Names should match");
        assertEquals(mockStudent.getMarks(), result.getMarks(), "Marks should match");
        
        
        // Verify interaction
        verify(studentRepository).save(mockStudent); // verify that studentRepo was called to save mockStudent
    }
    
}
