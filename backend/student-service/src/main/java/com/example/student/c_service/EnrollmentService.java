package com.example.student.c_service;

import java.util.List;

import com.example.student.a_entity.Enrollment;

public interface EnrollmentService {
    List<Enrollment> listAllEnrollments();
    List<Enrollment> listAllEnrollmentsFromCourse(Long courseId); // uses special query
    Enrollment findEnrollmentById(Long enrollmentId);
    // Enrollment findEnrollmentByStudentId(Long studentId); //needs special query
    
    void addEnrollment(Enrollment enrollment);
    Enrollment updateEnrollment(Long enrollmentId, Enrollment newEnrollment);
    void deleteEnrollment(Long enrollmentId);  
} 
