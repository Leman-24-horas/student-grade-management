package com.example.student.d_controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.student.a_entity.Student;
import com.example.student.c_service.StudentService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<Student> getAllStudents() {
        return studentService.listAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    @PostMapping("/add")
    public void postStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @PutMapping("/update/{id}")
    public Student putStudent(@PathVariable Long id, @RequestBody Student student) {        
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
    }
    
    
    
}
