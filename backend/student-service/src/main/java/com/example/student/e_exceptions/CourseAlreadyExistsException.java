package com.example.student.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String cName) {
        super("Course with name = " + cName + " already exists.");
    }
}
