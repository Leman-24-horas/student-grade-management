package com.example.grade.d_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.grade.DTO.RequestDTO;
import com.example.grade.a_entity.Grade;
import com.example.grade.c_service.GradeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/get/{enrollmentId}")
    public ResponseEntity<Grade> getGrade(@PathVariable Long enrollmentId) {
        Grade gradeFound = gradeService.getGradeByEnrollmentId(enrollmentId);
        return ResponseEntity.ok(gradeFound);
    }

    @PostMapping("/add")
    public ResponseEntity<Grade> postGradeForEnrollment(@RequestBody RequestDTO.Enrollment enrollmentDetails) {
        Long enrollmentId = enrollmentDetails.getEnrollmentId();
        Long marks = enrollmentDetails.getMarks();

        Grade gradeAdded = gradeService.assignGrade(enrollmentId, marks);

        return ResponseEntity.ok(gradeAdded);
    }

    @PutMapping("/update/{enrollmentId}")
    public ResponseEntity<Grade> putGrade(@PathVariable Long enrollmentId, @RequestBody RequestDTO.Marks newMarks) {
        Grade updatedGrade = gradeService.updateGrade(enrollmentId, newMarks.getMarks());
        return ResponseEntity.ok(updatedGrade);
    }

    @DeleteMapping("/delete/{enrollmentId}")
    public ResponseEntity<?> deleteGrade(@PathVariable Long enrollmentId) {
        gradeService.deleteGrade(enrollmentId);
        return ResponseEntity.ok("Grade with enrollment_id=" + enrollmentId + " deleted successfully");
    }

}
