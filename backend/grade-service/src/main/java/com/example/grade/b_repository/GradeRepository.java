package com.example.grade.b_repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grade.a_entity.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    
} 
