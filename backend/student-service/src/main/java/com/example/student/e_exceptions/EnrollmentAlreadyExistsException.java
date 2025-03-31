package com.example.student.e_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EnrollmentAlreadyExistsException extends RuntimeException {
    public EnrollmentAlreadyExistsException(Long enrollmentId) {
        super("Enrollment with enrollmentId = " + enrollmentId + " already exists");
    }

    public EnrollmentAlreadyExistsException() {
        super("Duplicate entry for enrollment");
    }

    public EnrollmentAlreadyExistsException(Long sId, Long cId) {
        super("DUPLICATE ENTRY ERROR: Student with sid = " + sId + " is already enrolled into Course with cid = " + cId);
    }
}
