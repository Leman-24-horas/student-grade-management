package com.example.student.b_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student.a_entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

} 
