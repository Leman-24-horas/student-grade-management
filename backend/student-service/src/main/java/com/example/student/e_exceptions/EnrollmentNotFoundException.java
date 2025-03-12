package com.example.student.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(Long enrollmentId) {
        super("Could not find Enrollment with eid = " + enrollmentId);
    }
}
