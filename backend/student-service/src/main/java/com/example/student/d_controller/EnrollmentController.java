package com.example.student.d_controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.student.c_service.*;
import com.example.student.a_entity.*;
import com.example.student.f_dto.*;


@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    /* Enrollment constructor here - not needed guess */

    @GetMapping("/all")
    public ResponseEntity<?> getAllEnrollments() {
        List<Enrollment> listOfAllEnrollments = enrollmentService.listAllEnrollments();

        if(listOfAllEnrollments.size() == 0) {
            return ResponseEntity.ok("Table is empty - please add Enrollment details");
        }

        return ResponseEntity.ok(listOfAllEnrollments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnrollment(@PathVariable Long id) {
        try {
            Enrollment validEnrollment = enrollmentService.findEnrollmentById(id);
            return ResponseEntity.ok(validEnrollment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addEnrollment(@RequestBody RequestDTO.CreateEnrollment newEnrollment) {
        
        try {
            Long sId = newEnrollment.getStudentId();
            Student student = studentService.findStudentById(sId); // will throw SNFE if sId invalid
            Long cId = newEnrollment.getCourseId();
            Course course = courseService.findCourseById(cId);

            Enrollment properEnrollment = new Enrollment(student, course, newEnrollment.getMarks());
            enrollmentService.addEnrollment(properEnrollment);
            return ResponseEntity.ok("Enrollment successfully added to database");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> putEnrollment(@PathVariable Long id, @RequestBody RequestDTO.CreateEnrollment newEnrollment) {
        /* You can request an Enrollment entity but the JSON body for it is too long -> chances of making errors
         * Use a dto so that we only need to use the sid, cid and the rest of the body can be obtained through internal code logic
         */

        try {
            // Create enrollment
            Long sId = newEnrollment.getStudentId();
            Student student = studentService.findStudentById(sId); // will throw SNFE if sId invalid
            Long cId = newEnrollment.getCourseId();
            Course course = courseService.findCourseById(cId);
            Enrollment newProperEnrollment = new Enrollment(student, course, newEnrollment.getMarks());

            Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, newProperEnrollment); 
            return ResponseEntity.ok(updatedEnrollment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Long id) {
        try {
            enrollmentService.deleteEnrollment(id);
            return ResponseEntity.ok("Enrollment with e_id = " + id + " successfully deleted.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/course/{cid}")
    public ResponseEntity<?> getEnrollmentFromClanId(@PathVariable Long cid) {
        try {
            List<Enrollment> list = enrollmentService.listAllEnrollmentsFromCourse(cid);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
