package com.example.student.d_controller;

import java.util.List;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.student.c_service.CourseService;
import com.example.student.c_service.EnrollmentService;
import com.example.student.c_service.StudentService;
import com.example.student.e_exceptions.StudentNotFoundException;
import com.example.student.a_entity.*;
import com.example.student.f_dto.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    /* Enrollment constructor here */

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

            System.out.println();
            System.out.println("----------Debugging---------------");
            System.out.println("CONTROLLER: marks are " + student.getId());
            System.out.println("CONTROLLER: marks are " + student.getName());
            System.out.println("CONTROLLER: marks are " + student.getMarks());
            System.out.println(course.toString());
            System.out.println("----------Debugging---------------");
            System.out.println();



            Enrollment properEnrollment = new Enrollment(student, course, newEnrollment.getGrade());
            enrollmentService.addEnrollment(properEnrollment);

            // Long studentId = newEnrollment.getStudent().getId();
            // Student student = studentService.findStudentById(studentId); // throw SNFE

            // Long courseId = newEnrollment.getCourse().getCourseId();
            // Course course = courseService.findCourseById(courseId); // throw CNFE

            // Enrollment enrollment = new Enrollment(student, course, newEnrollment.getGrade());

            // // enrollmentService.addEnrollment(newEnrollment); ORIGINAL
            // enrollmentService.addEnrollment(enrollment);

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
            Enrollment newProperEnrollment = new Enrollment(student, course, newEnrollment.getGrade());

            Enrollment updatedEnrollment = enrollmentService.updateEnrollment(id, newProperEnrollment); // TODO use Enrollment
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
