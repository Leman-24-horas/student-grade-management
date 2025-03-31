package com.example.grade.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GradeAlreadyExistsException extends RuntimeException {
    public GradeAlreadyExistsException(Long eId) {
        super("Grade for enrollment with eid=" + eId + " already exists");
    }
}
