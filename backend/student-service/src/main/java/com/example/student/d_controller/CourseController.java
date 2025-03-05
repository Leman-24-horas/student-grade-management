package com.example.student.d_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.c_service.CourseService;
import com.example.student.a_entity.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCourses() {
        List<Course> listOfAllCourses = courseService.listAllCourses();

        if(listOfAllCourses.size() == 0) {
            return ResponseEntity.ok("Table is empty - Please add Course Details");
        }
        
        return ResponseEntity.ok(listOfAllCourses);
    }

    @GetMapping("/{id}") // id here has to match with @PathVariable
    public ResponseEntity<?> getStudent(@PathVariable Long id) {
        try {
            Course course = courseService.findCourseById(id);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> postCourse(@RequestBody Course course) {
        try {
            courseService.addCourse(course);
            return ResponseEntity.ok("Course successfully added to database");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> putCourse(@PathVariable Long id, @RequestBody Course newCourse) {
        try {
            Course updatedCourse = courseService.updateCourse(id, newCourse.getCourseName());
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok("Course with id = " + id + " successfully deleted from database.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
    
}
