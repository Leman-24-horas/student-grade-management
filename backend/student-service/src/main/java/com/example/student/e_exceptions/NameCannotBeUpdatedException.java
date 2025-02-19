package com.example.student.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NameCannotBeUpdatedException extends RuntimeException {
    public NameCannotBeUpdatedException() {
        super("Error - Name provided does not matching existing name. Please be reminded that student name cannot be changed");
    }

}
