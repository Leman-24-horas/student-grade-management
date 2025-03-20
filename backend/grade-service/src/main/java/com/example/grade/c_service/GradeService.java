package com.example.grade.c_service;

import java.util.List;

import com.example.grade.a_entity.Grade;

public interface GradeService {
    List<Grade> listAllGrades();
    Grade getGradeByGid(Long gradeId);
    Grade addGrade(Grade grade);
    Grade updateGrade(Long gradeId, Grade grade);
    void deleteGrade(Long gradeId);
}
