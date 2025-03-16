package com.example.student.g_config;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.student.a_entity.*;
import com.example.student.c_service.*;

@Component
@Lazy
public class DataLoader implements CommandLineRunner { // commandLineRunner so that this data is loader at run time

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    
    @Autowired
    public DataLoader(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    // to load data at runtime
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        /* 1. Check if the database is empty, if not then skip loading data */
        List<Student> listOfAllStudents = studentService.listAllStudents();
        List<Course> listOfAllCourses = courseService.listAllCourses();
        List<Enrollment> listOfAllEnrollments = enrollmentService.listAllEnrollments();

        if(!listOfAllStudents.isEmpty() || !listOfAllCourses.isEmpty() || !listOfAllEnrollments.isEmpty()) {
            System.out.println("Exisiting data - data loading skipped");
            return;

            /* TODO - code that wipes out all previous entries and loads in new data 
             * Need to add delete all method in service
            */
        }

        // 2. Create 20 student entities
        List<Student> studentList = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            // a) create the student entity
            Student student = new Student();
            student.setStudentName("Student_" + i);

            // b) save the student entity - this way DB will assign it an id making it a proper student entity as per its constructor
            Student savedStudent = studentService.addStudent(student); // save in database
            studentList.add(savedStudent); // add to list
        }

        // 3. Create 4 course entities
        List<Course> courseList = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCourseName("Course_" + i);

            Course savedCourse = courseService.addCourse(course);
            courseList.add(savedCourse);
        }

        // 4. Create enrollments
        /* 20 students
         * 4 courses
         * 20/4 = 5 students per course
         * But each student is enrolled so there are 20 enrollment entities too
         */
        int studentsPerCourse = studentList.size()/courseList.size();
        for(int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);

            for(int j = 0; j < studentsPerCourse; j++) {
                int studentIndex = i * studentsPerCourse + j; // between 0 and 19
                Student student = studentList.get(studentIndex);

                // Marks
                Random rand = new Random();
                Long marks = rand.nextLong(50, 100); // to be used later by the letter_grade microservice

                Enrollment enrollment = new Enrollment(student, course, marks);
                enrollmentService.addEnrollment(enrollment);
            }
        }
        System.out.println("[SUCCESS] Student and Course data successfully loaded!");
    }
}

/* Marks
 * 50 - 60 : D ie Pass
 * 61 - 70 : C
 * 71 - 80 : B
 * 80 - 90 : A
 * 90 - 100 : A*
 */
