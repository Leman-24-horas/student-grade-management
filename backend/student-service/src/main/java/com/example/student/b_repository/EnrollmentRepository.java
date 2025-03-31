package com.example.student.b_repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.a_entity.*;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{

    // Find enrollment by sid and cid - to prevent duplication
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course); 
    List<Enrollment> findByCourse_CourseId(Long courseId);

} 
