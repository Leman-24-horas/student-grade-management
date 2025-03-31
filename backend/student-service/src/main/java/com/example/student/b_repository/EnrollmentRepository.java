package com.example.student.b_repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.a_entity.*;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
    /* Add custom queries later
     * Like find enrollment by courseId etc
     */

    // Find enrollment by sid and cid - to prevent duplication
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course); 
    List<Enrollment> findByCourse_CourseId(Long courseId);

    // @Query("SELECT e FROM Enrollment e JOIN FETCH e.course WHERE e.student.id = :studentId")
    // List<Enrollment> findByStudentIdWithCourse(@Param("studentId") Long studentId);

} 
