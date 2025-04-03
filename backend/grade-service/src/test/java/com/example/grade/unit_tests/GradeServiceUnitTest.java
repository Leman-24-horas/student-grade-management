package com.example.grade.unit_tests;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.grade.b_repository.GradeRepository;
import com.example.grade.c_service.GradeServiceImpl;
import com.example.grade.e_exceptions.GradeAlreadyExistsException;
import com.example.grade.e_exceptions.GradeNotFoundException;
import com.example.grade.a_entity.Grade;

@ExtendWith(MockitoExtension.class)
public class GradeServiceUnitTest {
    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeServiceImpl gradeService;

    private Grade mockGrade;

    @BeforeEach
    void setUp() {
        mockGrade = new Grade(1L, 95L, "A*"); // using the constructor eid, marks, letter_grade
    }

    @Test
    void getGradeByEnrollmentId_Exists_ReturnGrade() {
        // Arrange
        Long enrollmentId = 1L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.of(mockGrade));

        // Act
        Grade result = gradeService.getGradeByEnrollmentId(enrollmentId);

        // Assert
        assertNotNull(result);
        assertEquals(mockGrade, result);

        // Verify
        verify(gradeRepository, times(1)).findByEnrollmentId(enrollmentId);
    }

    @Test
    void getGradeByEnrollmentId_NotFound_ReturnGNFE() {
        // Arrange
        Long enrollmentId = 99L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GradeNotFoundException.class, () -> gradeService.getGradeByEnrollmentId(enrollmentId));

        // Verify
        verify(gradeRepository, times(1)).findByEnrollmentId(enrollmentId);
    }

    @Test
    void deleteGrade_ValidEid() {
        // Arrange
        Long enrollmentId = 1L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.of(mockGrade));

        // Act 
        gradeService.deleteGrade(enrollmentId);

        // Verfiy
        verify(gradeRepository, times(1)).delete(mockGrade);
    }

    @Test
    void deleteGrade_InvalidEid() {
        // Arrange
        Long enrollmentId = 99L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GradeNotFoundException.class, () -> gradeService.deleteGrade(enrollmentId));

        // Verify
        verify(gradeRepository, never()).delete(any());
    }

    @Test
    void assignGrade_Valid_ReturnGrade() {
        // Arrange
        Long enrollmentId = 2L;
        Long marks = 85L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.empty());
        when(gradeRepository.save(any(Grade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Grade result = gradeService.assignGrade(enrollmentId, marks);

        // Assert
        assertNotNull(result);
        assertEquals(enrollmentId, result.getEnrollmentId());
        assertEquals(marks, result.getMarks());
        assertEquals("A", result.getLetterGrade());

        // Verify
        verify(gradeRepository, times(1)).findByEnrollmentId(enrollmentId);
        verify(gradeRepository, times(1)).save(any(Grade.class));
    }

    @Test
    void assignGrade_DuplicateEntry_ReturnGradeAlrExistsException() {
        // Arrange
        Long enrollmentId = 1L;
        Long marks = 90L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.of(mockGrade));

        // Act & Assert
        assertThrows(GradeAlreadyExistsException.class, () -> gradeService.assignGrade(enrollmentId, marks));

        // Verify
        verify(gradeRepository, times(1)).findByEnrollmentId(enrollmentId);
        verify(gradeRepository, never()).save(any(Grade.class));
    }

    @Test
    void assignGrade_InvalidParameters_ReturnExceptions() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> gradeService.assignGrade(null, 85L));
        assertThrows(IllegalArgumentException.class, () -> gradeService.assignGrade(1L, null));
    }

    @Test
    void testCalculateGrade() {
        // Act & Assert
        assertEquals("A*", gradeService.calculateGrade(95L));
        assertEquals("A", gradeService.calculateGrade(85L));
        assertEquals("B", gradeService.calculateGrade(75L));
        assertEquals("C", gradeService.calculateGrade(65L));
        assertEquals("D", gradeService.calculateGrade(55L));
        assertEquals("U", gradeService.calculateGrade(45L));
    }

    @Test
    void updateGrade_Valid_ReturnGrade() {
        // Arrange
        Long enrollmentId = 1L;
        Long newMarks = 95L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.of(mockGrade));
        when(gradeRepository.save(any(Grade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Grade result = gradeService.updateGrade(enrollmentId, newMarks);

        // Assert
        assertNotNull(result);
        assertEquals(enrollmentId, result.getEnrollmentId());
        assertEquals(newMarks, result.getMarks());
        assertEquals("A*", result.getLetterGrade());

        // Verify
        verify(gradeRepository, times(1)).findByEnrollmentId(enrollmentId);
        verify(gradeRepository, times(1)).save(mockGrade);
    }

    @Test
    void updateGrade_InvalidEid_ReturnGNFE() {
        // Arrange
        Long enrollmentId = 99L;
        Long newMarks = 80L;
        when(gradeRepository.findByEnrollmentId(enrollmentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(GradeNotFoundException.class, () -> gradeService.updateGrade(enrollmentId, newMarks));

        // Verify
        verify(gradeRepository, times(1)).findByEnrollmentId(enrollmentId);
        verify(gradeRepository, never()).save(any(Grade.class));
    }
    
}