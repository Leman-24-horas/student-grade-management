package com.example.grade.d_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.grade.a_entity.Grade;
import com.example.grade.c_service.GradeService;
import com.example.grade.e_exceptions.GradeAlreadyExistsException;

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

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllGrades() {
        List<Grade> listOfAllGrades = gradeService.listAllGrades();
        if (listOfAllGrades.size() == 0) {
            return ResponseEntity.ok("Table is empty - Please add Grade details");
        }
        return ResponseEntity.ok(listOfAllGrades);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getGrade(@PathVariable Long id) {
        try {
            Grade gradeFound = gradeService.getGradeByGid(id);
            return ResponseEntity.ok(gradeFound);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> postGrade(@RequestBody Grade grade) {
        try {
            Grade addedGrade = gradeService.addGrade(grade);
            return ResponseEntity.ok(addedGrade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (GradeAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> putGrade(@PathVariable Long id, @RequestBody Grade newGrade) { // check if newGrade is valid like id not null etc
        try {
            Grade updatedGrade = gradeService.updateGrade(id, newGrade);
            return ResponseEntity.ok(updatedGrade);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGrade(@PathVariable Long id) {
        try {
            gradeService.deleteGrade(id);
            return ResponseEntity.ok("Grade with grade_id=" + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
      
}
