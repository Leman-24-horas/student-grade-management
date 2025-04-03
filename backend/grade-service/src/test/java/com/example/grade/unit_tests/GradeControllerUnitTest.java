package com.example.grade.unit_tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.grade.DTO.RequestDTO;
import com.example.grade.a_entity.Grade;
import com.example.grade.c_service.GradeServiceImpl;
import com.example.grade.d_controller.GradeController;
import com.example.grade.e_exceptions.GradeNotFoundException;

@ExtendWith(MockitoExtension.class)
public class GradeControllerUnitTest {
    @Mock
    private GradeServiceImpl gradeService;

    @InjectMocks
    private GradeController gradeController;

    private Grade mockGrade;

    @BeforeEach
    void setUp() {
        mockGrade = new Grade(1L, 95L, "A*");
    }

    @Test
    void getGrade_IfFound_ReturnOk() {
        // Arrange
        Long enrollmentId = 1L;
        when(gradeService.getGradeByEnrollmentId(enrollmentId)).thenReturn(mockGrade);
        
        // Act
        ResponseEntity<Grade> response = gradeController.getGrade(enrollmentId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockGrade, response.getBody());
        
        // Verify
        verify(gradeService, times(1)).getGradeByEnrollmentId(enrollmentId);
    }

    @Test
    void getGrade_InvalidEid_ReturnNotFound() {
        // Arrange
        Long enrollmentId = 99L;
        when(gradeService.getGradeByEnrollmentId(enrollmentId)).thenThrow(new GradeNotFoundException(enrollmentId));
        
        // Act
        Exception exception = assertThrows(GradeNotFoundException.class, () -> gradeController.getGrade(enrollmentId));
        
        // Assert
        assertEquals("Could not find grade for eid=" + enrollmentId, exception.getMessage());
        
        // Verify
        verify(gradeService, times(1)).getGradeByEnrollmentId(enrollmentId);
    }

    @Test
    void postGradeForEnrollment_IfValid_ReturnOk() {
        // Arrange
        RequestDTO.Enrollment enrollmentDetails = new RequestDTO.Enrollment();
        enrollmentDetails.setEnrollmentId(1L);
        enrollmentDetails.setMarks(85L);
        when(gradeService.assignGrade(enrollmentDetails.getEnrollmentId(), enrollmentDetails.getMarks())).thenReturn(mockGrade);
        
        // Act
        ResponseEntity<Grade> response = gradeController.postGradeForEnrollment(enrollmentDetails);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockGrade, response.getBody());
        
        // Verify
        verify(gradeService, times(1)).assignGrade(enrollmentDetails.getEnrollmentId(), enrollmentDetails.getMarks());
    }

    @Test
    void putGrade_IfValid_ReturnOk() {
        // Arrange
        Long enrollmentId = 1L;
        RequestDTO.Marks newMarks = new RequestDTO.Marks();
        newMarks.setMarks(90L);
        Grade updatedGrade = new Grade(enrollmentId, 90L, "A");
        when(gradeService.updateGrade(enrollmentId, newMarks.getMarks())).thenReturn(updatedGrade);
        
        // Act
        ResponseEntity<Grade> response = gradeController.putGrade(enrollmentId, newMarks);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGrade, response.getBody());
        
        // Verify
        verify(gradeService, times(1)).updateGrade(enrollmentId, newMarks.getMarks());
    }

    @Test
    void deleteGrade_IfValid_ReturnOk() {
        // Arrange
        Long enrollmentId = 1L;
        doNothing().when(gradeService).deleteGrade(enrollmentId);
        
        // Act
        ResponseEntity<?> response = gradeController.deleteGrade(enrollmentId);
        
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Grade with enrollment_id=" + enrollmentId + " deleted successfully", response.getBody());
        
        // Verify
        verify(gradeService, times(1)).deleteGrade(enrollmentId);
    }
}
