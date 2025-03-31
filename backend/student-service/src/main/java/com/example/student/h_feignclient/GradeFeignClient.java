package com.example.student.h_feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.student.i_feigndto.FeignEnrollmentDTO;
import com.example.student.i_feigndto.Grade;

@FeignClient(value = "grade-service", url = "http://localhost:8081") // might need to change this when using docker
public interface GradeFeignClient {

    @PostMapping("/grade/add")
    ResponseEntity<Grade> postGradeForEnrollment(@RequestBody FeignEnrollmentDTO enrollmentDetails);

    @GetMapping("/grade/get/{enrollmentId}")
    ResponseEntity<Grade> getGrade(@PathVariable Long enrollmentId);
} 
