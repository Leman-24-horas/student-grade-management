package com.example.student.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StudentAlreadyExistsException extends RuntimeException {

    public StudentAlreadyExistsException(String name) {
        super("Student with name = " + name + " already exsists.");
    }
    
}
