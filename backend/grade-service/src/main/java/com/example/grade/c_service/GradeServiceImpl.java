package com.example.grade.c_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.grade.a_entity.Grade;
import com.example.grade.b_repository.GradeRepository;
import com.example.grade.e_exceptions.GradeAlreadyExistsException;
import com.example.grade.e_exceptions.GradeNotFoundException;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public Grade getGradeByEnrollmentId(Long enrollmentId) {
        return gradeRepository.findByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new GradeNotFoundException(enrollmentId));
    }

    @Override
    public Grade assignGrade(Long enrollmentId, Long marks) { // this takes eId and marks

        // If invalid parameters
        if (enrollmentId == null || marks == null) {
            throw new IllegalArgumentException("Grade and/or Enrollment_id cannot be null.");
        }

        // If grade already exists
        Grade existingGrade = gradeRepository.findByEnrollmentId(enrollmentId).orElse(null);
        if (existingGrade != null) {
            throw new GradeAlreadyExistsException(enrollmentId);
        }

        String letterGrade = calculateGrade(marks);
        Grade grade = new Grade(enrollmentId, marks, letterGrade);
        return gradeRepository.save(grade);
    }

    public Grade updateGrade(Long enrollmentId, Long newMarks) {

        Grade existingGrade = getGradeByEnrollmentId(enrollmentId); // throws GNFE
        existingGrade.setMarks(newMarks);
        String updatedLetterGrade = calculateGrade(newMarks);
        existingGrade.setLetterGrade(updatedLetterGrade);

        return gradeRepository.save(existingGrade);
    }

    @Override
    public void deleteGrade(Long enrollmentId) {
        Grade grade = getGradeByEnrollmentId(enrollmentId); // throws GNFE
        gradeRepository.delete(grade);
    }

    @Override
    public String calculateGrade(Long marks) {
        if (marks >= 91 && marks <= 100) {
            return "A*";
        } else if (marks >= 81 && marks <= 90) {
            return "A";
        } else if (marks >= 71 && marks <= 80) {
            return "B";
        } else if (marks >= 61 && marks <= 70) {
            return "C";
        } else if (marks >= 50 && marks <= 60) {
            return "D";
        } else {
            return "U";
        }
    }

}
