package com.example.student.c_service;

import java.lang.StackWalker.Option;
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
    public void addEnrollment(Enrollment enrollment) {
        /* Duplication check
        -----------------------
         * Mukesh Math A
         * Mukesh Math B
         * This is not allowed
         * 
         * Mukesh Math A
         * Mukesh English B
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
            throw new EnrollmentAlreadyExistsException(student.getId(), course.getCourseId());
        }

        // System.out.println();
        //     System.out.println("----------Debugging SERVICE---------------");
        //     System.out.println("sid is " + student.getId());
        //     System.out.println("student name is " + student.getName());
        //     System.out.println("marks are " + student.getMarks());
        //     System.out.println("cid is " + course.getCourseId());
        //     System.out.println("course name is " + course.getCourseName());
        //     System.out.println("----------Debugging---------------");
        //     System.out.println();
        

        try {
            enrollmentRepository.save(enrollment);
        } catch (DataIntegrityViolationException e) {
            if(enrollment.getGrade().equals(null)) {
                throw new IllegalArgumentException("Grade cannot be null");
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
        currentEnrollment.setGrade(newEnrollment.getGrade());
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
