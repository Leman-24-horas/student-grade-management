package com.example.student.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long studentId) {
        super("Could not find Student with Id = " + studentId);
    }

    // Empty constructor being used in enrollmentService
    public StudentNotFoundException() {
        super("Student does not exist - failed to create enrollment");
    }
}
