package com.example.student.c_service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.student.a_entity.*;
import com.example.student.b_repository.EnrollmentRepository;
import com.example.student.e_exceptions.CourseNotFoundException;
import com.example.student.e_exceptions.EnrollmentAlreadyExistsException;
import com.example.student.e_exceptions.EnrollmentNotFoundException;
import com.example.student.e_exceptions.StudentNotFoundException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public List<Enrollment> listAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment findEnrollmentById(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);
        if (enrollment == null) {
            throw new EnrollmentNotFoundException(enrollmentId);
        }
        return enrollment;
    }

    @Override
    @Transactional
    public Enrollment addEnrollment(Enrollment enrollment) {
        /* Duplication check
        -----------------------
         * Mukesh Math 80
         * Mukesh Math 90
         * This is not allowed
         * 
         * Mukesh Math 90
         * Mukesh English 70
         * This is allowed
         * 
         * Enrollment cannot exist without student or couse
         */


        Student student = enrollment.getStudent();
        if(student == null) throw new StudentNotFoundException();

        Course course = enrollment.getCourse();
        if(course == null) throw new CourseNotFoundException();

        Optional<Enrollment> existingEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        if(existingEnrollment.isPresent()) {
            throw new EnrollmentAlreadyExistsException(student.getStudentId(), course.getCourseId());
        }
        
        try {
            return enrollmentRepository.save(enrollment);
        } catch (DataIntegrityViolationException e) {
            if(enrollment.getMarks().equals(null)) {
                throw new IllegalArgumentException("Marks cannot be null");
            } else {
                throw new EnrollmentAlreadyExistsException(enrollment.getEnrollmentId());
            }
        }
    }

    @Override
    public Enrollment updateEnrollment(Long enrollmentId, Enrollment newEnrollment) {
        Enrollment currentEnrollment = findEnrollmentById(enrollmentId);
        currentEnrollment.setStudent(newEnrollment.getStudent());
        currentEnrollment.setCourse(newEnrollment.getCourse());
        currentEnrollment.setMarks(newEnrollment.getMarks());
        enrollmentRepository.save(currentEnrollment);

        return currentEnrollment;
    }

    @Override
    public void deleteEnrollment(Long enrollmentId) {
        Enrollment enrollmentToBeDeleted = findEnrollmentById(enrollmentId);
        enrollmentRepository.delete(enrollmentToBeDeleted);
    }

    // Methods using special queries
    @Override
    public List<Enrollment> listAllEnrollmentsFromCourse(Long courseId) {
        List<Enrollment> list = enrollmentRepository.findByCourse_CourseId(courseId);
        if(list.size() == 0) throw new CourseNotFoundException(courseId);
        return list;
    }

    // @Override
    // public Enrollment findEnrollmentByStudentId(Long studentId) {
    //     Enrollment enrollment = enrollmentRepository.findByStudentIdWithCourse(studentId);
    //     if(enrollment == null) throw new StudentNotFoundException(studentId);
    //     return enrollment;
    // }


}
