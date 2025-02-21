package com.example.student.d_controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.student.a_entity.Student;
import com.example.student.c_service.StudentService;
import com.example.student.f_dto.MarksDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/student") // to set the base url: localhost:8080/student
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllStudents() {
        List<Student> listOfAllStudents = studentService.listAllStudents();

        if(listOfAllStudents.size() == 0) {
            return ResponseEntity.ok("Table is empty - Please add Student Details");
        }

        return ResponseEntity.ok(listOfAllStudents);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        try {
            Student student = studentService.findStudentById(id);
            return ResponseEntity.ok(student);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Alternative
    @PostMapping("/add")
    public ResponseEntity<?> postStudent(@RequestBody Student student) {
        try {
            studentService.addStudent(student);
            return ResponseEntity.ok("Student successfully added to database");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //Using DTO - Now marks of type Long have been converted into an Object
    @PutMapping("/update/{id}")
    public ResponseEntity<?> putStudent(@PathVariable Long id, @RequestBody MarksDTO marks) {
        try {
            Student updatedStudent = studentService.updateStudent(id, marks.getMarks());
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }        
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.removeStudent(id);
            return ResponseEntity.ok("Sucessfully deleted student with Id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
}
