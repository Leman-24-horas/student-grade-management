package com.example.student.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Long courseId) {
        super("Could not find course with cid = " + courseId);
    }

    public CourseNotFoundException() {
        super("Course does not exist - could not create enrollment");
    }
}
