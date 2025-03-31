package com.example.student.c_service;

import java.util.List;

import com.example.student.a_entity.Enrollment;
import com.example.student.i_feigndto.Grade;

public interface EnrollmentService {
    List<Enrollment> listAllEnrollments();
    List<Enrollment> listAllEnrollmentsFromCourse(Long courseId); // uses special query
    Enrollment findEnrollmentById(Long enrollmentId);
    
    Enrollment addEnrollment(Enrollment enrollment);
    Enrollment updateEnrollment(Long enrollmentId, Enrollment newEnrollment);
    void deleteEnrollment(Long enrollmentId); 
    void deleteAllEnrollments();
    
    // feign client methods
    Grade calculateGradeForEnrollment(Long enrollmentId);
    Grade getGradeForEnrollment(Long enrollmentId);
} 
