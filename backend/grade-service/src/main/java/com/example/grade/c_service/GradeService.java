package com.example.grade.c_service;

import com.example.grade.a_entity.Grade;

public interface GradeService {
    Grade getGradeByEnrollmentId(Long enrollmentId);
    Grade assignGrade(Long enrollmentId, Long marks);
    Grade updateGrade(Long enrollmentId, Long newMarks);
    void deleteGrade(Long enrollmentId);
    String calculateGrade(Long marks);
}
