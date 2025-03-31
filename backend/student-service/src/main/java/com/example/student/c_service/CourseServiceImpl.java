package com.example.student.c_service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.student.a_entity.Course;
import com.example.student.b_repository.CourseRepository;
import com.example.student.e_exceptions.CourseAlreadyExistsException;
import com.example.student.e_exceptions.CourseNotFoundException;

@Service
public class CourseServiceImpl implements CourseService {
    
    @Autowired // field injection this time instead of constructor injection
    private CourseRepository courseRepository;

    @Override
    public List<Course> listAllCourses() {
        return courseRepository.findAll(); // if empty return msg like no content in DB yet on controller
    }

    @Override
    public Course findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);

        if(course == null) {
            throw new CourseNotFoundException(id);
        }

        return course;
    }

    @Override
    public Course addCourse(Course course) {
        try {
            return courseRepository.save(course);
        } catch (DataIntegrityViolationException e) { // due to nullable = false and unique == true
            if(course.getCourseName() == null) {
                throw new IllegalArgumentException("Course name cannot be null");
            } else {
                throw new CourseAlreadyExistsException(course.getCourseName());
            }
        }
    }

    @Override
    public Course updateCourse(Long cId, String newCName) {
        Course courseInQuestion = findCourseById(cId);
        courseInQuestion.setCourseName(newCName);
        courseRepository.save(courseInQuestion);

        return courseInQuestion;
    }

    @Override
    public void deleteCourse(Long cId) {
        Course courseInQuestion = findCourseById(cId); // will throw CourseNotFoundException 
        courseRepository.delete(courseInQuestion);
    }

    @Override
    public void deleteAllCourses() {
        courseRepository.deleteAll();
    }
}
