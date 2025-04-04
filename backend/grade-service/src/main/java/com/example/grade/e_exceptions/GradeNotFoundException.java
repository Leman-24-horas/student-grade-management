package com.example.grade.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException(Long enrollmentId) {
        super("Could not find grade for eid=" + enrollmentId);
    }
}
