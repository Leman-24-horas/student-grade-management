package com.example.student.c_service;

import com.example.student.a_entity.Course;
import java.util.*;

public interface CourseService {
    List<Course> listAllCourses();
    Course findCourseById(Long id);
    Course addCourse(Course course);
    Course updateCourse(Long id, String courseName);
    void deleteCourse(Long id);
    void deleteAllCourses();

}
