package com.example.student.f_dto;

import lombok.Getter;
import lombok.Setter;

public class RequestDTO {
    
    @Getter
    @Setter
    public static class CreateEnrollment {
        Long studentId;
        Long courseId;
        Long marks;
    }

    @Getter
    @Setter
    public static class Marks {
        private Long marks;
    }

}

